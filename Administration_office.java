import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

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
public class Administration_office extends JFrame implements Observer, ActionListener {
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
	private System_status 	lnkSystem_status;
	private JPanel 		addPermitPanel; //Add permit panel
	private JPanel 		secondPanel;
	private JLabel		lblPermitHolder; //Permit holder name label
	private JLabel 		lblRegNo; //Registration number label
	private JLabel 		lblIssueDate; //Date of Issue label
	private JLabel 		lblHostName; //Student name label
	private JLabel 		lblStartDate; //Start date label
	private JLabel 		lblEndDate; //End date label
	private JTextField 	tfPermitHolder; //text field to insert permit holder's name
	private JTextField 	tfRegNo; // text field to insert registration number of a vehicle
	private JTextField 	tfIssueDate; //text field to insert date of issue
	private JTextField 	tfHostName; //text field to insert name of the university member
	private JTextField 	tfStartDate;
	private JTextField 	tfEndDate;
	private JTextField display;
	private JButton addPermit;
	private JComboBox cmbPermitList; //ComboBox with 4 permit type options
	private JTabbedPane tb; 
	private GridBagConstraints c;

	public Administration_office(System_status lnkSystem_status, Vehicle_list lnkVehicle_list, Permit_list lnkPermit_list) {
		this.lnkSystem_status = lnkSystem_status;
		this.lnkVehicle_list = lnkVehicle_list;
		this.lnkPermit_list = lnkPermit_list;

		setTitle("Administration");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container window = getContentPane();
		window.setLayout(new FlowLayout());     // The default is that JFrame uses BorderLayout
		setLayout(new GridLayout(1, 1));
		
		//Add Permit
		setFirstPanel();
		
//		Record Warning
		setSecondPanel();
		
//		Delete Warning
//		setThirdPanel();
		
//		Cancel Permit
//		setFourthPanel();
		
//		Status Enquiry
//		setFifthPanel();
		
//		Modify Permit
//		SetSixthPermit
		
		tb = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		
		tb.addTab("Add Permit   ", addPermitPanel);
		tb.addTab("Record Warning", secondPanel);
//		tb.addTab("Delete Warning", );
//		tb.addTab("Cancel Permit", );
//		tb.addTab("Status Enquiry", );
//		tb.addTab("Modify Permit",  );

//		getContentPane().add(thePane);
		add(tb);


		setSize(600,400);
		setVisible(true);
		setLocation(600, 300);
		lnkSystem_status.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
//		display.setText("Days passed since 01/09: " + lnkSystem_status.getToday());
		System.out.println("Administration---Today is: Day #" + lnkSystem_status.getToday());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addPermit) {
			tfPermitHolder.setEditable(false);
			System.out.println("DISATTIVAA");
		}
		if (e.getSource() == cmbPermitList) {
			JComboBox cb = (JComboBox) e.getSource();
			String msg = (String) cb.getSelectedItem();
			//			Day Visitor", "Regular visitor", "Permanent visitor", "University member
			switch (msg) {
			case "Day Visitor": setDVP();
			break;
			case "Regular visitor": setRVP();
			break;
			case "Permanent visitor": setPVP();
			break;
			case "University member": setUMP();
			break;
			}
		}
		
	}
	//	Set university member permit labels and text fields
	public void setUMP() {
		//Set UMP visible
		lblIssueDate.setVisible(true);
		tfIssueDate.setVisible(true);
		
		//Hide UMP
		lblHostName.setVisible(false);
		tfHostName.setVisible(false);
		lblStartDate.setVisible(false);
		tfStartDate.setVisible(false);
		lblEndDate.setVisible(false);
		tfEndDate.setVisible(false);
	}

	//	Set Regular visitor permit labels and text fields
	public void setRVP() {
		//Make RVP visible
		lblHostName.setVisible(true);
		tfHostName.setVisible(true);
		lblStartDate.setVisible(true);
		tfStartDate.setVisible(true);
		lblEndDate.setVisible(true);
		tfEndDate.setVisible(true);
		
		//Hide UMP
		lblIssueDate.setVisible(false);
		tfIssueDate.setVisible(false);
	}

	//	Set Permanent visitor permit labels and text fields
	public void setPVP() {
		
		//Hide UMP
		lblIssueDate.setVisible(false);
		tfIssueDate.setVisible(false);
		//Hide RVP
		lblHostName.setVisible(false);
		tfHostName.setVisible(false);
		lblStartDate.setVisible(false);
		tfStartDate.setVisible(false);
		lblEndDate.setVisible(false);
		tfEndDate.setVisible(false);
	}

	//	Set Day visitor permit labels and text fields
	public void setDVP() {
		lblHostName.setVisible(true); 
		tfHostName.setVisible(true);
		
		//Hide UMP
		lblIssueDate.setVisible(false);
		tfIssueDate.setVisible(false);
		//Hide RVP
		lblStartDate.setVisible(false);
		tfStartDate.setVisible(false);
		lblEndDate.setVisible(false);
		tfEndDate.setVisible(false);
	}
	
	public void setFirstPanel() {
		GridLayout experimentLayout = new GridLayout(0,2);
		addPermitPanel = new JPanel();
		addPermitPanel.setLayout(experimentLayout);
//		addPermitPanel = new JPanel(new GridBagLayout());
//		c = new GridBagConstraints();
//		c.insets = new Insets(10, 10, 10, 10);
//		c.gridx = 0;
//		c.gridy = 0;
		//      JCombBox permit types
		String[] permitTypes = {"Day Visitor", "Regular visitor", "Permanent visitor", "University member"};
		cmbPermitList = new JComboBox(permitTypes);
		cmbPermitList.setSelectedIndex(2);
		cmbPermitList.addActionListener(this);
		addPermitPanel.add(cmbPermitList);

		JLabel empty = new JLabel("");
		addPermitPanel.add(empty);
		
		//		label and textfield for permit holder name        
		lblPermitHolder = new JLabel("Name:");
		tfPermitHolder = new JTextField("", 25);

		addPermitPanel.add(lblPermitHolder);
		addPermitPanel.add(tfPermitHolder);

		//		label and textfield for registration number        
		lblRegNo = new JLabel("Registration #:");
		tfRegNo = new JTextField("", 10);
		addPermitPanel.add(lblRegNo);
		addPermitPanel.add(tfRegNo);

		//      University member permit

		//		label and textfield for a University member permit       
		lblIssueDate = new JLabel("Date of issue:");
		tfIssueDate = new JTextField("", 3);
		addPermitPanel.add(lblIssueDate);
		addPermitPanel.add(tfIssueDate);
		lblIssueDate.setVisible(false);
		tfIssueDate.setVisible(false);
		
		//      Regular Visitor Permit

		//		label and textfield for a Regular Visitor Permit   
		lblHostName = new JLabel("Name of the university member hosting the visit:");
		tfHostName = new JTextField("", 20);
		addPermitPanel.add(lblHostName);
		addPermitPanel.add(tfHostName);
		lblHostName.setVisible(false);
		tfHostName.setVisible(false);

		//		label and textfield for a Regular Visitor Permit   
		lblStartDate = new JLabel("Start Date:");
		tfStartDate = new JTextField("", 3);
		addPermitPanel.add(lblStartDate);
		addPermitPanel.add(tfStartDate);
		lblStartDate.setVisible(false);
		tfStartDate.setVisible(false);

		//		label and textfield for a Regular Visitor Permit   
		lblEndDate = new JLabel("End Date:");
		tfEndDate = new JTextField("", 3);
		addPermitPanel.add(lblEndDate);
		addPermitPanel.add(tfEndDate);
		lblEndDate.setVisible(false);
		tfEndDate.setVisible(false);
		
		addPermit = new JButton("Add permit");
		addPermit.addActionListener(this);
		addPermitPanel.add(addPermit);
	}
	
	public void setSecondPanel() {
		secondPanel = new JPanel();
		
	}
}
