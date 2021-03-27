import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;
public class JUnitTesting  extends Permit_list{

    private Permit_list permit_list = new Permit_list();
    private Date date;
    

    @Test
    public void testCreateAndCheckPermit() {
       permit_list.createPVP(("John"));
       assertTrue("Permit with name John does not exist in current list", permit_list.checkNameExists("John"));

       permit_list.createDVP("Hannah", date,"Chris");
        assertTrue("Permit holder with name Hannah on this date hosted by Chris does not exist in current list", permit_list.checkNameExists("Hannah"));

        permit_list.createUMP("Anna", date);
        assertTrue("Permit holder with name Anna does not exist in this list", permit_list.checkNameExists("Anna"));

        permit_list.createRVP("Alex", date, date, "Chris");
        assertTrue("Permit holder with name Alexa does not exist in this list", permit_list.checkNameExists("Alex"));
    }

    @Test
    public void testListSize(){

        assertEquals(0, permit_list.getSize());
        permit_list.createPVP(("Thomas"));
        assertEquals(1, permit_list.getSize());
        permit_list.createPVP(("John"));
        assertEquals(2, permit_list.getSize());
        permit_list.createPVP(("James"));
        assertEquals(3, permit_list.getSize());
    }

    @Test
    public void testRemovePermit(){
        permit_list.createPVP(("Chloe"));
        permit_list.removePermit("Chloe");
        assertFalse("Permit is not removed", permit_list.checkNameExists("Chloe"));
    }

    @Test
    public void testGetKeys(){
        permit_list.createPVP(("Joe")); //create new permit in list
        LinkedList<String> emptyLinkedList = new LinkedList<String>(); //create an empty linked list
        assertNotEquals(emptyLinkedList, permit_list.getKeys()); // compare the linked list of permit keys in permit_list to an empty linked list
    }


    @Test
    public void testGetPermit(){
        permit_list.createPVP(("Adam")); //create new permit in list
        assertNotNull(permit_list.getPermit("Adam"));
    }
}
