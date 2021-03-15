import java.time.LocalDate;
import java.util.Enumeration;
import java.util.LinkedList;

/* Generated by Together */

/**
 * Permit list manages the collection of permits currently issued and not yet cancelled (or expired).
 * It handles most of the use cases in the Administration section. Note that each Permit must have a
 * unique permit holder name (so a HashTable is probably a good implementation of the collection, with
 * permit holder name as key).
 *
 * There will only be one instance of this class.
 */
public class Permit_list {
    /** The Permit list maintains a collection of the Permits currently issued.
     *
     * This association must be implemented by an attribute holding a collection data structure (for
     * example: array, hash table - the latter is recommended).
     *
     * Note that no two Permits may have the same permit holder name (this information is not represented diagrammatically).
     * @associates Permit
     * @label Contains
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @directed*/
    private java.util.Hashtable<String, Permit> lnkPermit;
    
    public Permit_list(){
    	lnkPermit = new java.util.Hashtable<String, Permit>();
    }
    
    public Permit getPermit(String permitHolder) {
    	return lnkPermit.get(permitHolder);
    }
    
    //Create a new University Member Permit (UMP) and add it to the permit list
    public void createUMP(String permitHolder, Date lnkDate) {
    	University_member_permit ump = new University_member_permit(permitHolder, lnkDate);
//    	lnkPermit.put(permitHolder, ump);
    	addPermit(ump);
    }
    
    //Create a new Permanent Visitor Permit (PVP) and add it to the permit list
    public void createPVP(String permitHolder) {
    	Permanent_visitor_permit pvp = new Permanent_visitor_permit(permitHolder);
//    	lnkPermit.put(permitHolder, pvp);
    	addPermit(pvp);
    }
    
    //Create a new Regular Visitor Permit (RVP) and add it to the permit list
    public void createRVP(String permitHolder, Date startDate, Date endDate, String hostName) {
       	Regular_visitor_permit rvp = new Regular_visitor_permit(permitHolder, startDate, endDate, hostName);
//       	lnkPermit.put(permitHolder, rvp);
       	addPermit(rvp);
        }
    
  //Create a new Day Visitor Permit (DVP) and add it to the permit list
    public void createDVP(String permitHolder, Date lnkDate, String hostName) {
    	Day_visitor_permit dvp = new Day_visitor_permit(permitHolder, lnkDate, hostName);
//       	lnkPermit.put(permitHolder, dvp);
       	addPermit(dvp);
        }
    
    public boolean checkNameExists(String permitHolder) {
    	return lnkPermit.containsKey(permitHolder);
    }
    
    public void printAllVehicles() {
    	LinkedList<String> keys = getKeys();
//    	keys.forEach((key -> System.out.println(key)));
    	keys.forEach((key) -> lnkPermit.get(key).getVList().printVehicles());
    }
    
    public Permit findPermit(String regNo) {
    	LinkedList<String> keys = getKeys();
    	for (String key: keys) {
    		if(lnkPermit.get(key).getVList().isRegistered(regNo))
    			return lnkPermit.get(key);
    	}
    	return null;
    }
//    public void vehicleIsRegistered(String regNo) {
//    	LinkedList<String> keys = getKeys();
////    	keys.forEach((key -> System.out.println(key)));
//    	keys.forEach((key) -> lnkPermit.get(key).getVList().isRegistered(regNo));
//    }
    
    public boolean vehicleIsRegistered2(String regNo) {
    	LinkedList<String> keys = getKeys();
    	for (String key: keys) {
    		if(lnkPermit.get(key).getVList().isRegistered(regNo))
    			return true;
    	}
    	return false;
    }
    
    public LinkedList<String> getKeys() {
    	LinkedList<String> keysList = new LinkedList<String>();
    	Enumeration<String> keys = lnkPermit.keys();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            keysList.add(key);
//            System.out.println("Permi List --- Value of key: "+key+" is: ");
//            +lnkPermit.get(key).toString()
    }
        return keysList;
    }
    
    public void removePermit(String permitHolder) {
    	lnkPermit.remove(permitHolder);
    }
    
    public int getSize() {
    	return lnkPermit.size();
    }
    
    public void addPermit(Permit p) {
    	Permit p2;
    	p2 = (Permit) lnkPermit.get(p.getName());
    	if (p2 == null)
    		lnkPermit.put(p.getName(), p);
    	else {
    		System.out.println("Permit List --- Collision occurring: two permits cannot share a common permit holder name ");
    		System.out.println("Existing permit name: " + p2.getName() + " New permit name: " + p.getName());
    	}	
    }
    
    public void dailyUpdateAll() {
    	Enumeration e = lnkPermit.elements();
    	while(e.hasMoreElements()) {
    		Permit p = (Permit) e.nextElement();
    		p.dailyReset();
    		}
    }
    
    public void annualUpdateAll() {
    	Enumeration e = lnkPermit.elements();
    	while(e.hasMoreElements()) {
    		Permit p = (Permit) e.nextElement();
    		p.unsuspend();
    		p.clearWarnings();
    		}
    }
}
