import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class JUnitTesting  extends Permit_list {
    private Permit_list permit_list = new Permit_list();


    private Date date;

    @Test
    public void testCreateAndCheckPermit() {
        Permanent_visitor_permit testPvp = new Permanent_visitor_permit("John");
        permit_list.addPermit(testPvp);
        assertTrue("Permit with name John does not exist in current list", permit_list.checkNameExists("John"));

        Day_visitor_permit testDvp = new Day_visitor_permit("Hannah", date, "Chris");
        permit_list.addPermit(testDvp);
        assertTrue("Permit holder with name Hannah on this date hosted by Chris does not exist in current list", permit_list.checkNameExists("Hannah"));

        University_member_permit testUmp = new University_member_permit("Anna", date);
        permit_list.addPermit(testUmp);
        assertTrue("Permit holder with name Anna does not exist in this list", permit_list.checkNameExists("Anna"));

        Regular_visitor_permit testRvp = new Regular_visitor_permit("Alex", date, date, "Chris");
        permit_list.addPermit(testRvp);
        assertTrue("Permit holder with name Alexa does not exist in this list", permit_list.checkNameExists("Alex"));
    }


    @Test
    public void testListSize() {

        assertEquals(0, permit_list.getSize());
        Permanent_visitor_permit testPvp1 = new Permanent_visitor_permit("Thomas");
        permit_list.addPermit(testPvp1);
        assertEquals(1, permit_list.getSize());
        Permanent_visitor_permit testPvp2 = new Permanent_visitor_permit("John");
        permit_list.addPermit(testPvp2);
        assertEquals(2, permit_list.getSize());
        Permanent_visitor_permit testPvp3 = new Permanent_visitor_permit("James");
        permit_list.addPermit(testPvp3);
        assertEquals(3, permit_list.getSize());
    }

    @Test
    public void testRemovePermit() {
        Permanent_visitor_permit testPvp = new Permanent_visitor_permit("Chloe");
        permit_list.addPermit(testPvp);
        permit_list.removePermit("Chloe");
        assertFalse("Permit is not removed", permit_list.checkNameExists("Chloe"));
    }

    @Test
    public void testRemovePermit2() {
        Permanent_visitor_permit testPvp = new Permanent_visitor_permit("Joe");
        permit_list.addPermit(testPvp);
        permit_list.removePermit("Joe");
        assertTrue(true);
    }


    @Test
    public void testGetKeys() {
        Permanent_visitor_permit testPvp = new Permanent_visitor_permit("Joe");
        permit_list.addPermit(testPvp);
        LinkedList<String> emptyLinkedList = new LinkedList<String>(); //create an empty linked list
        assertNotEquals(emptyLinkedList, permit_list.getKeys()); // compare the linked list of permit keys in permit_list to an empty linked list
    }

    @Test
    public void testGetPermit(){
        Permanent_visitor_permit testPvp = new Permanent_visitor_permit("Adam");
        permit_list.addPermit(testPvp);
        assertNotNull(permit_list.getPermit("Adam"));
    }

    @Test
    public void testDailyUpdateAll() {
        Permanent_visitor_permit testPvp = new Permanent_visitor_permit("Jane");
        Vehicle_info testVehicle = new Vehicle_info("2", testPvp);

        testPvp.setVehicle(testVehicle);

        testPvp.increaseEntries();
        testPvp.setEnteredToday();

        System.out.println(testPvp.getVehicleUsedToday());
        System.out.println(testPvp.getEntries());

        permit_list.addPermit(testPvp);

        permit_list.dailyUpdateAll(); //reset all permits' vehicle used today and enteredToday

        System.out.println("-------------------");
        System.out.println(testPvp.getVehicleUsedToday()); //test that vehicle used today is reset
        System.out.println(testPvp.getEntries());
        assertNull("DailyUpdateAll() not updated successfully, vehicle used today is not reset", testPvp.getVehicleUsedToday());
    }

    @Test
    public void testAnnualUpdateAll() {
        Permanent_visitor_permit testPvp = new Permanent_visitor_permit("Jane");

        permit_list.addPermit(testPvp);
        testPvp.suspend();
        testPvp.addWarning();

        permit_list.annualUpdateAll(); //reset all permits' suspensions and warnings

        assertFalse("annualUpdateAll() did not clear suspended status", testPvp.isSuspended());
        assertEquals(0, testPvp.getWarnings());
    }
}

