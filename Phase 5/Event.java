
/**
 * A class to manipulate Event objects.
 *
 * @author Stephanie Phung
 * @version 1.2
 */
public class Event extends Handler {
    // Private variables for an Event object.
    private String eventName;
    private String seller;
    private int numTickets;
    private double ticketPrice;

    /**
     * Creates an event using a string transaction.
     *
     * @param trn the transaction string container all event data.
     */
    public Event(String trn) {
        if (trn.trim().equals(KEY_END)) {
            throw new IllegalArgumentException("Invalid trn: \"" + trn + "\".");
        } else {
            eventName = parseData(trn, KEY_EVENT);
            seller = parseData(trn, KEY_USER);
            numTickets = Integer.parseInt(parseData(trn, KEY_TICKET));
            ticketPrice = Double.parseDouble(parseData(trn, KEY_PRICE)); 
        }
    }

    /**
     * Returns the name of the event.
     *
     * @return String the name of the event.
     */
    public String getName() {
        return eventName;
    }

    /**
     * Returns the number of tickets available.
     *
     * @return int the number of available tickets.
     */
    public int getTickets() {
        return numTickets;
    }

    /**
     * Sets number of tickets.
     *
     * @param num the new number of tickets for sale.
     */
    public void updateTickets(int num) {
        numTickets = num;
    }

    /**
     * Returns the details of the current event in the form of a transaction.
     * Format: E_S_T_P where E is the event title, S is the seller's username,
     * T is the number of tickets, and P is the price per ticket, with spaces
     * between the values.
     */
    public String toTRN() {
        // Make sure the strings are formatted properly before returning all the data.
        String E = paddSpaces(KEY_EVENT, this.eventName);
        String S = paddSpaces(KEY_USER, this.seller);
        String T = paddSpaces(KEY_TICKET, String.valueOf(this.numTickets));
        String P = paddSpaces(KEY_PRICE, String.format("%.2f", this.ticketPrice));
        return E  + " " + S + " " + T + " " + P;
    }

    /**
     * Parses a transaction string for the specified type of data.
     *
     * @param trn transaction string to parse.
     * @param dataType the key for the type of data to parse.
     * @return String the value of the requested data.
     */
    private String parseData(String trn, int dataType) {
        int end = LENGTH_EVENT; // points to the end of the string
        int offset = 0; // points to the beginning of the string
        boolean removeWhitespace = true; // typically used for non-numerical data

        // Set the offset for the type of data.
        // This is set based on the format of the file.
        if (dataType == KEY_USER) { // if returning username of seller
            offset = end + 1;
            end = offset + LENGTH_USER;
        } else if (dataType == KEY_TICKET) { // if returning number of tickets for sale
            offset = end + LENGTH_USER + 2;
            end = offset + LENGTH_TICKET;
            removeWhitespace = false;
        } else if (dataType == KEY_PRICE) { // if returning price per ticket
            offset = end + LENGTH_USER + LENGTH_TICKET + 3;
            end = offset + LENGTH_PRICE;
            removeWhitespace = false;
        }

        // Returns the requested data.
        return parseValue(trn, removeWhitespace, offset, end);
    }
}
