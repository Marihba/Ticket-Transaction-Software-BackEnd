import static org.junit.Assert.assertEquals;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit class containing all test cases to test all methods in
 * User.java.
 *
 * @author Stephanie Phung
 * @version 1.2
 */
public class TestUser extends TestCase {
    protected String username;
    protected String accountType;
    protected double credit;
    protected String trn;
    protected User user;
    
    @Before
    public void setUp() {
        username = "admin";
        accountType = "AA";
        credit = 3456.89;
        trn = "admin           AA 003456.89";
    }
    
    @Test
    public void testCreateUser() {
        user = new User(trn);
        assertNotNull(user);
    }
    
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

    @Test
    public void testUserGetName() {
        user = new User(trn);
        assertEquals(username, user.getName());
    }
    
    @Test
    public void testUserGetType() {
        user = new User(trn);
        assertEquals(accountType, user.getType());
    }
    
    @Test
    public void testUserGetCredit() {
        user = new User(trn);
        assertEquals(credit, user.getCredit());
    }
    
    @Test
    public void testUserSetCredit() {
        user = new User(trn);
        double newCredit = 123.45;
        user.setCredit(newCredit);
        assertEquals(newCredit, user.getCredit());
    }
    
    @Test
    public void testUserToTRN() {
        user = new User(trn);
        assertEquals(trn, user.toTRN());
    }

    @Test
    public void testUserPaddUsername() {
        trn = "admin           AA 003456.89";
        user = new User(trn);
        String paddedUsername = "admin          ";
        assertEquals(paddedUsername, user.paddUsername());
    }
    
    public void tearDown() {
    }
}
