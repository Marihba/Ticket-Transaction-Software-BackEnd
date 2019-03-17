/**
* @file csci3060_team_surprised_pikachu\Version 1.0\Controller.java
* @author  Stephanie Phung
* @author  Abhiram Sinnarajah
* @author  Aaron Williams
* @date 9 Mar 2019
* @version 1.1
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
    // member variables
    private String userFile;
    private String ticketFile;
    private ArrayList<User> users;
    private ArrayList<Event> events;

    // constructors
    public Controller(String userFile, String ticketFile, ArrayList<User> users, ArrayList<Event> events) {
      this.userFile = userFile;
      this.ticketFile = ticketFile;
      this.users = users;
      this.events = events;
    }

    /**
     * Parses the current user accounts file and returns an ArrayList
     * containing all the users.
     *
     * @param fileName file name of file to parse.
     * @return ArrayList<User> contains all the users in the file.
     */
    public static ArrayList<User> parseUsers(String fileName) {
        ArrayList<User> users = new ArrayList();

        // Try opening the file.
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            // While the file has a next line, create a new user.
            while ((line = br.readLine()) != null) {
                User user = new User(line);
                // If the username is valid, then add it to the list.
                if (!(user.getName()).equals(user.KEY_END)) {
                  users.add(user);
                }
            }
            br.close();
        } catch (IOException e) { // Print error if exists.
            e.printStackTrace();
        }

        // Return the populated list.
        return users;
    }

    /**
     * Parses the tickets file and returns an ArrayList
     * containing all the events.
     *
     * @param fileName file name of file to parse.
     * @return ArrayList<Event> contains all the events found in the file.
     */
    public static ArrayList<Event> parseEvents(String fileName) {
        ArrayList<Event> events = new ArrayList();

        // Try opening the file.
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            // While the file has a next line, create a new event.
            while ((line = br.readLine()) != null) {
                Event event = new Event(line);
                // If the event name is valid, then add it to the list.
                if (!(event.getName()).equals(event.KEY_END)) {
                  events.add(event);
                }
            }
            br.close();
        } catch (IOException e) { // Print error if exists.
            e.printStackTrace();
        }

        // Return the populated list.
        return events;
    }

    /**
     * Creates a user and adds it to the ArrayList.
     *
     * @param trn transaction string containing the user data.
     * @param users the array list to add to.
     */
    public static void createUser(String trn, ArrayList<User> users) {
        User user = new User(trn);
        users.add(user);
    }

    /**
     * Deletes the object that matches the given transaction string.
     *
     * @param trn transaction string containing data to delete.
     * @param users search through this array list and delete the right object.
     */
    public static void deleteUser(String trn, ArrayList<User> users) {
        // Go through the list.
        for (int i = 0; i < users.size(); i++) {
            // If the data matches, remove it.
            if (trn.equals(users.get(i).toTRN())) {
                users.remove(i);
                return; // assume there's only one matching object
            }
        }
    }

    /**
     * Creates an event and adds it to the ArrayList.
     *
     * @param trn transaction string containing the event data.
     * @param events the array list to add to.
     */
    public static void createEvent(String trn, ArrayList<Event> events) {
        Event event = new Event(trn);
        events.add(event);
    }

    /**
     * Deletes the object that matches the given transaction string.
     *
     * @param trn transaction string containing data to delete.
     * @param events search through this array list and delete the right object.
     */
    public static void deleteEvent(String trn, ArrayList<Event> events) {
        // Go through the list.
        for (int i = 0; i < events.size(); i++) {
            // If the data matches, remove it.
            if (trn.equals(events.get(i).toTRN())) {
                events.remove(i);
                return; // assume there's only one matching object
            }
        }
    }

    /**
    * @brief To update a current user's available credit based on the specified amount indiciated by the
             transaction string of a current user.
    * @param [in] trn    A string representation of a transaction from the merged Daily transaction File (DTF)
                  users  An ArrayList object that contains all the user's on the Current User Accounts File
    * @return void
    */
    public void addCredit(String trn, ArrayList<User> users) {
      // method to add credit to a user based on the information provided from the array list and transaction

      if (trn.length() != 31) {
        System.err.println("Transaction length is incorrect");
        System.exit(0);
      } else {
        String username = trn.substring(3, 18);
        String credit = trn.substring(22, 31);
        for (int i=0; i<users.size(); i++) {
          if (username.equals(users.get(i).paddUsername())) {
            users.get(i).setCredit(Double.parseDouble(credit));
          } else {
            System.out.println("Error: seems user doesn't exist in the system.");
            }
          }
       }
    }

    /**
    * @brief To update a buyer's available credit and seller's available credit based on the specified amount
             indiciated by the transaction string of a current user.
    * @param [in] trn    A string representation of a transaction from the merged Daily transaction File (DTF)
                  users  An ArrayList object that contains all the user's on the Current User Accounts File
    * @return void
    */
    public void refundCredit(String buyer, String seller, String credit, ArrayList<User> users) {
      // method to refund credit to a user based on the information provided from the array list and transaction
      boolean checkBuyer = true;
      boolean checkSeller =  true;

      // find the matching user object from list
        for (int i=0; i<users.size(); i++) {
          if (checkBuyer) {
            if (buyer.equals(users.get(i).paddUsername())) {
              users.get(i).setCredit(users.get(i).getCredit() + Double.parseDouble(credit));
              checkBuyer = false;
            }
          }
          if (checkSeller) {
            if (seller.equals(users.get(i).paddUsername())) {
              users.get(i).setCredit(users.get(i).getCredit() - Double.parseDouble(credit));
              checkSeller = false;
            }
          }
        }
    }

    /**
    * @brief Applies the changes based on the id from each transaction line. Invokes necessary methods for each matched
             possble actions (create, delete, buy, sell, addcredit and refund).
    * @param [in] id     Identifies each transaction line based on either types of transactions.
    * @return void
    */
    public void enforceRules(String trn) {
      // identify which action to take
      String id = trn.substring(0, 2);
      // no need in checking the separator
      if (id != "00") {
        switch (id) {
          case "01":
            String cre_Harness_Trn = trn.substring(3, 31);
            System.out.println("Applying steps for create...");
            createUser(cre_Harness_Trn, this.users);
            System.out.println("Complete");
            break;

          case "02":
            String del_Harness_Trn = trn.substring(3, 31);
            System.out.println("Applying steps for delete!");
            deleteUser(del_Harness_Trn, this.users);
            System.out.println("Complete");
            break;

          case "03":
            String sel_Harness_Trn = trn.substring(3, 55);
            System.out.println("Applying steps for sell!");
            createEvent(sel_Harness_Trn, this.events);
            System.out.println("Complete");
            break;

          case "04":
            System.out.println("Applying steps for buy!");
              // still working on this

            System.out.println("Complete");
            break;

          case "05":
            System.out.println("Applying steps for refund!");
            String buyer = trn.substring(3, 18);
            String seller = trn.substring(19, 34);
            String credit = trn.substring(35, 44);
            refundCredit(buyer, seller, credit, this.users);
            System.out.println("Complete");
            break;

          case "06":
            System.out.println("Applying steps for addcredit!");
            addCredit(trn, this.users);
            System.out.println("Complete");
            break;
        }
      }
    }

      /**
    * @brief This is the main program logic. Also ensures standard saftey checks are met and also calls all necessary
             functions that result in modification of two files (user accounts & available tickets).
    * @param [in] args   Contains the supplied command-line-arguments, if any as an array of String objects.
    * @return void
    */
    public static void main(String[] args) {
      Controller c1 = new Controller("current_user_accounts.data", "available_tickets.data", parseUsers("current_user_accounts.data"), parseEvents("available_tickets.data"));

      //** step one: read in the mergedDTF fileName **
      try { File mergedDTF = new File("merged_DTF.data");
        FileReader dtf_fr = new FileReader(mergedDTF);
        BufferedReader dtf_br = new BufferedReader(dtf_fr);
        String trn_line;
        boolean sameFile = false;
        while ((trn_line = dtf_br.readLine()) != null) {
          //** step two: apply steps based on these per line instruction. **
          c1.enforceRules(trn_line);
        }
        dtf_br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      //** step three: to apply the changes to onto the both files **

      // UPDATING userAccounts file
      try {
        File userAccounts = new File("current_user_accounts.data");
        FileWriter uAcc_fw = new FileWriter(userAccounts);
        BufferedWriter uAcc_bw = new BufferedWriter(uAcc_fw);
        // whats going in to this file?
        for (User u : c1.users) {
          // applies changes to the userAccounts file
          uAcc_bw.write(u.toTRN() + "\n");
        }
        uAcc_bw.write("END                         ");
        // close these streams
        uAcc_bw.close();
      } catch(IOException e) {
        e.printStackTrace();
      }

      // UPDATING avaiabletickets file...
      try {
        File availableTickets = new File("available_tickets.data");
        FileWriter availTkts_fw = new FileWriter(availableTickets);
        BufferedWriter availTkts_bw = new BufferedWriter(availTkts_fw);
        // whats going in to this file?
        for (Event e : c1.events) {
          // applies changes to the availableTickets file
          availTkts_bw.write(e.toTRN() + '\n');
        }
        availTkts_bw.write("END                                                 ");
        availTkts_bw.close();
      } catch(IOException e) {
          e.printStackTrace();
      }
    }
}
