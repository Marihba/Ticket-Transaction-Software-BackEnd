import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * Write a description of class main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Controller{
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
    }

    public static ArrayList<User> parseUsers(String fileName) {
        ArrayList<User> users = new ArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("END")) {
                    User user = new User(line);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static ArrayList<Event> parseEvent(String fileName) {
        ArrayList<Event> events = new ArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("END")) {
                    Event event = new Event(line);
                    events.add(event);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }

    public static void createUser(String trn, ArrayList<User> users) {
        User user = new User(trn);
        users.add(user);
    }

    public static void deleteUser(String trn, ArrayList<User> users) {
        for (int i = 0; i < users.size(); i++) {
            if (trn.equals(users.get(i).toTRN())) {
                users.remove(i);
                return;
            }
        }
    }

    public static void createEvent(String trn, ArrayList<Event> events) {
        Event event = new Event(trn);
        events.add(event);
    }

    public static void deleteEvent(String trn, ArrayList<Event> events) {
        for (int i = 0; i < events.size(); i++) {
            if (trn.equals(events.get(i).toTRN())) {
                events.remove(i);
                return;
            }
        }
    }
}
