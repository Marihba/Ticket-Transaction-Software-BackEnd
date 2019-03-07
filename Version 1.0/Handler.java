
/**
 * Abstract class Handler - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Handler {
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

    protected String parseValue(String trn, boolean removeWhitespace, int offset, int end) {
        if (removeWhitespace) {
            for (; end > offset + 1; end--) {
                if (trn.charAt(end) != ' ') {
                    return trn.substring(offset, end + 1);
                }
            }
        } 
        return trn.substring(offset, end);
    }

    protected String paddSpaces(int dataType, String value) {
        if (value == "") {
            return value;
        }

        int specifiedLength = 0;
        String paddedValue = "";
        String paddingChar = " ";
        boolean padRight = true;
        boolean numerical = false;

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
        return paddedValue;
    }
}
