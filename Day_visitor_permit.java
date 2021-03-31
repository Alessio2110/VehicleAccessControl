/* Generated by Together */

/**
 * For a description of Day visitors, follow hyperlink to the Administration use case for
 * issuing a new Day visitor permit.
 */
public class Day_visitor_permit extends Permit {
    /**
     * The name of the University member hosting the visit.
     */
    private String hostName;

    /**
     * The date that the visitor is visiting on - entry will be allowed on that date only.
     * @clientCardinality 1
     * @supplierCardinality 1
     * @label Visiting on
     * @link aggregation
     * @directed
     */
    private Date lnkDate;
    
    
    public Day_visitor_permit(String permitHolder, Date lnkDate, String hostName) {
    	super( permitHolder);
    	this.lnkDate = lnkDate;
    	this.hostName = hostName;
    }
    
    
    public Date getVisitDate() {
    	return lnkDate;
    }
    
    public void changeDate(Date d) {
    	lnkDate = d;
    }
    
    public void changeHostName(String s) {
    	hostName = s;
    }
    
    public String getHostName() {
    	return hostName;
    }
    
    public boolean isAllowed(Vehicle_info v, Date d) {
    	if(!super.isAllowed(v, d))
    		return false;
    	if (d.isEqual(lnkDate))
    		return true;
    	return false;
    }
    /**
  	 * Get the string with the information of a Permit
  	 */
  	public String status() {
  		String s = "Permit type: Day Visitor Permit \n" + super.status() 
  				+  "Date of visit: #" + lnkDate.getDay();
  		
  		return s;
  	}
  	
    public boolean isExpired(Date today) { return  today.getDay() > lnkDate.getDay();}
}