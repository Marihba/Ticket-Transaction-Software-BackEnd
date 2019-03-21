import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * JUnit Runner class to execute all the test cases.
 *
 * @author Stephanie Phung
 * @version 2.0
 */
public class TestRunner {
    public static void main(String[] args) {
        // Run tests in the following class files.
        Result result = JUnitCore.runClasses(TestUser.class, TestEvent.class, TestController.class);
        
        // For every failure, if exists, print out the
        // cause of the error (failed test and details).
        for (Failure failure : result.getFailures()) {
            System.out.println("Test failed: " + failure.toString());
        }
        
        // Else, assume all tests passed.
        if (result.wasSuccessful()) {
            System.out.println("All test passed successfully.");
        }
    }
}
