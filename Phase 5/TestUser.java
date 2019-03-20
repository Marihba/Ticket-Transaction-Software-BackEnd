import static org.junit.Assert.assertEquals;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test case class containing all test cases to test 
 * all methods in User.java.
 *
 * @author Stephanie Phung
 * @version 2.0
 */
public class TestUser extends TestCase {
    // Variables to mimic private variables stored in User.
    protected String username;
    protected String accountType;
    protected double credit;
    
    // Variables User interacts with.
    protected String trn;
    
    // Instance of objects necessary for testing.
    protected User user;

    /**
     * Initialize required variables before running test cases.
     */
    @Before
    public void setUp() {
        username = "admin          ";
        accountType = "AA";
        credit = 3456.89;
        trn = "admin           AA 003456.89";
    }

    /**
     * Create a new User with a valid transaction string
     * and assert that it is not null.
     */
    @Test
    public void testCreateUser() {
        user = new User(trn);
        assertNotNull(user);
    }

    /**
     * Try to create a User with an invalid transaction string
     * and make sure an IllegalArgumentException is thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidUser() {
        boolean thrown = false;
        try {
            trn = "END                         ";
            user = new User(trn);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * Ensure that getName() returns the correct value.
     */
    @Test
    public void testUserGetName() {
        user = new User(trn);
        assertEquals(username, user.getName());
    }

    /**
     * Ensure that getType() returns the correct value.
     */
    @Test
    public void testUserGetType() {
        user = new User(trn);
        assertEquals(accountType, user.getType());
    }

    /**
     * Ensure that getCredit() returns the correct value.
     */
    @Test
    public void testUserGetCredit() {
        user = new User(trn);
        assertEquals(credit, user.getCredit());
    }

    /**
     * Ensure that setCredit() returns the correct value.
     */
    @Test
    public void testUserSetCredit() {
        user = new User(trn);
        double newCredit = 123.45;
        user.setCredit(newCredit);
        assertEquals(newCredit, user.getCredit());
    }

    /**
     * Ensure that toTRN() returns the correct value.
     */
    @Test
    public void testUserToTRN() {
        user = new User(trn);
        assertEquals(trn, user.toTRN());
    }
    
    public void tearDown() {
    }
}
