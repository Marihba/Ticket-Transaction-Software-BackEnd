
/**
 * Write a description of class Event here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Event extends Handler {
    private String eventName;
    private String seller;
    private int numTickets;
    private double ticketPrice;
    
    public Event(String trn) {
        eventName = parseData(trn, KEY_EVENT);
        seller = parseData(trn, KEY_USER);
        numTickets = Integer.parseInt(parseData(trn, KEY_TICKET));
        ticketPrice = Double.parseDouble(parseData(trn, KEY_PRICE));
    }    

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int getTickets() {
        return numTickets;
    }
    
    public void updateTickets(int num) {
        numTickets = num;
    }
    
    /**
     * Format: E_S_T_P where E is the event title, S is the seller's username,
     * T is the number of tickets, and P is the price per ticket, with spaces
     * between the values.
     */
    public String toTRN() {
        String E = paddSpaces(KEY_EVENT, this.eventName);
        String S = paddSpaces(KEY_USER, this.seller);
        String T = paddSpaces(KEY_TICKET, String.valueOf(this.numTickets));
        String P = paddSpaces(KEY_PRICE, String.format("%.2f", this.ticketPrice));
        return E  + " " + S + " " + T + " " + P;
    }
    
    private String parseData(String trn, int dataType) {
        if (trn.equals(KEY_END)) {
            return "";
        }

        int end = LENGTH_EVENT;
        int offset = 0;
        boolean removeWhitespace = true;

        if (dataType == KEY_USER) {
            offset = end + 1;
            end = offset + LENGTH_USER;
        } else if (dataType == KEY_TICKET) {
            offset = end + LENGTH_USER + 2;
            end = offset + LENGTH_TICKET;
            removeWhitespace = false;
        } else if (dataType == KEY_PRICE) {
            offset = end + LENGTH_USER + LENGTH_TICKET + 3;
            end = offset + LENGTH_PRICE;
            removeWhitespace = false;
        }
        
        return parseValue(trn, removeWhitespace, offset, end);
    }
}
