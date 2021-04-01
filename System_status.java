import java.util.Arrays;
import java.util.Observable;

/* Generated by Together */
//The model Class

/**
 * This class holds key control information for the operation of the barrier system.
 *
 * It extends Observable, and notifies its observers whenever either the date changes
 * (caused by the Timer class), or the barrier system activity status changes (caused
 * by an instance of the Campus security class), or the barrier event log changes (caused
 * by an instance of the Barrier class).
 *
 * There will only be one instance of this class.
 */
public class System_status extends Observable {
    /**
     * This attribute is the central indication of the activity status of the whole barrier
     * system. It is set/unset by messages from instances of the Campus security class.
     */
    private boolean systemActive = false;

    /**
     * An array of strings showing recent attempts to pass through the barriers (both successful
     * and unsuccessful). The last 20 should be enough? These are intended for display on
     * the Campus_security screens.
     */
    private String[] log;

    /**
     * This attribute is kept up to date by the Timer.
     * @clientCardinality 1
     * @supplierCardinality 1
     * @link aggregation
     * @label Contains
     * @directed
     */
    private Date today;
    
    
    /**
     * Constructor
     */
    public System_status() { //Constructor 
    	today = new Date(1);
    	log = new String[0];
    }
    
    /**
     * Notify observers after a change
     * 
     * @param isActive whether the system is active
     */
    public void setActive(boolean isActive) {
    	systemActive = isActive;
    	setChanged();
        notifyObservers();
    }
    
    /**
     * @return whether the system is active
     */
    public boolean getSystemActive() { 	return this.systemActive; }
     
    /**
     * @param regNo the String with the date and the registration number
     */
    public void addLog(String regNo) {
    	System.out.println(log.length);
    	String[] newlog = Arrays.copyOf(log, log.length + 1);
    	for (int i = 0; i < log.length; i++) {
    		newlog[i] = log[i];
    	}
    	newlog[log.length] = regNo;
    	log = newlog;
    	System.out.println(log.length);
    	
    }
    
    /**
     * @return how many logs there are
     */
    public int logslength() { return log.length; }
    
    /**
     *  Show all logs
     */
    public void showLogs() {
    	for (int i = 0; i < log.length - 1; i ++)
    		System.out.println(log[i]);
    	setChanged();
        notifyObservers();
    }
    
    /**
     * @return int: the number of today's date
     */
    public int getToday() { return today.getDay();}
    	
    /**
     * @return Date: today's date
     */
    public Date getDate() { return today;}
    	
    /**
     * Increase date by one, notify oberservers
     */
    public void nextDay() {
    	today.increment();
    	setChanged();
        notifyObservers();
    }
    
    /**
     * Remove parentheses and commas, useful for campus security logs formatting
     */
    @Override
	public String toString() {
		return  Arrays.toString(log).replace("[", "").replace("]", "").replace(",", "");
	}

	/**
	 * @return the array of logs
	 */
	public String[] getLogs() {
    	return log;
    }
    
}
