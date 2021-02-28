import java.awt.Container;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/* Generated by Together */

/**
 * This class represents the interactive interface to PACSUS administration functions carried
 * out in the Estates and Campus Services Office.  Information about these functions is in the
 * Administration use case diagram (hyperlinked from this class).
 *
 * The interface comprises one screen with all the functions present on it: they could all be on
 * view at once, or perhaps in alternative JPanels (in a JFrame with JTabbedPane); the current
 * date (day number) is always displayed.
 *
 * There could be any number of instances of this class, potentially one for each workstation
 * in the office, with different staff carrying different functions.
 *
 * The class implements Observer, and observes the system status so that it can keep the displayed current date correct.
 * @stereotype boundary
 */
public class Administration_office extends JFrame implements Observer {
    /**
     * Each instance of Administration_office has a navigable association to the permit list so
     * that it can enquire about/add/delete/modify permits.
     * @supplierCardinality 1
     * @clientCardinality 1..*
     * @label Administration functions
     * @directed
     */
    private Permit_list lnkPermit_list;

    /**
     * Each instance of Administration_office has a navigable association to the vehicle list so
     * that it can enquire about/add/delete/modify vehicle details.
     * @clientCardinality 1..*
     * @supplierCardinality 1
     * @label Administration functions
     * @directed
     */
    private Vehicle_list lnkVehicle_list;

    /**
     * Each instance of Administration_office has a navigable association to the system status so
     * that it can find out status information about the system.
     * @clientCardinality 1..*
     * @supplierCardinality 1
     * @label See date
     * @directed
     */
    private System_status lnkSystem_status;
    
    private JTextField display;
    public Administration_office(System_status lnkSystem_status, Vehicle_list lnkVehicle_list, Permit_list lnkPermit_list) {
    	this.lnkSystem_status = lnkSystem_status;
    	this.lnkVehicle_list = lnkVehicle_list;
    	this.lnkPermit_list = lnkPermit_list;
    	
    	setTitle("Administration");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());     // The default is that JFrame uses BorderLayout
        
        display = new JTextField("No data", 20);
        display.setText("Days passed since 01/09: " + lnkSystem_status.getToday());
        add(display);
        
        setSize(250,100);
        setVisible(true);
        setLocation(250, 0);
    	lnkSystem_status.addObserver(this);
    }

	@Override
	public void update(Observable o, Object arg) {
		display.setText("Days passed since 01/09: " + lnkSystem_status.getToday());
		System.out.println("Administration---Today is: Day #" + lnkSystem_status.getToday());
		
	}
}
