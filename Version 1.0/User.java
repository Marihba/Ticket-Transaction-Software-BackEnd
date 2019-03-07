/**
 * Write a description of class User here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class User extends Handler {
    private String username;
    private String userType;
    private double credit;

    public User(String trn) {
        username = parseData(trn, KEY_USER);
        userType = parseData(trn, KEY_TYPE);
        credit = Double.parseDouble(parseData(trn, KEY_CRED));
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public String getName() {
        return username;
    }

    public String getType() {
        return userType;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    /**
     * Format: U_T_C where U is the username, T is the account type, and
     * C is the credit, all separated with spaces.
     */
    public String toTRN() {
        String U = paddSpaces(KEY_USER, this.username);
        String T = this.userType;
        String C = paddSpaces(KEY_CRED, String.format("%.2f", this.credit));
        return U + " " + T + " " + C;
    }

    private String parseData(String trn, int dataType) {
        if (trn.equals(KEY_END)) {
            return "";
        }

        int end = LENGTH_USER;
        int offset = 0;
        boolean removeWhitespace = true;

        if (dataType == KEY_TYPE) {
            offset = end + 1;
            end = offset + LENGTH_TYPE;
            removeWhitespace = false;
        } else if (dataType == KEY_CRED) {
            offset = end + LENGTH_TYPE + 2;
            end = offset + LENGTH_CRED;
            removeWhitespace = false;
        }
        
        return parseValue(trn, removeWhitespace, offset, end);
    }
}