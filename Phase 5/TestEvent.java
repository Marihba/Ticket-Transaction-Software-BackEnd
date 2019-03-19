import static org.junit.Assert.assertEquals;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit class containing all test cases to test all methods in
 * Event.java.
 *
 * @author Stephanie Phung
 * @version 1.2
 */
public class TestEvent extends TestCase {
    protected String eventName;
    protected String seller;
    protected int numTickets;
    protected double ticketPrice;
    protected String trn;
    protected Event event;
    
    @Before
    public void setUp() {
        eventName = "My Awesome Event";
        seller = "seller 123";
        numTickets = 5;
        ticketPrice = 25.95;
        trn = "My Awesome Event          seller 123      005 025.95";
    }

    @Test
    public void testCreateEvent() {
        event = new Event(trn);
        assertNotNull(event);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidEvent() {
        boolean thrown = false;
        try {
            trn = "END                                                 ";
            event = new Event(trn);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testEventGetName() {
        event = new Event(trn);
        assertEquals(eventName, event.getName());
    }

    @Test
    public void testEventGetTickets() {
        event = new Event(trn);
        assertEquals(numTickets, event.getTickets());
    }

    @Test
    public void testEventUpdateTickets() {
        event = new Event(trn);
        int num = 77;
        event.updateTickets(num);
        assertEquals(num, event.getTickets());
    }
    
    @Test
    public void testEventToTRN() {
        event = new Event(trn);
        assertEquals(trn, event.toTRN());
    }
    
    public void tearDown() {
    }
}
