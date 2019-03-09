
/**
 * An abstract handler class containing useful functions to
 * help manipulate data.
 *
 * @author Stephanie Phung
 * @version 1.1
 */
public abstract class Handler {
    // Constants from the supposed format of the data files.
    protected static final int LENGTH_USER = 15;
    protected static final int LENGTH_TYPE = 2;
    protected static final int LENGTH_CRED = 9;
    protected static final int LENGTH_EVENT = 25;
    protected static final int LENGTH_TICKET = 3;
    protected static final int LENGTH_PRICE = 6;
    protected static final int KEY_USER = 1;
    protected static final int KEY_TYPE = 2;
    protected static final int KEY_CRED = 3;
    protected static final int KEY_EVENT = 4;
    protected static final int KEY_TICKET = 5;
    protected static final int KEY_PRICE = 6;
    protected static final String KEY_END = "END";

    /**
     * Returns a value with whitespace removed if necessary.
     *
     * @param trn transaction string of the data.
     * @param removeWhitespace true if we need to remove whitespace.
     * @param offset the beginning of the string value in the transaction string.
     * @param end points to the end of the string value in the transaction string.
     * @return String the trimmed value.
     */
    protected String parseValue(String trn, boolean removeWhitespace, int offset, int end) {
        if (removeWhitespace) {
            // Read from right to left.
            for (; end > offset + 1; end--) {
                // If the character is not a space, then return it.
                if (trn.charAt(end) != ' ') {
                    return trn.substring(offset, end + 1);
                }
            }
        }
        // Otherwise, return the value.
        return trn.substring(offset, end);
    }

    /**
     * Adds spaces or zeroes as padding to data, as necessary.
     *
     * @param dataType unique key of the type of data.
     * @param value the value of the data to padd.
     * @return String the padded data.
     */
    protected String paddSpaces(int dataType, String value) {
        if (value == "") { // if the value is empty, return it
            return value;
        }

        int specifiedLength = 0; // the correct length of the final value
        String paddedValue = ""; // the end result to return
        String paddingChar = " "; // default character to pad with is a space
        boolean padRight = true; // pad from the right
        boolean numerical = false; // true if value is numerical

        // Initializes the appropriate specified length.
        if (dataType == KEY_USER) {
            specifiedLength = LENGTH_USER;
        } else if (dataType == KEY_CRED) {
            specifiedLength = LENGTH_CRED;
            numerical = true;
        } else if (dataType == KEY_EVENT) {
            specifiedLength = LENGTH_EVENT;
        } else if (dataType == KEY_TICKET) {
            specifiedLength = LENGTH_TICKET;
            numerical = true;
        } else if (dataType == KEY_PRICE) {
            specifiedLength = LENGTH_PRICE;
            numerical = true;
        }

        // If the type of data is numerical, pad from the left,
        // and pad with zeroes instead of spaces.
        if (numerical) {
            padRight = false;
            paddingChar = "0";
        }

        // Calculates the necessary padding.
        int padding = specifiedLength - value.length();

        // Pads the word from either the right or the left.
        if (padRight) {
            paddedValue += value;
            for (int i = 0; i < padding; i++) {
                paddedValue += paddingChar;
            }
        }
        else if (!padRight) {
            for (int i = padding; i > 0; i--) {
                paddedValue += paddingChar;
            }
            paddedValue += value;
        }
        // Return the final string result.
        return paddedValue;
    }
}
