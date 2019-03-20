import static org.junit.Assert.assertEquals;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit class containing all test cases to test functionality of
 * Controller.java
 *
 * @author Stephanie Phung
 * @version 1.0
 */
public class TestController extends TestCase {
    protected Controller controller;
    protected String userFile;
    protected String ticketFile;
    
    @Before
    public void setUp() {
        userFile = "test_current_user_accounts.data"; 
        ticketFile = "test_available_tickets.data";      
    }
    
    @Test
    public void testCreateController() {
        controller = new Controller(userFile, ticketFile);
        assertNotNull(controller);
    }
    
    public void tearDown() {
    }
}
