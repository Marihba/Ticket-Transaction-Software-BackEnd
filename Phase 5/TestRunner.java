import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * JUnit Runner class to execute all the test cases.
 *
 * @author Stephanie Phung
 * @version 1.2
 */
public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(TestUser.class, TestEvent.class);
		
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      if(result.wasSuccessful()) {
         System.out.println("All tests passed successfully.");
      }
   }
}  
