/**
* @file C:\Users\Abhiram96\Desktop\Winter 2019\Software Quality Assurance\Projects\Phase_Four\Project\RoughWork\controllermethods.java
* @author Abhiram Sinnarajah
* @date 6 Mar 2019
* @copyright 2019 Abhiram Sinnarajah
* @brief --
*/

public class controller {
  // member variables
  private String userFile;
  private String ticketFile;
  private ArrayList<User> users;
  private ArrayList<Event> events;

  // member methods
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
            user[i] -= credit.parseDouble();
          }
        }
      } else {
        System.error("Constraint Error: Transaction not apart of addcredit action")
      }
    }
  }

  // main method
  public static void main(String args[]) {
    System.out.println("helloWorld!!!!!");
  }
}

