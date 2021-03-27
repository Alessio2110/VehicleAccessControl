import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.*;
import javax.swing.border.Border;

import org.w3c.dom.ls.LSOutput;

import java.awt.*;

/* Generated by Together */

/**
 * This class represents the interactive interface to PACSUS administration
 * functions carried out in the Estates and Campus Services Office. Information
 * about these functions is in the Administration use case diagram (hyperlinked
 * from this class).
 *
 * The interface comprises one screen with all the functions present on it: they
 * could all be on view at once, or perhaps in alternative JPanels (in a JFrame
 * with JTabbedPane); the current date (day number) is always displayed.
 *
 * There could be any number of instances of this class, potentially one for
 * each workstation in the office, with different staff carrying different
 * functions.
 *
 * The class implements Observer, and observes the system status so that it can
 * keep the displayed current date correct.
 * 
 * @stereotype boundary
 */
public class Administration_office extends JFrame implements Observer, ActionListener {
	/**
	 * Each instance of Administration_office has a navigable association to the
	 * permit list so that it can enquire about/add/delete/modify permits.
	 * 
	 * @supplierCardinality 1
	 * @clientCardinality 1..*
	 * @label Administration functions
	 * @directed
	 */
	private Permit_list lnkPermit_list;

	/**
	 * Each instance of Administration_office has a navigable association to the
	 * vehicle list so that it can enquire about/add/delete/modify vehicle details.
	 * 
	 * @clientCardinality 1..*
	 * @supplierCardinality 1
	 * @label Administration functions
	 * @directed
	 */
	private Vehicle_list lnkVehicle_list;

	/**
	 * Each instance of Administration_office has a navigable association to the
	 * system status so that it can find out status information about the system.
	 * 
	 * @clientCardinality 1..*
	 * @supplierCardinality 1
	 * @label See date
	 * @directed
	 */
	private System_status lnkSystem_status;

	// First Panel: Add Permit
	private JPanel addPermitPanel; // Add permit panel

	// border for invalid information
	private Border border = BorderFactory.createLineBorder(Color.red, 3);

	private JLabel lblToday;
	private JLabel lblPermitHolder; // Permit holder name label
	private JLabel lblRegNo; // Registration number label
	private JLabel lblIssueDate; // Date of Issue label
	private JLabel lblHostName; // Student name label
	private JLabel lblStartDate; // Start date label
	private JLabel lblEndDate; // End date label
	private JLabel lblMsg1;

	private JTextField tfPermitHolder; // text field to insert permit holder's name
	private JTextField tfRegNo; // text field to insert registration number of a vehicle
	private JTextField tfIssueDate; // text field to insert date of issue
	private JTextField tfHostName; // text field to insert name of the university member
	private JTextField tfStartDate;
	private JTextField tfEndDate;

	private JButton addPermit;
	// Second Panel: Record Warning
	private JPanel secondPanel;

	private JLabel lblToday2;
	private JLabel lblPermitHolder2;
	private JLabel lblRecordWarning;

	private JTextField tfPermitHolder2;
	private JTextField tfWarnings;

	private JComboBox cmbPermitList; // ComboBox with 4 permit type options
	private JComboBox cmbPermitList2; // ComboBox with 4 permit type options

	private JTabbedPane tb;

	// Third panel: Delete warning
//	private JPanel deleteWarningPanel;
//	private JButton deleteWarning;
//	private JList warnings = new javax.swing.JList();
	// Third panel: Delete warning 

	private JPanel deleteWarningPanel; 
	private JButton deleteWarning; 
	private JButton deleteAllWarnings; 
	private JTextField tfDeleteWarningPermitHolderName; 
	private JLabel lblDeleteWarningPermitHolderName; 
	private JLabel lblMsgDelete; 
	private JLabel lblNumberSelectedFromComboBox; 
	private JComboBox cmbDeleteWarningList; // ComboBox with 3 options of amount of warnings to delete
	//stores the amount of warnings the user wants to delete, changes whenever new option is selected from cmbDeleteWarningList 
	private int amountOfWarningsToDelete = 0; 
	
	//Fourth panel: Cancel Permit 

	private JPanel cancelPermitPanel; 
	private JButton cancelPermit; 
	private JTextField tfCancelPermitHolderName; 
	private JLabel lblCancelPermitHolderName; 
	private JLabel lblMsgCancel; 

