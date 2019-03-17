import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

public class TestUser extends TestCase {
    protected String username;
    protected String accountType;
    protected double credit;
    protected String trn;
    
    @Before
    public void setUp() {
        username = "admin";
        accountType = "AA";
        credit = 3456.89;
        trn = "admin           AA 003456.89";
    }

    @Test
    public void testGetName() {
        User user = new User(trn);
        assertEquals(username, user.getName());
    }
    
    public void tearDown() {
    }
}
