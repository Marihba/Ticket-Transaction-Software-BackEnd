import static org.junit.Assert.assertEquals;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test case class containing all test cases to test 
 * all methods in Event.java.
 *
 * @author Stephanie Phung
 * @version 2.0
 */
public class TestEvent extends TestCase {
    // Variables to mimic private variables stored in Event.
    protected String eventName;
    protected String seller;
    protected int numTickets;
    protected double ticketPrice;

    // Variables User interacts with.
    protected String trn;

    // Instance of objects necessary for testing.
    protected Event event;
    
    /**
     * Initialize required variables before running test cases.
     */
    @Before
    public void setUp() {
        eventName = "My Awesome Event         ";
        seller = "seller 123     ";
        numTickets = 5;
        ticketPrice = 25.95;
        trn = "My Awesome Event          seller 123      005 025.95";
    }

    /**
     * Create a new Event with a valid transaction string
     * and assert that it is not null.
     */
    @Test
    public void testCreateEvent() {
        event = new Event(trn);
        assertNotNull(event);
    }

    /**
     * Try to create an Event with an invalid transaction string
     * and make sure an IllegalArgumentException is thrown.
     */
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

    /**
     * Ensure that getName() returns the correct value.
     */
    @Test
    public void testEventGetName() {
        event = new Event(trn);
        assertEquals(eventName, event.getName());
    }

    /**
     * Ensure that getTickets() returns the correct value.
     */
    @Test
    public void testEventGetTickets() {
        event = new Event(trn);
        assertEquals(numTickets, event.getTickets());
    }

    /**
     * Ensure that number of tickets are updated correctly.
     */
    @Test
    public void testEventUpdateTickets() {
        event = new Event(trn);
        int num = 77;
        event.updateTickets(num);
        assertEquals(num, event.getTickets());
    }
    
    /**
     * Ensure that toTRN() returns the correct value.
     */
    @Test
    public void testEventToTRN() {
        event = new Event(trn);
        assertEquals(trn, event.toTRN());
    }
    
    public void tearDown() {
    }
}
