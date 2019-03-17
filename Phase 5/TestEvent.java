import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

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
        event = new Event(trn);
    }

    @Test
    public void testGetName() {
        assertEquals(eventName, event.getName());
    }

    @Test
    public void testGetTickets() {
        assertEquals(numTickets, event.getTickets());
    }
    
    public void tearDown() {
    }
}
