/**
* @file csci3060_team_surprised_pikachu\Phase 5\Controller.java
* @author  Stephanie Phung
* @author  Abhiram Sinnarajah
* @author  Aaron Williams
* @version 3.0
* @brief Runs the main program logic of the back end.
*/
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Controller {
    // constants
    private final String KEY_END = "END";
    private final int KEY_USERS = 11; // trivial
    private final int KEY_EVENTS = 99; // trivial
    private final int USER_LENGTH = 28; // length of a line in userFile
    private final int EVENT_LENGTH = 52; // length of a line in ticketsFile

    // member variables
    private String userFile;
    private String ticketFile;
    private String inputFile;
    private ArrayList<User> users;
    private ArrayList<Event> events;

    public Controller(String userFile, String ticketFile, String inputFile) {
      this.userFile = userFile;
      this.ticketFile = ticketFile;
      this.inputFile = inputFile;
      users = new ArrayList<>();
      events = new ArrayList<>();

      parseData(KEY_USERS);
      parseData(KEY_EVENTS);
    }

    /**
     * Parses the correct data file into the appropriate arraylist.
     *
     * @param key unique ID for type of data
     */
    private void parseData(int key) {
        // Try opening the file.
        try {
            FileReader fr = null;

            // Select file based on the key.
            if (key == KEY_USERS) {
              fr = new FileReader(userFile);
            } else if (key == KEY_EVENTS) {
              fr = new FileReader(ticketFile);
            }

            if (fr == null) {
              System.err.println("ERROR: Invalid key.");
              System.exit(1);
            } else {
                BufferedReader br = new BufferedReader(fr);
                String line;
                // While the file has a next valid line, create a new user/event.
                while (((line = br.readLine()) != null) && !line.trim().equals(KEY_END)) {
                    if (key == KEY_USERS) {
                      User user = new User(line);
                      users.add(user);
                    } else if (key == KEY_EVENTS) {
                      Event event = new Event(line);
                      events.add(event);
                    }
                }
                br.close();
            }
        } catch (IOException e) { // Print error if exists.
            e.printStackTrace();
        }
    }

    /**
     * Creates an appropriate object (user or event) and adds it to the
     * appropriate prott  protected arraylist by checking of transaction string.
     *
     * @param trn transaction string containing the data.
     */
    private void create(String trn) {
        if (trn.length() == USER_LENGTH) {
          User user = new User(trn);
          if (!duplicateExists(user.getName(), this.users)) {
            users.add(user);
          } else {
            System.err.println("ERROR: User already exists");
          }
        } else if (trn.length() == EVENT_LENGTH) {
          Event event = new Event(trn);
          if (!duplicateExists(event.getName(), this.events)) {
            events.add(event);
          } else {
            System.err.println("ERROR: Event already exists");
          }
        }
    }

    /**
     * Returns true if a there is an object with the same name in the list.
     *
     * @return boolean true if duplicate exists.
     */
    private boolean duplicateExists(String name, ArrayList<? extends Handler> list) {
        for (Handler h : list) {
            if (h.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes the object that matches the given transaction string by
     * going through the appropriate array list.
     *
     * @param trn transaction string containing data to delete.
     * @param list array list of type child of Handler
     */
    private void delete(String trn, ArrayList<? extends Handler> list) {
        String username = "";
        for (int i = 0; i < list.size(); i++) {
            // If the data matches, remove it.
            if (trn.equals(list.get(i).toTRN())) {
                username = list.get(i).getName();
                list.remove(i);
                break;
                //return;
            }
        }
        if (!username.equals("")) {
          int j = events.size();
          for (int i = 0; i < j;) {
            if (events.get(i).getSeller().equals(username)) {
              events.remove(i);
              j--;
            } else {
              i++;
            }
          }
        }
    }

    /**
     * Writes saved data to file.
     *
     * @param list array list of type child of Handler
     */
    private void write(ArrayList<? extends Handler> list, int key) {
      try {
        File file = null;
        // Open appropriate file.
        //if (list.get(0) instanceof User) {
        if (key == KEY_USERS) {
          file = new File(userFile);
        //} else if (list.get(0) instanceof Event) {
        } else if (key == KEY_EVENTS) {
          file = new File(ticketFile);
        }

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        // Write data to file.
        for (Handler h : list) {
          bw.write(h.toTRN() + "\n");
        }
        // Add final "END" keyword with appropriate padding.
        //int padding = list.get(0).toTRN().length() - KEY_END.length();
        int padding = EVENT_LENGTH;
        if (!list.isEmpty()) {
          padding = list.get(0).toTRN().length() - KEY_END.length();
        }

        String end = KEY_END;
        for (int i = 0; i < padding; i++) {
          end += " ";
        }
        bw.write(end);
        bw.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    /*
    * Save to both tickets and users file.
    */
    private void save() {
      write(this.users, KEY_USERS);
      write(this.events, KEY_EVENTS);
    }

    /**
    * @brief To update a current user's available credit based on the specified amount indicated by the
             transaction string of a current user.
    * @param [in] trn    A string representation of a transaction from the merged Daily transaction File (DTF)
    */
    private void addCredit(String trn) {
        String username = trn.substring(3, 18);
        String credit = trn.substring(22, 31);

        for (int i=0; i<users.size(); i++) {
          if (username.equals(users.get(i).getName())) {
            double newCredit = users.get(i).getCredit() + Double.parseDouble(credit);
            // check for overflow issue
            if (newCredit > 999999.99) {
               System.out.println("ERROR: Overflow");
            } else {
               users.get(i).setCredit(newCredit);
            }
            return;
          }
        }
      }

    /**
    * @brief To update a buyer's available credit and seller's available credit based on the specified amount
             indiciated by the transaction string of a current user.
    * @param [in] increase    A string that represents the name of the user object that will receive the increase in their available credit
                  decreasee   A string that represents the name of the user object that will decrease their available credit
                  credit      A string representation of the amount to adjust the credit of specified users by.
    * @return void
    */
    private void refundCredit(String increase, String decrease, String credit) {
      // method to refund credit to a user based on the information provided from the array list and transaction
      // here increase represents the buyer () and decrease represents seller
      boolean checkIncrease = true;
      int increaseLoc = 0;
      boolean checkDecrease =  true;
      int decreaseLoc = 0;

      // find the matching user object from list
        for (int i=0; i<users.size(); i++) {
          if (checkIncrease) {
            if (increase.equals(users.get(i).getName())) {
              increaseLoc = i;
              checkIncrease = false;
            }
          }
          if (checkDecrease) {
            if (decrease.equals(users.get(i).getName())) {
              decreaseLoc = i;
              checkDecrease = false;
            }
          }
        }
        // check for overflow errors and then proceed
        if (((users.get(increaseLoc).getCredit() + Double.parseDouble(credit)) <= 999999.99) &&
          ((users.get(decreaseLoc).getCredit() - Double.parseDouble(credit)) >= 0.0)) {
          users.get(increaseLoc).setCredit(users.get(increaseLoc).getCredit() + Double.parseDouble(credit));
          users.get(decreaseLoc).setCredit(users.get(decreaseLoc).getCredit() - Double.parseDouble(credit));
        } else {
          System.out.println("ERROR: Overflow");
        }
      }

    /**
    * @brief Applies the changes based on the id from each transaction line. Invokes necessary methods for each matched
             possble actions create, delete, buy, sell, addcredit and refund. (Buy is a special case here**)
    * @param [in] trn     A string that representation of the transaction from reading the line. Transaction Buy in this
                          case is a special case which gets padded with extra information that is needed for completion of
                          the transaction, buyer name.
    * @return void
    */
    private void enforceRules(String trn) {
      // identify which action to take
      String id = trn.substring(0, 2);
      if (id != "00") {
        switch (id) {
          case "01":
            String cre_Harness_Trn = trn.substring(3, 31);
            System.out.println("Applying steps for create...");
            create(cre_Harness_Trn);
            System.out.println("Complete");
            break;

          case "02":
            String del_Harness_Trn = trn.substring(3, 31);
            System.out.println("Applying steps for delete!");
            delete(del_Harness_Trn, this.users);
            System.out.println("Complete");
            break;

          case "03":
            String sel_Harness_Trn = trn.substring(3, 55);
            System.out.println("Applying steps for sell!");
            create(sel_Harness_Trn);
            System.out.println("Complete");
            break;

          // special case ** for purchase transactions
          case "04":
            System.out.println("Applying steps for buy!");
            String[] lines = trn.split("\n");
            String eventData = lines[0].substring(3);
            String userData = lines[1].substring(3);

            Event e = new Event(eventData);
            User temp = new User(userData);
            User u = null;
            User s = null;
            for (User user : users) {
              if (temp.getName().equals(user.getName())) {
                u = user;
              }
              if (e.getSeller().equals(user.getName())) {
                s = user;
              }
            }
            if ((u == null) || (s == null)) {
              System.out.println("Error: User not found, may have been deleted.");
              return;
            }

            // constraint: if user is not admin, cannot buy more than 4 tickets
            if (!(u.getType().equals("AA")) && (e.getTickets() > 4)) {
              System.out.println("Invalid transaction.");
              //break;
              return;
            }

            // Go through all the events
            for (Event event : events) {
              // If the event name and seller is the same
              if (event.getName().equals(e.getName()) && event.getSeller().equals(e.getSeller())) {
                // check to see if there are enough tickets to purchase
                int ticketsPurchased = event.getTickets(); // set as default as all the tickets
                if (event.getTickets() >= e.getTickets()) { // if we want to purchase less than all
                  ticketsPurchased = e.getTickets(); // set that number instead
                }

                double total = ticketsPurchased * event.getPrice();

                // check that the user has enough credits to make the purchase
                if (total <= u.getCredit()) {
                  // check that the seller's credits will not overflow
                  if ((s.getCredit() + total) > 999999.99) {
                    System.out.println("Error: Seller has too much money.");
                    return;
                  } else {
                    // update the user credit and update the events
                    u.setCredit(u.getCredit() - total);
                    s.setCredit(s.getCredit() + total);
                    event.updateTickets(event.getTickets() - ticketsPurchased);

                    if (event.getTickets() == 0) {
                      delete(event.toTRN(), events);
                    }

                    // Save the user details
                    for (int i = 0; i < users.size(); i++) {
                      if (users.get(i).getName().equals(s.getName())) {
                        users.set(i, s);
                      }
                      if (users.get(i).getName().equals(u.getName())) {
                        users.set(i, u);
                      }
                    }

                    System.out.println("Complete");
                    return;
                  }
                }
              }
            }
            break;

          case "05":
            System.out.println("Applying steps for refund!");
            String buyer = trn.substring(3, 18);
            String seller = trn.substring(19, 34);
            String credit = trn.substring(35, 44);
            refundCredit(buyer, seller, credit);
            System.out.println("Complete");
            break;

          case "06":
            System.out.println("Applying steps for addcredit!");
            addCredit(trn);
            System.out.println("Complete");
            break;
        }
      }
    }

    public void execute() {
      //** step one: read in the mergedDTF fileName **
      try {
        //File mergedDTF = new File("merged_DTF.data");
        File mergedDTF = new File(this.inputFile);
        FileReader dtf_fr = new FileReader(mergedDTF);
        BufferedReader dtf_br = new BufferedReader(dtf_fr);
        String trn_line;
        // three variables below used for buy transaction specifically
        boolean sameDTF = false;
        String buy_Trn = "";
        String logout_Trn = "";
        while ((trn_line = dtf_br.readLine()) != null) {
          //** step two: apply steps based on these per line instruction. **
          String id = trn_line.substring(0, 2);
          if (id.equals("04")) {
            buy_Trn += trn_line +"\n";
            sameDTF = true;
            // retreive all the necessary information first, then create a
            // new string that contains all three information. Pass this into the enforceRules.
          }
          if (sameDTF == true) {
            if (id.equals("00")) {
              // now check the length of this transaction to see if its logout trans. or end of file
              if (trn_line.length() == 31) {
                logout_Trn = trn_line;
                // At this point, I now have access to previous buyer information and logout info.
                // So for more than one buy transaction, simply parse it and call this method that many
                // times
                String[] allBuyerStrings = buy_Trn.split("\n");
                for (int i=0; i<allBuyerStrings.length; i++) {
                  //System.out.println(allBuyerStrings[i]);
                  //System.out.println(logout_Trn);
                  //c1.enforceRules(buyTrnString(allBuyerStrings[i], logout_Trn));
                  enforceRules(allBuyerStrings[i] + "\n" + logout_Trn);
                }
                //c1.enforceRules(buyTrnString(buy_Trn, logout_Trn));
              }
              // this means "00" so the following line is not apart of the previous dtf
              else {
                sameDTF = false;
                buy_Trn = "";
              }
            }
          }
          else {
            enforceRules(trn_line);
          }
        }
        // add buyer and logout lines to test print of them below this..
        dtf_br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      //** step three: save the changes to the files **
      save();
    }

      /**
    * @brief This is the main program logic. Also ensures standard saftey checks are met and also calls all necessary
             functions that result in modification of two files (user accounts & available tickets).
    * @param [in] args   Contains the supplied command-line-arguments, if any as an array of String objects.
    * @return void
    */
    public static void main(String[] args) {
      String userFile = "";
      String ticketFile = "";
      String inputFile = "";

      if (args.length < 3) {
        System.out.println("Not enough arguments. Try:\njava Controller <userFile> <ticketFile> <inputFile>");
        System.exit(0);
      } else {
        userFile = args[0];
        ticketFile = args[1];
        inputFile = args[2];
      }

      Controller controller = new Controller(userFile, ticketFile, inputFile);
      controller.execute();
  }
}
