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
	
	/**
	 * Constructor
	 * 
	 * @param permitHolder the permit holder name
	 */
	public Permit(String permitHolder) {
		this.permitHolder = permitHolder;
		permittedVehicles = new Vehicle_list();
	}

	/**
	 * Get name of permit holder
	 */
	public String getName() {
		return permitHolder;
	}

	/**
	 * Increase entries by one
	 */
	public void increaseEntries() {
		noOfEntries++;
	}

	/**
	 * Set entries to a given value
	 * 
	 * @param entries The number of entries to be set as the new value
	 */
	public void setEntries(int entries) {
		noOfEntries = entries;
	}

	/**
	 * Get entries made in this year
	 */
	public int getEntries() {
		return noOfEntries;
	}

	/**
	 * Set entries to zero
	 */
	public void clearEntries() {
		noOfEntries = 0;
	}

	/**
	 * Get number of warnings
	 */
	public int getWarnings() {
		return warnings;
	}

	/**
	 * Add a warning, if they are greater than 3, suspend permit
	 */
	public void addWarning() {
		warnings++;
		if (checkMaxWarnings()) {
			warnings = 3;
			suspended = true;
		}
	}

	/**
	 * Delete a number of warnings
	 * 
	 * @param deletedWarnings the number of deleted warnings to be deleted from the
	 *                        permit
	 */
	public void deleteWarning(int deletedWarnings) {
		this.warnings -= deletedWarnings;

		if (checkMaxWarnings()) {
			suspended = true;
		} else
			suspended = false;
	}

	/**
	 * Add a certain amount of warnings
	 * 
	 * @param warnings The number of warnings to be added
	 */
	public void setWarning(int warnings) {
		this.warnings = warnings;

		if (checkMaxWarnings()) {
			suspended = true;
		} else
			suspended = false;
	}

	/**
	 * Check if the there are at least three warnings
	 */
	public boolean checkMaxWarnings() {
		if (warnings >= 3)
			return true;
		return false;
	}

	/**
	 * Clear all warnings
	 */
	public void clearWarnings() {
		warnings = 0;

		unsuspend();
	}

	/**
	 * Suspend Permit
	 */
	public void suspend() {
		suspended = true;
	}

	/**
	 * Unsuspend Permit
	 */
	public void unsuspend() {
		suspended = false;
	}

	/**
	 * Check whether permit is suspended
	 */
	public boolean isSuspended() {
		return suspended;
	}

	/**
	 * Set that a vehicle has passed through the barrier today
	 */
	public void setEnteredToday() {
		enteredToday = true;
	}

	/**
	 * Set that a vehicle has not passed through the barrier today
	 */
	public void setNotEnteredToday() {
		enteredToday = false;
	}

	public boolean getEnteredToday() {
		return enteredToday;
	}

	/**
	 * Set the vehicle that has passed through the barrier today
	 * 
	 * @param vehicleUsedToday The vehicle used today by the permit holder
	 */
	public void setVehicle(Vehicle_info vehicleUsedToday) {
		this.vehicleUsedToday = vehicleUsedToday;
	}

	/**
	 * Get vehicle used today
	 */
	public Vehicle_info getVehicleUsedToday() {
		return vehicleUsedToday;
	}

	/**
	 * Get the list of vehicles registered for the permit holder
	 */
	public Vehicle_list getVList() {
		return permittedVehicles;
	}

	/**
	 * Add a vehicle to the vehicle list of this permit holder
	 */
	public void addPermittedVehicle(Vehicle_info v) { permittedVehicles.addVehicle(v); }
	
//	/**
//	 * Check whether a vehicle in vehicle list is allowed to pass through the barrier
//	 * 
//	 * @param v The vehicle registration number we need to check
//	 */
//	public boolean isVehiclePermitted(String v) {
//		if (permittedVehicles.isRegistered(v)) {
//			return true;
//		}
//		return false;
//	}

	/**
	 * Remove a vehicle from the vehicle list of a permit
	 * 
	 * @param v The vehicle registration number we need to remove
	 */
	public boolean removePermittedVehicle(String v) {
		if (permittedVehicles.removeVehicle(v)) {
			return true;
		}
		return false;
	}

	/**
	 * Get the string with the information of a Permit, this method is overridden in permit subclasses.
	 */
	public String status() {
		String s = "Permit holder name: " + getName() + "; # of entries since permit issued: " + getEntries()
				+ "; \n # of warnings: " + getWarnings() + "; suspended: ";
		if (isSuspended())
			s += "Yes;\n";
		else
			s += "No; \n";
		if (getVehicleUsedToday() != null)
			s += "Registration number of the vehicle used today: " + getVehicleUsedToday().getRegNo() + "+\n";
		else
			s += "No vehicles used today \n";

		s += "Vehicles registered on this Permit:\t" + permittedVehicles.getAllVehicles();
		return s;
	}

	/**
	 * Daily update for this permit
	 */
	public void dailyReset() {
		setNotEnteredToday();
		setVehicle(null);
	}

	/**
	 * Annual update for this permit
	 */
	public void annualReset() {
	}

	/**
	 * Get the array list with all vehicles for this permit as a String
	 */
	public String getAllVehicles() {
		return permittedVehicles.getAllVehicles();
	}

	public boolean isAllowed(Vehicle_info v, Date d) {
		boolean allowed = true;
		if (suspended)
			return false;

		if (!enteredToday) {
			return true;
		}
		if (vehicleUsedToday.getRegNo().equals(v.getRegNo())) {
			return true;
		}
		return false;
	}

}
