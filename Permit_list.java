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
    
    /**
   	 * Constructor, create a hashtable with Permit as the object stored, and the permit holder name, a String, as the key
   	 */ 
    public Permit_list(){
    	lnkPermit = new java.util.Hashtable<String, Permit>();
    }
    
    /**
	 * Get the Permit object
	 * 
	 * @param permitHolder the permit holder name of the Permit object
	 */  
    public Permit getPermit(String permitHolder) {
    	return lnkPermit.get(permitHolder);
    }
    
    /**
  	 * Create a new University Member Permit (UMP) and add it to the permit list
  	 * 
  	 * @param permitHolder the permit holder name of the University Member Permit object
  	 * @param lnkDate the date the permit was issued
  	 */  
    public void createUMP(String permitHolder, Date lnkDate) {
    	University_member_permit ump = new University_member_permit(permitHolder, lnkDate);
//    	lnkPermit.put(permitHolder, ump);
    	addPermit(ump);
    }
    
    /**
   	 * Create a new Permanent Visitor Permit (PVP) and add it to the permit listst
   	 * 
   	 * @param permitHolder the permit holder name of the University Member Permit objectd
   	 */ 
    public void createPVP(String permitHolder) {
    	Permanent_visitor_permit pvp = new Permanent_visitor_permit(permitHolder);
//    	lnkPermit.put(permitHolder, pvp);
    	addPermit(pvp);
    }
    
    /**
   	 * Create a new Regular Visitor Permit (RVP) and add it to the permit list
   	 * 
   	 * @param permitHolder the permit holder name of the Regular Visitor Permit object
   	 * @param startDate the first day the RVP is allowed to pass through the barrier
   	 * @param endDate the last day the RVP is allowed to pass through the barrier
   	 * @param hostName the name of the university member hosting the visit
   	 */  
    public void createRVP(String permitHolder, Date startDate, Date endDate, String hostName) {
       	Regular_visitor_permit rvp = new Regular_visitor_permit(permitHolder, startDate, endDate, hostName);
//       	lnkPermit.put(permitHolder, rvp);
       	addPermit(rvp);
        }
    
    /**
  	 * Create a new Day Visitor Permit (DVP) and add it to the permit list
  	 * 
  	 * @param permitHolder the permit holder name of the Regular Visitor Permit object
  	 * @param lnkDate the day the DVP is allowed to pass through the barrier
  	 * @param hostName the name of the university member hosting the visit
  	 */  
    public void createDVP(String permitHolder, Date lnkDate, String hostName) {
    	Day_visitor_permit dvp = new Day_visitor_permit(permitHolder, lnkDate, hostName);
//       	lnkPermit.put(permitHolder, dvp);
       	addPermit(dvp);
        }
    
    /**
  	 * Check whether there is a Permit with a given name
  	 * 
  	 * @param permitHolder the permit holder name of the Permit
  	 */  
    public boolean checkNameExists(String permitHolder) {
    	return lnkPermit.containsKey(permitHolder);
    }
    
    /**
  	 * Print vehicles for each permit
  	 */  
    public void printAllVehicles() {
    	LinkedList<String> keys = getKeys();
//    	keys.forEach((key -> System.out.println(key)));
    	keys.forEach((key) -> lnkPermit.get(key).getVList().printVehicles());
    }
    
//    public void vehicleIsRegistered(String regNo) {
//    	LinkedList<String> keys = getKeys();
////    	keys.forEach((key -> System.out.println(key)));
//    	keys.forEach((key) -> lnkPermit.get(key).getVList().isRegistered(regNo));
//    }
    
    /**
  	 * Check whether the given vehicle registration number is found in any of the vehicle lists of each permit
  	 * 
  	 * @param regNo the registration number of the vehicle
  	 */  
    public boolean vehicleIsRegistered2(String regNo) {
    	LinkedList<String> keys = getKeys();
    	for (String key: keys) {
    		if(lnkPermit.get(key).getVList().isRegistered(regNo))
    			return true;
    	}
    	return false;
//    	keys.forEach((key) ->{
//    		if(lnkPermit.get(key).getVList().isRegistered(regNo))
//    			return true;
//    		else
//    			return false;
//    		};
    }
    
    public boolean vehicleisSuspended(String regNo) {
    	LinkedList<String> keys = getKeys();
    	for (String key: keys) {
    		if(lnkPermit.get(key).getVList().isAllowed(regNo))
    			return true;
    	}
    	return false;
    }
    
//    public boolean hasbeenUsed(String regNo) {
//    	LinkedList<String> keys = getKeys();
//    	for (String key: keys) {
//    		if(lnkPermit.get(key).)
//    			return false;
//    	}
//    	return true;
//    }
    
    /**
  	 * Get all the keys of the permits in permit list
  	 */  
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
    
    /**
  	 * Permanently remove a permit from the permit list
  	 * 
  	 * @param permitHolder the name of the permit holder we want to delete from the permit list
  	 */  
    public void removePermit(String permitHolder) {
    	lnkPermit.remove(permitHolder);
    }
    
    /**
   	 * Get number of permits in permit list
   	 */  
    public int getSize() {
    	return lnkPermit.size();
    }
    
    /**
  	 * add a Permit to the permit list, this could be any of the four subclasses of Permit
  	 */  
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
    
    /**
     * Make daily update on each permit in permit list
     */  
    public void dailyUpdateAll() {
    	Enumeration e = lnkPermit.elements();
    	while(e.hasMoreElements()) {
    		Permit p = (Permit) e.nextElement();
    		p.dailyReset();
    		}
    }
    
    /**
   	 * Make annual update on each permit in permit list
   	 */  
    public void annualUpdateAll() {
    	Enumeration e = lnkPermit.elements();
    	while(e.hasMoreElements()) {
    		Permit p = (Permit) e.nextElement();
    		p.unsuspend();
    		p.clearWarnings();
    		}
    }
}
