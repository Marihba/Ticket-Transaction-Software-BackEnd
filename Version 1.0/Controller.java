import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * Runs the main program logic of the back end.
 *
 * @author Stephanie Phung
 * @author
 * @version 1.0
 */
public class Controller{
    // Private class variables for the Controller class.
    private String userFile;
    private String ticketFile;
    private ArrayList<User> users;
    private ArrayList<Event> events;

    public static void main() {
        // temp demo code
        ArrayList<User> users = parseUsers("current_user_accounts.data");
        System.out.println("Saved:");
        for (User u : users) {
            System.out.println(u.toTRN());
        }
        
        ArrayList<Event> events = parseEvents("available_tickets.data");
        System.out.println("Saved:");
        for (Event e : events) {
            System.out.println(e.toTRN());
        }
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
}
