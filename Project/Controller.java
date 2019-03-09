/**
* @file csci3060_team_surprised_pikachu\Version 1.0\Controller.java
* @author  Stephanie Phung
* @author  Abhiram Sinnarajah
* @author  Aaron Williams
* @date 9 Mar 2019
* @version 1.0.2
* @brief Runs the main program logic of the back end.
*/
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Controller {
    // member variables
    private String userFile;
    private String ticketFile;
    private ArrayList<User> users;
    private ArrayList<Event> events;

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
    public void addCredit(string trn, ArrayList<User> users) {
      // method to add credit to a user based on the information provided from the array list and transaction

      if (trn.length() != 31) {
        System.error.println("Transaction length is incorrect");
        System.exit(0);
      } else {
        // here I am parsing the string whih are information needed to create a new User object.
        String id, username, userType, credit;
        id = trn.substring(0, 2);
        username = trn.substring(3, 18);
        userType = trn.substring(19, 21);
        credit = trn.substring(22, 31);

        // another safety check
        if (id == "06") {
          // find the matching user object from list
          for (int i=0; i<users.length; i++) {
            if (users[i] == username) {
              user[i].setCredit += credit.parseDouble();
            }
          }
        } else {
          System.error("Constraint Error: Transaction not apart of addcredit action")
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
    public void refundCredit(string trn, ArrayList<User> users) {
      // method to refund credit to a user based on the information provided from the array list and transaction

      if (trn.length() != 45) {
        System.error.println("Transaction length is incorrect");
        System.exit(0);
      } else {
        String id, buyer, seller, credit;
        id = trn.substring(0, 2);
        buyer = trn.substring(3, 18);
        seller = trn.substring(18, 33);
        credit = trn.substring(34, 45);

        // another safety check
        if (id == "05") {
          // find the matching user object from list
          for (int i=0; i<users.length; i++) {
            // if username matches buyer, apply changes
            if (users[i] == buyer) {
              user[i].setCredit += credit.parseDouble();
            }
            // if username matches seller, apply changes
            if (users[i] == seller) {
              user[i].setCredit -= credit.parseDouble();
            }
          }
        } else {
          System.error("Fatal Error: Transaction not apart of addcredit action");
  	      System.exit(0);
        }
      }
    }

    /**
    * @brief Applies the changes based on the id from each transaction line. Invokes necessary methods for each matched
             possble actions (create, delete, buy, sell, addcredit and refund).
    * @param [in] id     Identifies each transaction line based on either types of transactions.
    * @return void
    */
    public static void enforceRules(String id) {
      switch (id) {
        case "01":
          // will include actions here for create
          System.out.println("Applying steps for create!");
          break;

        case "02":
          // will include actions here for delete
          System.out.println("Applying steps for delete!");
          break;

        case "03":
          // will include actions here for sell
          System.out.println("Applying steps for sell!");
          break;

        case "04":
          // will include actions here for v
          System.out.println("Applying steps for buy!");
          break;

        case "05":
          // will include actions here for refund
          System.out.println("Applying steps for refund!");
          break;

        case "06":
          // will include actions here for addcredit
          System.out.println("Applying steps for addcredit!");
          break;
      }
    }

      /**
    * @brief This is the main program logic. Also ensures standard saftey checks are met and also calls all necessary
             functions that result in modification of two files (user accounts & available tickets).
    * @param [in] args   Contains the supplied command-line-arguments, if any as an array of String objects.
    * @return void
    */
    public static void main(String[] args) {
        // step one: read in the mergedDTF fileName
        try { File mergedDTF = new File("merged_DTF.data");
          FileReader dtf_fr = new FileReader(mergedDTF);
          BufferedReader dtf_br = new BufferedReader(dtf_fr);
          String trn_line;
          while ((trn_line = dtf_br.readLine()) != null) {
            // step two: apply steps based on these per line instruction.
            enforceRules(trn_line.substring(0, 2));
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      // step three: to apply the changes to onto the both files
      // todo: create stream to transfter changes onto two new files
    }
}
