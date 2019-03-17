/**
 * A class to create and store all details for a User object.
 *
 * @author Stephanie Phung
 * @version 1.1
 */
public class User extends Handler {
    // Private class variables for the User object.
    private String username;
    private String userType;
    private double credit;

    /**
     * Creates a user using a string transaction.
     *
     * @param trn the transaction string container all user data.
     */
    public User(String trn) {
        username = parseData(trn, KEY_USER);
        if (!username.equals(KEY_END)) {
            userType = parseData(trn, KEY_TYPE);
            credit = Double.parseDouble(parseData(trn, KEY_CRED));
        }
    }

    /**
     * Returns the name of the user.
     *
     * @return String the name of the user.
     */
    public String getName() {
        return username;
    }

    /**
     * Returns the type of user.
     *
     * @return String type of user.
     */
    public String getType() {
        return userType;
    }

    /**
     * Returns the total credit of a user.
     *
     * @return double the total credit.
     */
    public double getCredit() {
        return credit;
    }

    /**
     * Sets the total credit of a user.
     *
     * @param credit the new credit amount.
     */
    public void setCredit(double credit) {
        this.credit = credit;
    }

    /**
     * Returns the details of the current user in the form of a transaction.
     * Format: U_T_C where U is the username, T is the account type, and
     * C is the credit, all separated with spaces.
     *
     * @return String the transaction string of the user data.
     */
    public String toTRN() {
        // Make sure the strings are formatted properly before returning all the data.
        String U = paddSpaces(KEY_USER, this.username);
        String T = this.userType;
        String C = paddSpaces(KEY_CRED, String.format("%.2f", this.credit));
        return U + " " + T + " " + C;
    }

    /**
     * Parses a transaction string for the specified type of data.
     *
     * @param trn transaction string to parse.
     * @param dataType the key for the type of data to parse.
     * @return String the value of the requested data.
     */
    private String parseData(String trn, int dataType) {
        int end = LENGTH_USER; // points to the end of the string
        int offset = 0; // points to the beginning of the string
        boolean removeWhitespace = true; // typically used for non-numerical data

        // Set the offset for the type of data.
        // This is set based on the format of the file.
        if (dataType == KEY_TYPE) { // if returning user account type
            offset = end + 1;
            end = offset + LENGTH_TYPE;
            removeWhitespace = false;
        } else if (dataType == KEY_CRED) { // if returning credit amount
            offset = end + LENGTH_TYPE + 2;
            end = offset + LENGTH_CRED;
            removeWhitespace = false;
        }

        // Returns the requested data.
        return parseValue(trn, removeWhitespace, offset, end);
    }

    // for testing purposes only
    public String toString() {
      return this.username + " " + this.userType + " " + this.credit;
    }

    public String paddUsername() {
      return paddSpaces(KEY_USER, this.username);
    }
}
