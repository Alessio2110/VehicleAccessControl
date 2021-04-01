import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class JUnitTesting  extends Permit_list {
    private Permit_list permit_list = new Permit_list();



    /**
     * Check whether the name of a permit is already stored in the permit list.
     * Since the name of permit holders is used as a key, it is important to ensure there is only at a time with the same name.
     * 
     *    If the name is not found, an appropriate message is shown.
     *    One sample data from each permit was used to ensure there was no problem with any particular subclass of Permit.
     */
    @Test
    public void testcheckNameExists() {
        Permanent_visitor_permit testPvp = new Permanent_visitor_permit("John");
        permit_list.addPermit(testPvp);
        assertTrue("Permit with name John does not exist in current list", permit_list.checkNameExists("John"));

        Day_visitor_permit testDvp = new Day_visitor_permit("Hannah", new Date(1), "Chris");
        permit_list.addPermit(testDvp);
        assertTrue("Permit holder with name Hannah on this date hosted by Chris does not exist in current list", permit_list.checkNameExists("Hannah"));

        University_member_permit testUmp = new University_member_permit("Anna", new Date(1));
        permit_list.addPermit(testUmp);
        assertTrue("Permit holder with name Anna does not exist in this list", permit_list.checkNameExists("Anna"));

      
        Regular_visitor_permit testRvp = new Regular_visitor_permit("Alexa", new Date(1), new Date(2), "Chris");
        permit_list.addPermit(testRvp);
        //The name is Alexa, not Alex
        assertFalse("Permit holder with name Alexa does not exist in this list", permit_list.checkNameExists("Alex"));
      //The name is Alexa, so it is found in the permit list
        assertTrue("Permit holder with name Alexa does not exist in this list", permit_list.checkNameExists("Alexa"));
    }


    /**
     *  This test checks how many permits are stored in the permit list.
     *  It is needed to check that when we try to add, or remove, a permit the amount of permits in the permit list is actually altered as we would expect. 
     *  Sample data from different subclasses of Permit was used to ensure that adding, or removing a permit, would work with all permits and we would
     *  get the exact permit list size we would expect
     */
    @Test
    public void testGetSize() {

        assertEquals(0, permit_list.getSize());
        Regular_visitor_permit testRvp1 = new Regular_visitor_permit("Thomas", new Date(1), new Date(8), "Chris");
        permit_list.addPermit(testRvp1);
        assertNotEquals("Permit was not added succesfully", 0, permit_list.getSize());
        assertEquals("Permit was not added succesfully",1, permit_list.getSize());
        
        Permanent_visitor_permit testPvp2 = new Permanent_visitor_permit("John");
        permit_list.addPermit(testPvp2);
        assertEquals("Permit was not added succesfully", 2, permit_list.getSize());
        
        Day_visitor_permit testDvp3 = new Day_visitor_permit("James", new Date(5), "Jim");
        permit_list.addPermit(testDvp3);
        assertNotEquals("Permit was not added succesfully", 4, permit_list.getSize());
        assertEquals("Permit was not added succesfully", 3, permit_list.getSize());
        
        University_member_permit testUmp = new University_member_permit("Anna", new Date(1));
        permit_list.addPermit(testUmp);
        assertEquals("Permit was not added succesfully", 4, permit_list.getSize());
        assertNotEquals("Permit was not added succesfully", 3, permit_list.getSize());
        assertNotEquals("Permit was not added succesfully", 5, permit_list.getSize());
        permit_list.removePermit("Anna");
        permit_list.removePermit("James");
        assertEquals("Permit were not removed succesfully", 2, permit_list.getSize());
        assertNotEquals("Permit were not removed succesfully", 4, permit_list.getSize());
        
    }

    /**
     * Test removing a permit, check whether the permit is permanently removed from the permit list,
     *  and if the name of the permit holder can be found in the permit list.
     *  This is important to test because in administration there is a "Cancel permit" panel which allows admin staff
     *  to permanently remove a permit.
     *  Two permits were used as sample data, the subclass type should not matter for this operation.
     */
    @Test
    public void testRemovePermit() {
        Permanent_visitor_permit testPvp = new Permanent_visitor_permit("Chloe");
        permit_list.addPermit(testPvp);
        permit_list.removePermit("Chloe");
        assertFalse("Permit is not removed", permit_list.checkNameExists("Chloe"));
        assertEquals("Permit is not removed", 0, permit_list.getSize());
        assertNotEquals("Permit is not removed", 1, permit_list.getSize());
        
        Day_visitor_permit testDvp = new Day_visitor_permit("Joe", new Date(5), "Chris");
        permit_list.addPermit(testDvp);
        assertEquals("Permit is not added", 1, permit_list.getSize());
        permit_list.removePermit("Joe");
        assertFalse("Permit is not removed", permit_list.checkNameExists("Joe"));
        assertNotEquals("Permit is not removed", 1, permit_list.getSize());   
    }

    /**
     * It is important to check that when retrieving information from the permit list, no information is lost about the permit.
     * We check this for each type of permit subclass, more tests are made for permits with more attributes.
     * Ensuring all data from all subclasses is stored and retrieved correctly is crucial for the type of system we are creating.
     */
    @Test
    public void testGetPermit(){
    	//DVP test
    	Day_visitor_permit testDvp = new Day_visitor_permit("Charles", new Date(3), "Chris");
    	permit_list.addPermit(testDvp);
    	assertNotNull(permit_list.getPermit("Charles"));
    	
    	//is it the right instance?
    	assertTrue("It is NOT a Day_visitor_permit", (permit_list.getPermit("Charles") instanceof Day_visitor_permit));
    	assertFalse("It is a Permanent_visitor_permit", (permit_list.getPermit("Charles") instanceof Permanent_visitor_permit));
    	assertFalse("It is a Regular_visitor_permit", (permit_list.getPermit("Charles") instanceof Regular_visitor_permit));
    	assertFalse("It is a University_member_permit", (permit_list.getPermit("Charles") instanceof University_member_permit));
    	
    	// Is it the right name?
    	assertEquals(permit_list.getPermit("Charles").getName(), "Charles");
    	assertNotEquals(permit_list.getPermit("Charles").getName(), "Chris");
    	
    	// Is it the right date?
    	assertEquals( ((Day_visitor_permit) permit_list.getPermit("Charles")).getVisitDate().getDay(), 3);
    	assertNotEquals( ((Day_visitor_permit) permit_list.getPermit("Charles")).getVisitDate().getDay(), 2);
    	
    	// Is it the right host name?
    	assertEquals( ((Day_visitor_permit) permit_list.getPermit("Charles")).getHostName(), "Chris");
    	assertNotEquals( ((Day_visitor_permit) permit_list.getPermit("Charles")).getHostName(), "Charles");

    	
    	//PVP test
    	Permanent_visitor_permit testPvp = new Permanent_visitor_permit("Adam");
    	permit_list.addPermit(testPvp);
    	assertNotNull(permit_list.getPermit("Adam"));
    	
    	//is it the right instance?
    	assertFalse("It is a Day_visitor_permit", (permit_list.getPermit("Adam") instanceof Day_visitor_permit));
    	assertTrue("It is NOT a Permanent_visitor_permit", (permit_list.getPermit("Adam") instanceof Permanent_visitor_permit));
    	assertFalse("It is a Regular_visitor_permit", (permit_list.getPermit("Adam") instanceof Regular_visitor_permit));
    	assertFalse("It is a University_member_permit", (permit_list.getPermit("Adam") instanceof University_member_permit));
    	
    	// Is it the right name?
    	assertEquals(permit_list.getPermit("Adam").getName(), "Adam");
    	assertNotEquals(permit_list.getPermit("Adam").getName(), "Aadm");
    	
    	
    	//RVP test
    	Regular_visitor_permit testRvp = new Regular_visitor_permit("Thomas", new Date(1), new Date(8), "Amy");
    	permit_list.addPermit(testRvp);
    	assertNotNull(permit_list.getPermit("Thomas"));

    	//is it the right instance?
    	assertFalse("It is a Day_visitor_permit", (permit_list.getPermit("Thomas") instanceof Day_visitor_permit));
    	assertFalse("It is a Permanent_visitor_permit", (permit_list.getPermit("Thomas") instanceof Permanent_visitor_permit));
    	assertTrue("It is NOT a Regular_visitor_permit", (permit_list.getPermit("Thomas") instanceof Regular_visitor_permit));
    	assertFalse("It is a University_member_permit", (permit_list.getPermit("Thomas") instanceof University_member_permit));

    	// Is it the right name?
    	assertEquals(permit_list.getPermit("Thomas").getName(), "Thomas");
    	assertNotEquals(permit_list.getPermit("Thomas").getName(), "Thommas");

    	//is it the right starting date?
    	assertEquals(((Regular_visitor_permit) permit_list.getPermit("Thomas")).getStartDate().getDay(), 1);
    	assertNotEquals(((Regular_visitor_permit) permit_list.getPermit("Thomas")).getStartDate().getDay(), 8);

    	//is it the right ending date?
    	assertEquals(((Regular_visitor_permit) permit_list.getPermit("Thomas")).getEndDate().getDay(), 8);
    	assertNotEquals(((Regular_visitor_permit) permit_list.getPermit("Thomas")).getEndDate().getDay(), 1);

    	//is it the right host name?
    	assertEquals( ((Regular_visitor_permit) permit_list.getPermit("Thomas")).getHostName(), "Amy");
    	assertNotEquals( ((Regular_visitor_permit) permit_list.getPermit("Thomas")).getHostName(), "Thomas");

    	
    	//UMP test
    	University_member_permit testUmp = new University_member_permit("Anna", new Date(4));
    	 permit_list.addPermit(testUmp);
    	 assertNotNull(permit_list.getPermit("Anna"));
    	 
    	//is it the right instance?
    	 assertFalse("It is a Day_visitor_permit", (permit_list.getPermit("Anna") instanceof Day_visitor_permit));
    	 assertFalse("It is a Permanent_visitor_permit", (permit_list.getPermit("Anna") instanceof Permanent_visitor_permit));
    	 assertFalse("It is a Regular_visitor_permit", (permit_list.getPermit("Anna") instanceof Regular_visitor_permit));
    	 assertTrue("It is NOT a University_member_permit", (permit_list.getPermit("Anna") instanceof University_member_permit));
    	 
    	 //is it the right name?
    	 assertEquals(permit_list.getPermit("Anna").getName(), "Anna");
    	 assertNotEquals(permit_list.getPermit("Anna").getName(), "Annie");
    	 
    	 //is it the right issue date?
    	 assertEquals(((University_member_permit) permit_list.getPermit("Anna")).getIssueDate().getDay(), 4);
    	 assertNotEquals(((University_member_permit) permit_list.getPermit("Anna")).getIssueDate().getDay(), 1);
    	 
    }

    /**
     * For all permits, on a new day set that all permits have not entered today, and their used vehicle to null.
     * Check whether the expired permit is removed after the end date has passed, this applies for DVP, and RVP only
     * Different operations for some subclasses of Permit, for example RVP, and DVP, are removed from the list if they are expired.
     */
    @Test
    public void testDailyUpdateAll() {
    	Date today = new Date(3); //Today's date
    	
    	//Create a permit and a vehicle for each permit, than add it to the permit list
        Permanent_visitor_permit testPvp = new Permanent_visitor_permit("Jane");
        Vehicle_info testVehicle = new Vehicle_info("1", testPvp);
        permit_list.addPermit(testPvp);
        permit_list.getPermit("Jane").setVehicle(testVehicle);
        permit_list.getPermit("Jane").increaseEntries();
        permit_list.getPermit("Jane").setEnteredToday();
        
        Day_visitor_permit testDvp = new Day_visitor_permit("Hannah", new Date(3), "Chris"); // Visit date is day #3!
        Vehicle_info testVehicle2 = new Vehicle_info("2", testDvp);
        permit_list.addPermit(testDvp);
        permit_list.getPermit("Hannah").setVehicle(testVehicle2);
        permit_list.getPermit("Hannah").increaseEntries();
        permit_list.getPermit("Hannah").setEnteredToday();
        
        University_member_permit testUmp = new University_member_permit("Anna", new Date(1));
        Vehicle_info testVehicle3 = new Vehicle_info("3", testUmp);
        permit_list.addPermit(testUmp);
        permit_list.getPermit("Anna").setVehicle(testVehicle3);
        permit_list.getPermit("Anna").increaseEntries();
        permit_list.getPermit("Anna").setEnteredToday();
        
        Regular_visitor_permit testRvp = new Regular_visitor_permit("Alexa", new Date(1), new Date(3), "Chris"); //End date is day #3!
        Vehicle_info testVehicle4 = new Vehicle_info("2", testDvp);
        permit_list.addPermit(testRvp);
        permit_list.getPermit("Alexa").setVehicle(testVehicle4);
        permit_list.getPermit("Alexa").increaseEntries();
        permit_list.getPermit("Alexa").setEnteredToday();
        
        //All permits have used a vehicle, so this should not be null
        assertNotNull("DailyUpdateAll() not updated successfully, vehicle used today is not reset", testDvp.getVehicleUsedToday());
        assertNotNull("DailyUpdateAll() not updated successfully, vehicle used today is not reset", testPvp.getVehicleUsedToday());
        assertNotNull("DailyUpdateAll() not updated successfully, vehicle used today is not reset", testRvp.getVehicleUsedToday());
        assertNotNull("DailyUpdateAll() not updated successfully, vehicle used today is not reset", testUmp.getVehicleUsedToday());

        //There are 4 permits in permit list
        assertEquals("Permit was not added succesfully",4, permit_list.getSize());
        
        //Before the day is incremented above
        today.increment();
        permit_list.dailyUpdateAll(today); //Today is 4, so RVP and DVP are expired and cancelled from permit list.
        //After the day is incremented below

        //Two permits are no longer there, two remaining
        assertFalse("Permit holder with name Alexa on this date still exists", permit_list.checkNameExists("Alexa"));
        assertFalse("Permit holder with name Hannah on this date still exists", permit_list.checkNameExists("Hannah"));
        assertTrue("Permit holder with name Jane was wrongly removed", permit_list.checkNameExists("Jane"));
        assertTrue("Permit holder with name Anna was wrongly removed", permit_list.checkNameExists("Anna"));

        assertEquals("Permits were not removed succesfully", 2, permit_list.getSize());
        assertNotEquals("Permits were not removed succesfully", 4, permit_list.getSize());
        
        //Remaining permits have not used a vehicle today
        assertNull("DailyUpdateAll() not updated successfully, vehicle used today is not reset", testPvp.getVehicleUsedToday());
        assertNull("DailyUpdateAll() not updated successfully, vehicle used today is not reset", testUmp.getVehicleUsedToday());

        //No permit holder has entered today
        assertFalse("Entered today was not reset", permit_list.getPermit("Jane").getEnteredToday());
        assertFalse("Entered today was not reset", permit_list.getPermit("Anna").getEnteredToday());
     
    }
    /**
     * 	To test annualUpdateAll() method, create a permit, add it to the Permit_list,
     *  then we suspend it by adding 3 warnings.
     *   After calling annualUpdateAll(), we test whether it resets all suspensions and warnings for all permits.
     *   The type of permit does not really change anything, so two random subclasses of Permit can be used, no need to test each one.
     *   No permit is actually deleted, that is more dailyUpdateAll job.
     */
    @Test
    public void testAnnualUpdateAll() {
        Permanent_visitor_permit testPvp = new Permanent_visitor_permit("Jane");
        permit_list.addPermit(testPvp);
        for (int i = 0; i < 3; i ++) //Add 3 warnings
        permit_list.getPermit("Jane").addWarning();
        
        assertTrue("Adding warnings did not suspend the permit", permit_list.getPermit("Jane").isSuspended());
        
        Regular_visitor_permit testRvp = new Regular_visitor_permit("Thomas", new Date(1), new Date(8), "Amy");
        permit_list.addPermit(testRvp);
        for (int i = 0; i < 3; i ++) //Add 3 warnings
            permit_list.getPermit("Thomas").addWarning();
        
        assertTrue("Adding warnings did not suspend the permit", permit_list.getPermit("Thomas").isSuspended());
        
        permit_list.annualUpdateAll(); //reset all permits' suspensions and warnings
        
        //No permit is suspended nor has a warning
        assertFalse("annualUpdateAll() did not clear suspended status", permit_list.getPermit("Jane").isSuspended());
        assertFalse("annualUpdateAll() did not clear suspended status", permit_list.getPermit("Thomas").isSuspended());
        assertEquals(0, permit_list.getPermit("Jane").getWarnings());
        assertEquals(0, permit_list.getPermit("Thomas").getWarnings());
        assertNotEquals(3, permit_list.getPermit("Thomas").getWarnings());
    }
}