	// Delete Warning 

	// Status Panel
	private JPanel statusMainPanel;
	private JTextField statusPermitHolder;
	private JButton statusSearch;
	private JLabel statusInfo;
	// Modify Panel
	private JPanel modifyPanel;
	private JLabel lblChangeStartDate;
	private JLabel lblChangeEndDate;
	private JTextField modifyPermitName;
	private JTextField modifynoOfEntries;
	private JTextField modifyWarnings;
	private JTextField modifyEnteredToday;
	private JTextField modifyVehicleInfo;
	private JTextField modifyStartDate;
	private JTextField modifyEndDate;
	private JButton updatePermit;
	private JButton searchPermit;

	String msg = "Permanent visitor";

	public Administration_office(System_status lnkSystem_status, Vehicle_list lnkVehicle_list,
			Permit_list lnkPermit_list) {
		this.lnkSystem_status = lnkSystem_status;
		this.lnkVehicle_list = lnkVehicle_list;
		this.lnkPermit_list = lnkPermit_list;

		setTitle("Administration");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container window = getContentPane();
		window.setLayout(new FlowLayout()); // The default is that JFrame uses BorderLayout
		setLayout(new GridLayout(1, 1));

		// Regular_visitor_permit rvp1 = new Regular_visitor_permit("a", new Date(2),
		// new Date(4),"A");
		// lnkPermit_list.addPermit(rvp1);
		// Regular_visitor_permit rvp2 = new Regular_visitor_permit("b", new Date(1),
		// new Date(3),"B");
		// lnkPermit_list.addPermit(rvp2);
		// Add Permit
		setFirstPanel();

		// Record Warning
		setSecondPanel();

		// Delete Warning
		setThirdPanel();

		// Cancel Permit
		setFourthPanel();

		// Status Enquiry
		setStatusPanel();

		// Modify Permit
		setModifyPermitPanel();

		tb = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

		tb.addTab("Add Permit   ", addPermitPanel);
		tb.addTab("Record Warning", secondPanel);
		tb.addTab("Delete Warning", deleteWarningPanel);
		tb.addTab("Cancel Permit", cancelPermitPanel);
		tb.addTab("Status Enquiry", statusMainPanel);
		tb.addTab("Modify Permit", modifyPanel);
		// warnings.add("test", deleteWarningPanel);

		add(tb);
		setSize(800, 600);
		setVisible(true);
		setLocation(600, 300);
		lnkSystem_status.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// display.setText("Days passed since 01/09: " + lnkSystem_status.getToday());
		lblToday.setText("                                           Today is:       " + lnkSystem_status.getToday());
		lblToday2.setText("                                           Today is:       " + lnkSystem_status.getToday());
		System.out.println("Administration---Today is: Day #" + lnkSystem_status.getToday());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addPermit) {
			// tfPermitHolder.setEditable(false);
			System.out.println(msg);

			System.out.println("size is " + lnkPermit_list.getKeys().size());
			switch (msg) {
			case "Day Visitor":
				newDVP();
				cleanTFP1();
				break;
			case "Regular visitor":
				newRVP();
				cleanTFP1();
				break;
			case "Permanent visitor":
				newPVP();
				cleanTFP1();
				break;
			case "University member":
				newUMP();
				cleanTFP1();
				break;
			}
			System.out.println(lnkPermit_list.getSize());
			// String permitHolder, int noOfEntries, int warnings, boolean suspended,
			// boolean enteredToday, Vehicle_info vehicleUsedToday, Vehicle_list
			// permittedVehicles
			// lnkPermit_list.addPermit(p);
		}
		if (e.getSource() == cmbPermitList) {
			msg = cmbPermitList.getSelectedItem().toString();
			// Day Visitor", "Regular visitor", "Permanent visitor", "University member
			switch (msg) {
			case "Day Visitor":
				setDVP();
				break;
			case "Regular visitor":
				setRVP();
				break;
			case "Permanent visitor":
				setPVP();
				break;
			case "University member":
				setUMP();
				break;
			}
		}
		
//		if (e.getSource() == deleteWarning) {
//			String selected = (String) warnings.getSelectedValue(); // get name of selected permit
//
//			if (lnkPermit_list.getPermit(selected) != null) { // check if selected permit is not null
//				lnkPermit_list.getPermit(selected).clearWarnings(); // delete warnings from selected permit
//				System.out.println(selected);
//				System.out.println("Warning deleted");
//			}
//		}
		if(e.getSource() == cmbDeleteWarningList) { 

			amountOfWarningsToDelete = (int) cmbDeleteWarningList.getSelectedItem(); //sets the amount of warnings the user wants to delete 

		} 

