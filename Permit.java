import java.util.ArrayList;
import java.util.Iterator;

/* Generated by Together */

/**
 * Abstract superclass of all permit types. Note that since this class is
 * abstract, all "instances" of it are actually instances of its concrete
 * subclasses. The vehicles permitted entry by this permit are recorded here in
 * the permittedVehicles attribute. Further information could be recorded here,
 * such as contact details for the permit holder (and for the hosts in Regular
 * and Day visitor subclasses), but in this design these details are not
 * required. Note that no permit holder may have more than one permit at any
 * time, so no two instances of Permit (through its concrete sub-classes) may
 * have the same permit holder name. There is no direct notation for this.
 * Instead the Permit list object collecting the Permit must enforce it by
 * checking new additions (a hash table will help with this).
 *
 * For subclasses with dates: No permits are issued to span from one year into
 * the next; instead re-issue occurs "automatically" at the start of the year
 * (see the Timer use case diagram - follow hyperlink) - all permits are simply
 * carried over to the new year (except Day visitor and Regular visitor permits
 * that expire on the last day of the year). Permits which have a limited period
 * of validity (Day visitor and Regular visitor permits) are automatically
 * cancelled from PACSUS at the start of the day following their last valid day.
 */
abstract public class Permit {
	/**
	 * The name of the permit holder
	 */
	private String permitHolder;

	/**
	 * Counts the number of days on which the campus was entered while the access
	 * barriers were in operation. Counting starts when the permit is issued, and
	 * afresh at the start of each year.
	 */
	private int noOfEntries = 0;

	/**
	 * Counts the number of warnings issued to vehicles registered on this permit.
	 */
	private int warnings = 0;

	/**
	 * False if the permit has not been suspended, and true if it has (on the third
	 * warning).
	 */
	private boolean suspended = false;

	/**
	 * Set to false at the start of each day. Remains false until first entry of a
	 * vehicle on this permit, when it is set true, and the vehicle is noted in
	 * vehicleUsed. Used for checking that subsequent entries in the day are the
	 * same vehicle (since exits are not monitored).
	 */
	private boolean enteredToday = false;

	/**
	 * Once a vehicle has entered on this permit on any day, this attribute records
	 * the vehicle that entered, so that any subsequent entries associated with this
	 * permit can be verified as the same vehicle (or at least with the same
	 * registration number!). The attribute will be null until a vehicle has
	 * entered.
	 * 
	 * @clientCardinality 1
	 * @directed true
	 * @label Allowed today
	 * @supplierCardinality 0..1
	 */
	private Vehicle_info vehicleUsedToday;

	/**
	 * This holds references to all the Vehicle_info instances for the vehicles
	 * registered to this permit. Note that any vehicle can be registered to only
	 * one permit, but many vehicles may be registered to the same permit. This
	 * attribute must be implemented by a collection data structure (such as array,
	 * hash table,...).
	 * 
	 * @clientCardinality 1
	 * @directed true
	 * @label Controls access of
	 * @supplierCardinality 0..*
	 */
	private Vehicle_list permittedVehicles;

	// methods to add about/add/delete/modify
	public Permit(String permitHolder) {
		this.permitHolder = permitHolder;
		permittedVehicles = new Vehicle_list();
	}

	public Permit(String permitHolder, int noOfEntries, int warnings, boolean suspended, boolean enteredToday,
			Vehicle_info vehicleUsedToday, Vehicle_list permittedVehicles) {
		this.permitHolder = permitHolder;
		this.noOfEntries = noOfEntries;
		this.warnings = warnings;
		this.suspended = suspended;
		this.enteredToday = enteredToday;
		this.permittedVehicles = permittedVehicles;
	}

	public String getName() {
		return permitHolder;
	}

	public void increaseEntries() {
		noOfEntries++;
	}

	public void setEntries(int entries) {
		noOfEntries += entries;
	}

	public int getEntries() {
		return noOfEntries;
	}

	public void clearEntries() {
		noOfEntries = 0;
	}

	public int getWarnings() {
		return warnings;
	}

	public void addWarning() {
		warnings++;
		if (checkMaxWarnings()) {
			warnings = 0;
			suspended = true;
		}
	}
	
	public void deleteWarning(int deletedWarnings) { 
		this.warnings -= deletedWarnings; 
		} 
	
	public void setWarning(int warnings) {
		this.warnings += warnings;
	}

	public boolean checkMaxWarnings() {
		if (warnings == 3)
			return true;
		return false;
	}

	public void clearWarnings() {
		warnings = 0;
	}

	public void suspend() {
		suspended = true;
	}

	public void unsuspend() {
		suspended = false;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setEnteredToday() {
		enteredToday = true;
	}

	public void setNotEnteredToday() {
		enteredToday = false;
	}

	public void setVehicle(Vehicle_info vehicleUsedToday) {
		this.vehicleUsedToday = vehicleUsedToday;
	}

	public Vehicle_info getVehicleUsedToday() {
		return vehicleUsedToday;
	}

	public boolean isAllowed() {
		if (!suspended)
			return true;
		else
			return false;
	}

	public Vehicle_list getVList() {
		return permittedVehicles;
	}

	public void addPermittedVehicle(String v) {
		System.out.println("Permit.addPermittedVehicle ---Adding vehicle: " + v);
		permittedVehicles.addPermitVehicle(v);
	}

	public boolean getPermittedVehicle(String v) {
		if (permittedVehicles.isRegistered(v)) {
			return true;
		}
		return false;
	}

	public boolean removePermittedVehicle(String v) {
		if (permittedVehicles.removeVehicle(v)) {
			return true;
		}
		return false;
	}

	public String status() {
		String s = "Permit holder name: " + getName() + "; # of entries since permit issued: " + getEntries()
				+ "; # of warnings: " + getWarnings() + "; suspended: ";
		if (isSuspended())
			s += "Yes; ";
		else
			s += "No; ";
		if (getVehicleUsedToday() != null)
			s += "Registration number of the vehicle used today: " + getVehicleUsedToday().getRegNo();

		return s;
	}

	public void dailyReset() {
		setNotEnteredToday();
		setVehicle(null);
	}
	public ArrayList<String> getAllVehicles(){
		return permittedVehicles.getAllVehicles();
	}
}
