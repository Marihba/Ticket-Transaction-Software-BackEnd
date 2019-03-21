import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import jdk.jfr.Timestamp;

/**
 * JUnit class containing all test cases to test functionality of
 * Controller.java
 *
 * @author Aaron
 * @author Abhiram
 * @version 1.0
 */
public class TestController extends TestCase {
    private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream testErr = new ByteArrayOutputStream();
    private final PrintStream ogOut = System.out;
    private final PrintStream ogErr = System.err;

    protected Controller controller;
    protected String userFile;
    protected String ticketFile;
    protected String testUser;
    protected String testEvent;
    protected String firstUserName;
    protected String firstEventName;
    protected String emptyUser;
    protected String emptyEvent;
    
    @Before
    public void setUp() {
        userFile = "test_current_user_accounts.data"; 
        ticketFile = "test_available_tickets.data"; 
        emptyUser = "empty_user.data";
        emptyEvent = "empty_tics.data";
        testEvent = "My Awesome Event          seller          002 050.01";     
        testUser = "test1           AA 000000.00";
        firstUserName = "admin";
        firstEventName = "My Awesome Event";
    }
    @Before
    public void setupStreams() {
    System.setOut(new PrintStream(testOut));
    System.setErr(new PrintStream(testErr));
    }

    @After
    public void restoreStreams() {
      System.setOut(ogOut);
      System.setErr(ogErr);
    }
    
    @Test
    public void testConstructor() {
        controller = new Controller(userFile, ticketFile);
        assertNotNull(controller);
    }

    @Test
    public void testUserCreate() {
      controller = new Controller(emptyUser, emptyEvent);
      // add a user to list using create
      controller.create(testUser);
      assertNotNull(controller.users);
    }

    @Test
    public void testEventCreate() {
      controller = new Controller(emptyUser, emptyEvent);
      // add a event to list using create
      controller.create(testEvent);
      assertNotNull(controller.events); 
    }

    @Test
    public void testDupUser(){
      controller = new Controller(emptyUser, emptyEvent);
      controller.create(testUser);
      controller.create(testUser);
      assertEquals("ERROR: User already exists", testErr.toString());
    }

    @Test
    public void testDupEvent(){
      controller = new Controller(emptyUser, emptyEvent);
      controller.create(testEvent);
      controller.create(testEvent);
      assertEquals("ERROR: Event already exists", testErr.toString());
    }

    @Test
    public void testDelete() {
      controller = new Controller(emptyUser, emptyEvent);
      controller.create(testUser);
      controller.delete(testUser, controller.users);
      
      assertTrue(controller.users.isEmpty());
    }
    
    @Test
    public void testSave() {
      controller = new Controller(emptyUser, emptyEvent);
      controller.create(testUser);
      controller.create(testEvent);
      controller.save();

      FileReader userCheck = new FileReader(emptyUser);
      assertTrue(userCheck.ready());
      userCheck.close();

    @Test
    public void testBadWrite() {}
    
    public void tearDown() {
    }
}
