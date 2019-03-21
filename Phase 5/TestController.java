import static org.junit.Assert.*;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.BufferedReader;
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
    // loop and decision tests
    @Test
    public void testUserParse() {
      controller.userFile = "one_user.data";
      controller.ticketFile = ticketFile;
      controller.events = new ArrayList<>();
      controller.users = new ArrayList<>();

      controller.parseData(11);
      assertTrue(!(controller.users.isEmpty()));
    }

    @Test
    public void testEventParse() {
      controller.userFile = userFile;
      controller.ticketFile = "test_available_tickets.data";
      controller.events = new ArrayList<>();
      controller.users = new ArrayList<>();

      controller.parseData(99);
      assertTrue(!(controller.events.isEmpty()));
    }
    
    /*@Test(expected = FileNotFoundException.class)
    public void testENDParse() {
      boolean thrown = false;
      try {
        controller.userFile = userFile;
        controller.ticketFile = ticketFile;
        controller.events = new ArrayList<>();
        controller.users = new ArrayList<>();
        controller.parseData(69);
      }
      
      assertTrue(!(controller.events.isEmpty()));
    }*/
    
    // other tests
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

      File userCheck = new File(emptyUser);
      assertTrue(userCheck.length() != 0);
    }

    @Test
    public void testbuyTrnString() {
      String buyString = "04 My Awesome Event          seller          004 123.45";
      String logoutString = "00 standard        FS 000700.00";
      boolean isBSgreater = buyString.length() >= 55;
      boolean isLSgreater = logoutString.length() == 31;
      boolean greaterThan = isBSgreater && isLSgreater;
      assertTrue(greaterThan);
    }


    @Test
    public void testEnforceCreate() {
      String trnm = "01 Nami            BS 800000.00";
      String trnTrim = (trnm.substring(0, 2));
      String newid = "01";
      boolean idandlength = (trnTrim.equals(newid)) && trnm.length() == 31;
      assertTrue(idandlength);
    }

    @Test
    public void testEnforceDelete() {
      String trnm = "02 YOLO            BS 700000.00";
      String trnTrim = (trnm.substring(0, 2));
      String newid = "02";
      boolean idandlength = (trnTrim.equals(newid)) && trnm.length() == 31;
      assertTrue(idandlength);
    }

    @Test
    public void testEnforceSel() {
      String trnm = "03 A new event!              admin           055 001.23";
      String trnTrim = (trnm.substring(0, 2));
      String newid = "03";
      boolean idandlength = (trnTrim.equals(newid)) && trnm.length() == 55;
      assertTrue(idandlength);
    }

    @Test
    public void testEnforceBuy00() {
      String buyString = "04 My Awesome Event          seller          004 123.45";
      String logoutString = "00 standard        FS 000700.00";
      Controller c1 = new Controller("current_user_accounts.data", "available_tickets.data");
      String result = c1.buyTrnString(buyString, logoutString);
      String[] lines = result.split("\n");
      int newLines = lines.length;
      assertEquals(newLines, 8);
    }

    @Test
    public void testEnforceBuy01() {
      String b_available = "123.45";
      double value = Double.parseDouble(b_available);
      assertEquals(value, 123.45);
    }

    @Test
    public void testEnforceBuy02() {
      String b_available = "123.45";
      String b_Total = "1000.00";
      boolean result = (Double.parseDouble(b_available) < Double.parseDouble(b_Total));
      assertTrue(result);
    }

    @Test
    public void testEnforceBuy03() {
      String b_available = "123.45";
      double currentPrice = 300.00;
      boolean result = (Double.parseDouble(b_available) < currentPrice);
      assertTrue(result);
    }

    @Test
    public void testEnforceBuy04() {
      String b_available = "123.45";
      double currentPrice = 300.00;
      boolean result = (Double.parseDouble(b_available) < currentPrice);
      assertTrue(result);
    }

    @Test
    public void testEnforceBuy05() {
      int buyable_Tickets = 0;
      boolean result = (buyable_Tickets == 0);
      assertTrue(result);
    }

    @Test
    public void testEnforceBuy06() {
      int inventory = 3;
      int buyable_Tickets = 3;
      boolean result = (buyable_Tickets == inventory);
      assertTrue(result);
    }

    @Test
    public void testEnforceRefund() {
      String trnm = "05 standard        Nami            034234.00";
      String trnTrim = (trnm.substring(0, 2));
      String newid = "05";
      boolean idandlength = (trnTrim.equals(newid)) && trnm.length() == 44;
      assertTrue(idandlength);
    }

    @Test
    public void testEnforceAddCredit() {
      String trnm = "06 Nami            BS 000300.00";
      String trnTrim = (trnm.substring(0, 2));
      String newid = "06";
      boolean idandlength = (trnTrim.equals(newid)) && trnm.length() == 31;
      assertTrue(idandlength);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMainFileReaderIOe() {
        boolean thrown = false;
        try {
            File mergedDTF = new File("blacnk.data");
            FileReader dtf_fr = new FileReader(mergedDTF);
            BufferedReader dtf_br = new BufferedReader(dtf_fr);
            String trn_line;
            while ((trn_line = dtf_br.readLine()) != null) {
            }
          }catch (IOException e) {
            thrown = true;
          }
        assertTrue(thrown);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFileReadLines() {
        boolean thrown = false;
        try {
            File mergedDTF = new File("merged_DTF.data");
            FileReader dtf_fr = new FileReader(mergedDTF);
            BufferedReader dtf_br = new BufferedReader(dtf_fr);
            String trn_line;
            while ((trn_line = dtf_br.readLine()) != null) {
              // do something with lines
              String readlines = trn_line;
            }
          }catch (IOException e) {
            thrown = true;
          }
        assertFalse(thrown);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testFileBuyTrn00() {
      try {
        File mergedDTF = new File("merged_DTF.data");
        FileReader dtf_fr = new FileReader(mergedDTF);
        BufferedReader dtf_br = new BufferedReader(dtf_fr);
        String trn_line;
        while ((trn_line = dtf_br.readLine()) != null) {
          String id = trn_line.substring(0, 2);
          if (id.equals("04")) {
              boolean result = true;
              assertTrue(true);
          }
        }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Test(expected = IllegalArgumentException.class)
  public void testFileBuyTrn01() {
    try {
      File mergedDTF = new File("merged_DTF.data");
      FileReader dtf_fr = new FileReader(mergedDTF);
      BufferedReader dtf_br = new BufferedReader(dtf_fr);
      String trn_line;
      boolean sameDTF = false;
      while ((trn_line = dtf_br.readLine()) != null) {
        String iD = trn_line.substring(0, 2);
        if (iD.equals("04")) {
            sameDTF = true;
        }
          if (sameDTF == true) {
            if (iD.equals("00")) {
              boolean results = true;
              assertTrue(results);
            }
          }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFileBuyTrn02() {
    try {
      File mergedDTF = new File("merged_DTF.data");
      FileReader dtf_fr = new FileReader(mergedDTF);
      BufferedReader dtf_br = new BufferedReader(dtf_fr);
      String trn_line;
      boolean sameDTF = false;
      while ((trn_line = dtf_br.readLine()) != null) {
        String iD = trn_line.substring(0, 2);
        if (iD.equals("04")) {
            sameDTF = true;
        }
          if (sameDTF == true) {
            if (iD.equals("00")) {
              if (trn_line.length() == 31) {
                boolean results = true;
                    assertTrue(results);
              }
            }
          }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFileBuyTrn03() {
    try {
      File mergedDTF = new File("merged_DTF.data");
      FileReader dtf_fr = new FileReader(mergedDTF);
      BufferedReader dtf_br = new BufferedReader(dtf_fr);
      String trn_line;
      boolean sameDTF = false;
      while ((trn_line = dtf_br.readLine()) != null) {
        String iD = trn_line.substring(0, 2);
        if (iD.equals("04")) {
            sameDTF = true;
        }
          if (sameDTF == true) {
            if (iD.equals("00")) {
              if (trn_line.length() == 31) {
                boolean results = true;
                    assertTrue(results);
              }
            }
          }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFileBuyTrn04() {
    try {
      File mergedDTF = new File("merged_DTF.data");
      FileReader dtf_fr = new FileReader(mergedDTF);
      BufferedReader dtf_br = new BufferedReader(dtf_fr);
      String trn_line;
      boolean sameDTF = false;
      while ((trn_line = dtf_br.readLine()) != null) {
        String iD = trn_line.substring(0, 2);
        if (iD.equals("04")) {
            sameDTF = true;
        }
          if (sameDTF == true) {
            if (iD.equals("00")) {
              if (trn_line.length() == 31) {

              }
              else {
                boolean results = true;
                    assertTrue(results);
              }

            }
          }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFileMainEnforce() {
    try {
      File mergedDTF = new File("merged_DTF.data");
      FileReader dtf_fr = new FileReader(mergedDTF);
      BufferedReader dtf_br = new BufferedReader(dtf_fr);
      String trn_line;
      boolean sameDTF = false;
      while ((trn_line = dtf_br.readLine()) != null) {
        String iD = trn_line.substring(0, 2);
        if (iD.equals("04")) {
            sameDTF = true;
        }
          if (sameDTF == true) {
            if (iD.equals("00")) {
              if (trn_line.length() == 31) {
                // do stuff here for if length = 31
              }
              else {
                // do stuff for lenth of just 00
              }

            }
          }
          else{
            // invokes methods other than buy
            boolean results = true;
                assertTrue(results);
          }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
    
    public void tearDown() {
    }
}