		if (e.getSource() == deleteWarning) { 

			if(lnkPermit_list.getPermit(tfDeleteWarningPermitHolderName.getText()) != null) { 

				int amountOfWarnings = 0; 

				amountOfWarnings = lnkPermit_list.getPermit(tfDeleteWarningPermitHolderName.getText()).getWarnings(); 
				switch(amountOfWarnings) { //switch statement based on amount of warnings the selected permit holder has 
				case 0: 
					lblMsgDelete.setText("Warnings not removed, permit holder has no warnings."); 
					return; 
				case 1: 
					if(amountOfWarningsToDelete == 1) {  
						lnkPermit_list.getPermit(tfDeleteWarningPermitHolderName.getText()).deleteWarning(amountOfWarningsToDelete); 
					} 
					else { 
						lblMsgDelete.setText("Warnings not removed, permit holder has less number of warnings than selected."); 
						return; 
					} 
					break; 
				case 2: 
					if(amountOfWarningsToDelete <= 2) { 
						lnkPermit_list.getPermit(tfDeleteWarningPermitHolderName.getText()).deleteWarning(amountOfWarningsToDelete); 
					} 
					else { 
						lblMsgDelete.setText("Warnings not removed, permit holder has less number of warnings than selected."); 
						return; 
					} 
					break; 
				case 3: 
					if(amountOfWarningsToDelete <= 3) { 
						lnkPermit_list.getPermit(tfDeleteWarningPermitHolderName.getText()).deleteWarning(amountOfWarningsToDelete); 
					} 
					else { 
						lblMsgDelete.setText("Warnings not removed, permit holder has less number of warnings than selected."); 
						return; 
					} 
					break; 
				} 
				lblMsgDelete.setText("Warnings removed succesfully!"); 
				tfDeleteWarningPermitHolderName.setText(""); 
			} else { 
				lblMsgDelete.setText("Warnings not removed, invalid permit holder name entered."); 
			} 
		} 
		if	(e.getSource() == deleteAllWarnings) 
		{ 
			if(lnkPermit_list.getPermit(tfDeleteWarningPermitHolderName.getText()) != null) { 
				if(lnkPermit_list.getPermit(tfDeleteWarningPermitHolderName.getText()).getWarnings() > 0) { 
					lnkPermit_list.getPermit(tfDeleteWarningPermitHolderName.getText()).clearWarnings(); 
					lblMsgDelete.setText("All warnings removed succesfully!"); 
					tfDeleteWarningPermitHolderName.setText(""); 
				} 
				else { 
					lblMsgDelete.setText("Warnings not removed, permit holder has no warnings."); 
				} 
			} else { 
				lblMsgDelete.setText("Warnings not removed, invalid permit holder name entered."); 
			} 
		} 
		if (e.getSource() == cancelPermit) { 
			if(lnkPermit_list.getPermit(tfCancelPermitHolderName.getText()) != null){ 
				System.out.println("canceling permit"); 
				lnkPermit_list.removePermit(tfCancelPermitHolderName.getText()); 
				lblMsgCancel.setText("Permit cancelled succesfully!"); 
				tfCancelPermitHolderName.setText(""); 
			} 
			else { 
				lblMsgCancel.setText("Permit not cancelled succesfully, incorrect permit holder name entered."); 
			} 
		} 
//		
//		
//		
		if (e.getSource() == statusSearch) {
			if (lnkPermit_list.checkNameExists(statusPermitHolder.getText())) {
				statusInfo.setText(lnkPermit_list.getPermit(statusPermitHolder.getText()).status());
			} else {
				statusInfo.setText("Invalid Name");
			}
		}
		if (e.getSource() == searchPermit) {
			String name = modifyPermitName.getText();
			if (name.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter a Permit Holder name");
			} else{
				
				msg = cmbPermitList2.getSelectedItem().toString();
				// Day Visitor", "Regular visitor", "Permanent visitor", "University member
				switch (msg) {
				case "Day Visitor":
					setModifyInfo(name);
					break;
				case "Regular visitor":
					setModifyInfo(name);
					break;
				case "Permanent visitor":
					setModifyInfo(name);
					break;
				case "University member":
					setModifyInfo(name);
					break;
				}
			}
		}
		if (e.getSource() == updatePermit) {
			String name = modifyPermitName.getText();

			if (name.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter a Permit Holder name");
			} else {
				if (lnkPermit_list.checkNameExists(name)) {

					if (!modifynoOfEntries.getText().isEmpty()) {
						try {
							Integer.parseInt(modifynoOfEntries.getText());// try's to convert the string to an int
							lnkPermit_list.getPermit(name).setEntries(Integer.parseInt(modifynoOfEntries.getText()));// updates
							// the
							// amount
							// of
							// entries today
						} catch (NumberFormatException e2) {
							JOptionPane.showMessageDialog(null, "No of entries must be a number");
							modifynoOfEntries.setBorder(border);
						}
					}

				} else {
					JOptionPane.showMessageDialog(null, name + " is not a permit holder");
					if (!modifyWarnings.getText().isEmpty()) {
						try {
							Integer.parseInt(modifyWarnings.getText());// try's to convert the string to an int

							// if statement checks the current number of warnings are below 3 and then
							// checks to see if current warnings and new warnings together will be 3 or less
							if (lnkPermit_list.getPermit(name).getWarnings() < 3
									&& (lnkPermit_list.getPermit(name).getWarnings()
											+ Integer.parseInt(modifyWarnings.getText())) <= 3) {
								lnkPermit_list.getPermit(name).setWarning(Integer.parseInt(modifyWarnings.getText()));
							} else {
								JOptionPane.showMessageDialog(null, "Permit holder warnings cannot be more than 3");
							}
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "Warnings must be a number");
							modifyWarnings.setBorder(border);
						}
					}
					if (!modifyEnteredToday.getText().isEmpty()) {
						if (modifyEnteredToday.getText().toLowerCase() == "true") {
							lnkPermit_list.getPermit(name).setEnteredToday();
						} else if (modifyEnteredToday.getText().toLowerCase() == "false") {
							lnkPermit_list.getPermit(name).setNotEnteredToday();
						} else {
							JOptionPane.showMessageDialog(null, "Entered today must be True or False");
							modifyEnteredToday.setBorder(border);
						}
					}
					if (!modifyVehicleInfo.getText().isEmpty()) {
						if (lnkPermit_list.getPermit(name).isVehiclePermitted(modifyVehicleInfo.getText())) {
							JOptionPane.showMessageDialog(null, "Vehicle already on permit");
							modifyVehicleInfo.setBorder(border);
						} else {
							lnkPermit_list.getPermit(name).addPermittedVehicle(modifyVehicleInfo.getText());
							JOptionPane.showMessageDialog(null, "Vehicle has been added");
						}
					}
				}
				if (!modifyStartDate.getText().isEmpty()) {

				}

			}
		}
	}

	public void newUMP() {
		if (tfPermitHolder.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Holder name must not be empty");
			tfPermitHolder.setBorder(border);
		} else {
			String name = tfPermitHolder.getText();
			if (lnkPermit_list.checkNameExists(name)) {
				JOptionPane.showMessageDialog(null, "Holder name already exsist");
				tfPermitHolder.setBorder(border);
			} else {
				Date today = new Date(lnkSystem_status.getToday());
				lnkPermit_list.createUMP(name, today);
				if (!tfRegNo.getText().isEmpty()) {
					newVehicle(name);
				} else {
					JOptionPane.showMessageDialog(null, "Must have atleast one registration number");
					tfRegNo.setBorder(border);

				}
			}

		}
	}

	public void newRVP() {
		if (tfPermitHolder.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Holder name must not be empty");
			tfPermitHolder.setBorder(border);
		} else {
			String name = tfPermitHolder.getText();
			if (lnkPermit_list.checkNameExists(name)) {
				JOptionPane.showMessageDialog(null, "Holder name already exsist");
				tfPermitHolder.setBorder(border);
			} else {
				if (isInt(tfStartDate.getText()) && isInt(tfEndDate.getText())) {
					Date startDate = new Date(Integer.parseInt(tfStartDate.getText()));
					Date endDate = new Date(Integer.parseInt(tfEndDate.getText()));
					if (!startDate.isBefore(endDate)) {
						JOptionPane.showMessageDialog(null,
								"This is not a time machine, end date should come after start date");
						tfEndDate.setBorder(border);
					} else {
						lnkPermit_list.createRVP(name, startDate, endDate, name);
						// JOptionPane.showMessageDialog(null,"Regular visitor permit added
						// susccesfully"); //Probably better to have it on a label, and only have pop-up
						// messages for errors (?)
						lblMsg1.setText("Regular visitor permit added susccesfully");
						if (!tfRegNo.getText().isEmpty())
							newVehicle(name);
						// The lines below should be modified since you could create a permit without a
						// vehicle
						// It should check whether the vehicle already exists in another permit before
						// being added
						else {
							JOptionPane.showMessageDialog(null, "Must have atleast one regestration number");
							tfRegNo.setBorder(border);
						}
					}
				} else
					JOptionPane.showMessageDialog(null, "Entered Date(s) are not a valid day number [1 - 365]");

			}

		}
	}

	public void newPVP() {
		if (tfPermitHolder.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Holder name must not be empty");
			tfPermitHolder.setBorder(border);
		} else {
			String name = tfPermitHolder.getText();
			if (lnkPermit_list.checkNameExists(name)) {
				JOptionPane.showMessageDialog(null, "Holder name already exsist");
				tfPermitHolder.setBorder(border);
			} else {
				lnkPermit_list.createPVP(name);
				lblMsg1.setText("Permanent visitor permit added susccesfully");
				if (!tfRegNo.getText().isEmpty())
					newVehicle(name);
			}
		}
	}

	public void newDVP() {
		if (tfPermitHolder.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Holder name must not be empty");
			tfPermitHolder.setBorder(border);
		} else {
			String name = tfPermitHolder.getText();
			if (lnkPermit_list.checkNameExists(name)) {
				JOptionPane.showMessageDialog(null, "Holder name already exsist");
				tfPermitHolder.setBorder(border);
			} else {
				if (isInt(tfStartDate.getText())) {
					Date startDate = new Date(Integer.parseInt(tfStartDate.getText()));
					if (tfHostName.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Host name cannot be empty");
						tfHostName.setBorder(border);
					} else {
						lnkPermit_list.createDVP(name, startDate, name);
						// JOptionPane.showMessageDialog(null,"Regular visitor permit added
						// susccesfully"); //Probably better to have it on a label, and only have pop-up
						// messages for errors (?)
						lblMsg1.setText("Regular visitor permit added susccesfully");

						if (!tfRegNo.getText().isEmpty())
							newVehicle(name);
					} // end else statement
				} else
					JOptionPane.showMessageDialog(null, "Entered Date(s) are not a valid day number [1 - 365]");

			}

		}
	}

	public void newVehicle(String name) {
		String vehicleNames = tfRegNo.getText();
		String str = vehicleNames;
		String[] arrOfStr = str.split(", ");
		for (String v : arrOfStr) {
			if (!lnkPermit_list.vehicleIsRegistered2(v))
				lnkPermit_list.getPermit(name).addPermittedVehicle(v);
		} // end for loop
		System.out.println("Printing all vehicles for each permit:");
		lnkPermit_list.printAllVehicles();
	}

	// Set university member permit labels and text fields
	public void setUMP() {
		// Set UMP visible
		lblIssueDate.setVisible(true);
		tfIssueDate.setVisible(true);

		// Hide UMP
		lblHostName.setVisible(false);
		tfHostName.setVisible(false);
		lblStartDate.setVisible(false);
		tfStartDate.setVisible(false);
		lblEndDate.setVisible(false);
		tfEndDate.setVisible(false);
	}

	// Set Regular visitor permit labels and text fields
	public void setRVP() {
		// Make RVP visible
		lblHostName.setVisible(true);
		tfHostName.setVisible(true);
		lblStartDate.setVisible(true);
		tfStartDate.setVisible(true);
		lblEndDate.setVisible(true);
		tfEndDate.setVisible(true);

		// Hide UMP
		lblIssueDate.setVisible(false);
		tfIssueDate.setVisible(false);
	}

	// Set Permanent visitor permit labels and text fields
	public void setPVP() {

		// Hide UMP
		lblIssueDate.setVisible(false);
		tfIssueDate.setVisible(false);
		// Hide RVP
		lblHostName.setVisible(false);
		tfHostName.setVisible(false);
		lblStartDate.setVisible(false);
		tfStartDate.setVisible(false);
		lblEndDate.setVisible(false);
		tfEndDate.setVisible(false);
	}

	// Set Day visitor permit labels and text fields
	public void setDVP() {

		lblHostName.setVisible(true);
		tfHostName.setVisible(true);
		lblStartDate.setVisible(true);
		tfStartDate.setVisible(true);
		// Hide UMP
		lblIssueDate.setVisible(false);
		tfIssueDate.setVisible(false);
		// Hide RVP
		lblEndDate.setVisible(false);
		tfEndDate.setVisible(false);
	}

	public void setFirstPanel() {
		GridLayout experimentLayout = new GridLayout(0, 2);
		addPermitPanel = new JPanel();
		addPermitPanel.setLayout(experimentLayout);

		String[] permitTypes = { "Day Visitor", "Regular visitor", "Permanent visitor", "University member" };
		cmbPermitList = new JComboBox(permitTypes);
		cmbPermitList.setSelectedIndex(2);
		cmbPermitList.addActionListener(this);
		addPermitPanel.add(cmbPermitList);

		lblToday = new JLabel(
				"                                           Today is:       " + lnkSystem_status.getToday());
		lblToday.setFont(lblToday.getFont().deriveFont(15f));
		lblToday.setForeground(Color.red);
		lblToday.setOpaque(true);
		addPermitPanel.add(lblToday);

		// label and textfield for permit holder name
		lblPermitHolder = new JLabel("Permit Holder Name:");
		tfPermitHolder = new JTextField("", 25);

		addPermitPanel.add(lblPermitHolder);
		addPermitPanel.add(tfPermitHolder);

		// label and textfield for registration number
		lblRegNo = new JLabel("Registration #:");
		tfRegNo = new JTextField("", 10);
		addPermitPanel.add(lblRegNo);
		addPermitPanel.add(tfRegNo);

		// University member permit

		// label and textfield for a University member permit
		lblIssueDate = new JLabel("Date of issue:");
		tfIssueDate = new JTextField("", 3);
		addPermitPanel.add(lblIssueDate);
		addPermitPanel.add(tfIssueDate);
		lblIssueDate.setVisible(false);
		tfIssueDate.setVisible(false);

		// Regular Visitor Permit

		// label and textfield for a Regular Visitor Permit
		lblHostName = new JLabel("Name of the university member hosting the visit:");
		tfHostName = new JTextField("", 20);
		addPermitPanel.add(lblHostName);
		addPermitPanel.add(tfHostName);
		lblHostName.setVisible(false);
		tfHostName.setVisible(false);

		// label and textfield for a Regular Visitor Permit
		lblStartDate = new JLabel("Start Date:");
		tfStartDate = new JTextField("", 3);
		addPermitPanel.add(lblStartDate);
		addPermitPanel.add(tfStartDate);
		lblStartDate.setVisible(false);
		tfStartDate.setVisible(false);

		// label and textfield for a Regular Visitor Permit
		lblEndDate = new JLabel("End Date:");
		tfEndDate = new JTextField("", 3);
		addPermitPanel.add(lblEndDate);
		addPermitPanel.add(tfEndDate);
		lblEndDate.setVisible(false);
		tfEndDate.setVisible(false);

		addPermit = new JButton("Add permit");
		addPermit.addActionListener(this);
		addPermitPanel.add(addPermit);

		lblMsg1 = new JLabel("");
		addPermitPanel.add(lblMsg1);
	}

	public void setSecondPanel() {
		GridLayout experimentLayout = new GridLayout(0, 2);
		secondPanel = new JPanel();
		secondPanel.setSize(100, 5);
		secondPanel.setLayout(experimentLayout);

		JLabel empty = new JLabel("");
		secondPanel.add(empty);

		lblToday2 = new JLabel(
				"                                           Today is:       " + lnkSystem_status.getToday());
		lblToday2.setFont(lblToday.getFont().deriveFont(15f));
		lblToday2.setForeground(Color.red);
		lblToday2.setOpaque(true);
		secondPanel.add(lblToday2);
		// label and textfield for permit holder name
		lblRecordWarning = new JLabel("Record Warning:");
		secondPanel.add(lblRecordWarning);
		tfWarnings = new JTextField("", 3);
		secondPanel.add(tfWarnings);

		lblPermitHolder2 = new JLabel("Permit Holder Name: ");
		secondPanel.add(lblPermitHolder2);
		tfPermitHolder2 = new JTextField("", 3);
		secondPanel.add(tfPermitHolder2);

		// tfPermitHolder = new JTextField("", 25);

		for (int i = 0; i < 10; i++)
			secondPanel.add(new JLabel(""));

	}

	public void setThirdPanel() { 
		GridLayout experimentLayout = new GridLayout(0, 2); 
		deleteWarningPanel = new JPanel(); 
		deleteWarningPanel.setSize(100, 5); 
		// label and textfield for permit holder name 
		lblDeleteWarningPermitHolderName = new JLabel("Permit Holder Name:"); 
		tfDeleteWarningPermitHolderName = new JTextField("", 25); 
		lblNumberSelectedFromComboBox = new JLabel("Select # of warnings"); 
		deleteWarningPanel.add(lblNumberSelectedFromComboBox); 
		Integer[] amountOfWarningsToDelete = {1, 2, 3}; 
		cmbDeleteWarningList = new JComboBox(amountOfWarningsToDelete); 
		cmbDeleteWarningList.setSelectedIndex(2); 
		cmbDeleteWarningList.addActionListener(this); 
		deleteWarningPanel.add(cmbDeleteWarningList); 
		deleteWarningPanel.add(lblDeleteWarningPermitHolderName); 
		deleteWarningPanel.add(tfDeleteWarningPermitHolderName); 
		deleteWarning = new JButton("Delete warning"); 
		deleteWarning.addActionListener(this); 
		deleteWarningPanel.add(deleteWarning); 
		deleteAllWarnings = new JButton("Delete ALL warnings"); 
		deleteAllWarnings.addActionListener(this); 
		deleteWarningPanel.add(deleteAllWarnings); 
		lblMsgDelete = new JLabel(""); 
		deleteWarningPanel.add(lblMsgDelete); 
	} 


	public void setFourthPanel() { 
		GridLayout experimentLayout = new GridLayout(0, 2); 
		cancelPermitPanel = new JPanel(); 
		cancelPermitPanel.setSize(100, 5); 
		// label and textfield for permit holder name 
		lblCancelPermitHolderName = new JLabel("Permit Holder Name:"); 
		tfCancelPermitHolderName = new JTextField("", 25); 
		cancelPermitPanel.add(lblCancelPermitHolderName); 
		cancelPermitPanel.add(tfCancelPermitHolderName); 
		cancelPermit = new JButton("Cancel Permit"); 
		cancelPermit.addActionListener(this); 
		cancelPermitPanel.add(cancelPermit); 
		lblMsgCancel = new JLabel(""); 
		cancelPermitPanel.add(lblMsgCancel);
	} 
		
	// Clean text fields panel 1
	public void cleanTFP1() {
		tfPermitHolder.setText("");
		tfRegNo.setText("");
		tfIssueDate.setText("");
		tfHostName.setText("");
		tfStartDate.setText("");
		tfEndDate.setText("");
	}

	static boolean isInt(String s) {
		try {
			int i = Integer.parseInt(s);
			return true;
		} catch (NumberFormatException er) {
			return false;
		}
	}

	public void setStatusPanel() {
		GridLayout mainLayout = new GridLayout(2, 1);
		statusMainPanel = new JPanel();
		statusMainPanel.setLayout(mainLayout);
		GridLayout infoLayout = new GridLayout(4, 2);
		JPanel statusPanelTop = new JPanel();
		statusPanelTop.setLayout(infoLayout);
		JPanel statusPanelBot = new JPanel();
		statusMainPanel.add(statusPanelTop);
		statusMainPanel.add(statusPanelBot);

		JLabel day = new JLabel("Day: ");
		lblToday2 = new JLabel(" " + lnkSystem_status.getToday());
		lblToday2.setFont(lblToday.getFont().deriveFont(15f));
		lblPermitHolder = new JLabel("Permit Holder Name: ");
		statusPermitHolder = new JTextField("", 3);
		statusSearch = new JButton("Search");
		statusSearch.addActionListener(this);
		statusInfo = new JLabel();

		statusPanelTop.add(day);
		statusPanelTop.add(lblToday2);
		statusPanelTop.add(lblPermitHolder);
		statusPanelTop.add(statusPermitHolder);
		statusPanelTop.add(statusSearch);
		statusPanelTop.add(new JLabel(""));
		statusPanelTop.add(new JLabel(" Permit Status:"));
		statusPanelBot.add(statusInfo);

	}

	public void setModifyPermitPanel() {
		modifyPanel = new JPanel();
		GridLayout infoLayout = new GridLayout(2, 1);
		modifyPanel.setLayout(infoLayout);
		GridLayout infoLayout2 = new GridLayout(0, 2);
		JPanel modifyTopPanel = new JPanel();
		modifyTopPanel.setLayout(infoLayout2);
		JPanel modifyBotPanel = new JPanel();
		modifyBotPanel.setLayout(infoLayout2);

		String[] permitTypes2 = { "Day Visitor", "Regular visitor", "Permanent visitor", "University member" };
		cmbPermitList2 = new JComboBox(permitTypes2);
		cmbPermitList2.setSelectedIndex(2);
		cmbPermitList2.addActionListener(this);

		// creating and assigning all components
		lblToday2 = new JLabel(" " + lnkSystem_status.getToday());
		JLabel day = new JLabel("Day: ");
		lblPermitHolder = new JLabel("Permit Holder Name: ");
		modifyPermitName = new JTextField();
		JLabel lblmodifyNoOfEntries = new JLabel("No Of Entries: ");
		modifynoOfEntries = new JTextField();
		JLabel lblmodifyWarnings = new JLabel("Warnings : ");
		modifyWarnings = new JTextField();
		JLabel lblmodifyEnteredToday = new JLabel("Entered Today (True/False) : ");
		modifyEnteredToday = new JTextField();
		JLabel lbladdVehicleInfo = new JLabel("Modify Vehicles: ");
		modifyVehicleInfo = new JTextField();
		lblChangeStartDate = new JLabel(" Update Start Date: ");
		lblChangeEndDate = new JLabel(" Update End Date: ");
		lblChangeEndDate.setVisible(false);
		modifyStartDate = new JTextField();
		modifyEndDate = new JTextField();
		modifyEndDate.setVisible(false);
		updatePermit = new JButton("Update");
		updatePermit.addActionListener(this);
		searchPermit = new JButton("Search");
		searchPermit.addActionListener(this);

		modifyPanel.add(modifyTopPanel);
		modifyPanel.add(modifyBotPanel);
		modifyTopPanel.add(day);
		modifyTopPanel.add(lblToday2);
		modifyTopPanel.add(lblPermitHolder);
		modifyTopPanel.add(modifyPermitName);
		modifyTopPanel.add(searchPermit);
		modifyBotPanel.add(lblmodifyNoOfEntries);
		modifyBotPanel.add(modifynoOfEntries);
		modifyBotPanel.add(lblmodifyWarnings);
		modifyBotPanel.add(modifyWarnings);
		modifyBotPanel.add(lblmodifyEnteredToday);
		modifyBotPanel.add(modifyEnteredToday);
		modifyBotPanel.add(lbladdVehicleInfo);
		modifyBotPanel.add(modifyVehicleInfo);
		modifyBotPanel.add(lblChangeStartDate);
		modifyBotPanel.add(modifyStartDate);
		modifyBotPanel.add(lblChangeEndDate);
		modifyBotPanel.add(modifyEndDate);
		modifyBotPanel.add(updatePermit);
	}

	private void setModifyInfo(String name) {
		modifynoOfEntries.setText("" + lnkPermit_list.getPermit(name).getEntries());
		modifyWarnings.setText("" + lnkPermit_list.getPermit(name).getWarnings());
		modifyVehicleInfo.setText("" + lnkPermit_list.getPermit(name).getAllVehicles());
		modifyEnteredToday.setText("" + lnkPermit_list.getPermit(name).getVehicleUsedToday());
		modifyStartDate.setText("Date");
		modifyEndDate.setText("Date");

	}
}
