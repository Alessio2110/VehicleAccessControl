import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

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
	private JLabel lblToday3;
	private JPanel deleteWarningPanel;
	private JButton deleteWarning;
	private JButton deleteAllWarnings;
	private JTextField tfDeleteWarningPermitHolderName;
	private JLabel lblDeleteWarningPermitHolderName;
	private JLabel lblMsgDelete;
	private JLabel lblNumberSelectedFromComboBox;
	private JComboBox cmbDeleteWarningList; // ComboBox with 3 options of amount of warnings to delete
	// stores the amount of warnings the user wants to delete, changes whenever new
	// option is selected from cmbDeleteWarningList
	private int amountOfWarningsToDelete = 0;

	// Fourth panel: Cancel Permit
	private JLabel lblToday4;
	private JPanel cancelPermitPanel;
	private JButton cancelPermit;
	private JTextField tfCancelPermitHolderName;
	private JLabel lblCancelPermitHolderName;
	private JLabel lblMsgCancel;

	// Status Panel
	private JLabel lblToday5;
	private JPanel statusMainPanel;
	private JTextField statusPermitHolder;
	private JButton statusSearch;
//	private JLabel statusInfo;
	private JTextArea statusInfo;
	// Modify Panel
	private JLabel lblToday6;
	private JPanel modifyPanel;
	private JLabel lblChangeStartDate;
	private JLabel lblChangeEndDate;
	private JLabel lblAllVehicles;
	private JTextField modifyPermitName;
	private JTextField modifynoOfEntries;
	private JTextField modifyWarnings;
	private JTextField modifyEnteredToday;
	private JTextField modifyVehicleInfo;
	private JTextField modifyStartDate;
	private JTextField modifyEndDate;
	private JButton updatePermit;
	private JButton searchPermit;
	private JButton addVehicle;
	private JButton removeVehicle;

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
		lblToday3.setText("                                           Today is:       " + lnkSystem_status.getToday());
		lblToday4.setText("                                           Today is:       " + lnkSystem_status.getToday());
		lblToday5.setText("                                           Today is:       " + lnkSystem_status.getToday());
		lblToday6.setText("                                           Today is:       " + lnkSystem_status.getToday());
		System.out.println("Administration---Today is: Day #" + lnkSystem_status.getToday());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// If clicking on the add permit panel
		if (e.getSource() == addPermit)
			newPermit();

		// If clicking on one of 4 types of permit in add permit panel
		if (e.getSource() == cmbPermitList)
			setPermit();

		// Amount of warnings to delete
		if (e.getSource() == cmbDeleteWarningList)
			amountOfWarningsToDelete = (int) cmbDeleteWarningList.getSelectedItem(); // sets the amount of warnings the
																						// user wants to delete

		// Delete warning(s)
		if (e.getSource() == deleteWarning)
			deleteWarning();

		// Delete all warnings
		if (e.getSource() == deleteAllWarnings)
			deleteAllWarnings();

		// Permanently cancel a permit
		if (e.getSource() == cancelPermit)
			cancelPermit();

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
				modifyPermitName.setBorder(border);
			} else if (lnkPermit_list.checkNameExists(name)) {
				lblAllVehicles.setText(lnkPermit_list.getPermit(name).getAllVehicles());
				modifyPermitName.setBorder(null);
				if (lnkPermit_list.getPermit(name) instanceof Regular_visitor_permit) {
					setModifyInfoRVP(name);
				} else if (lnkPermit_list.getPermit(name) instanceof Permanent_visitor_permit) {
					setModifyInfoPVP(name);
				} else if (lnkPermit_list.getPermit(name) instanceof Day_visitor_permit) {
					setModifyInfoDVP(name);
				} else if (lnkPermit_list.getPermit(name) instanceof University_member_permit) {
					setModifyInfoUVP(name);
				}

				else {
					JOptionPane.showMessageDialog(null, "No permit with that Name");
					modifyPermitName.setBorder(border);
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
						if (modifyEnteredToday.getText().toLowerCase().equals("true")) {
							lnkPermit_list.getPermit(name).setEnteredToday();
						} else if (modifyEnteredToday.getText().toLowerCase().equals("false")) {
							lnkPermit_list.getPermit(name).setNotEnteredToday();
						} else {
							JOptionPane.showMessageDialog(null, "Entered today must be True or False");
							modifyEnteredToday.setBorder(border);
						}
					}
					if (!modifyStartDate.getText().isEmpty()) {
						if (lnkPermit_list.getPermit(name) instanceof Regular_visitor_permit) {
							((Regular_visitor_permit) lnkPermit_list.getPermit(name))
									.changeStartDate(new Date(Integer.parseInt(modifyStartDate.getText())));
							JOptionPane.showMessageDialog(null, "Start date has been update");
						} else if (lnkPermit_list.getPermit(name) instanceof Day_visitor_permit) {
							((Day_visitor_permit) lnkPermit_list.getPermit(name))
									.changeDate(new Date(Integer.parseInt(modifyStartDate.getText())));
							JOptionPane.showMessageDialog(null, "Date has been update");
						} else if (lnkPermit_list.getPermit(name) instanceof University_member_permit) {
							((University_member_permit) lnkPermit_list.getPermit(name))
									.changeDate(new Date(Integer.parseInt(modifyStartDate.getText())));
							JOptionPane.showMessageDialog(null, "Date has been update");
						}
					}
					if (!modifyEndDate.getText().isEmpty()) {
						((Regular_visitor_permit) lnkPermit_list.getPermit(name))
								.changeEndDate(new Date(Integer.parseInt(modifyStartDate.getText())));
						JOptionPane.showMessageDialog(null, "End date has been update");
					}

				} else {
					JOptionPane.showMessageDialog(null, modifyPermitName.getText() + " is not a permit holder");
				}
			}
			JOptionPane.showMessageDialog(null, lnkPermit_list.getPermit(name).getName() + " information has been updated");
			clearModifyInfo();
		}
		if (!modifyVehicleInfo.getText().isEmpty()) {
			String name = modifyPermitName.getText();

			if (e.getSource() == addVehicle) {
				modifyNewVehicle(lnkPermit_list.getPermit(name));
				lblAllVehicles.setText("");
				modifyVehicleInfo.setText("");
				JOptionPane.showMessageDialog(null, "Vehicle has been added");
			}
			if (e.getSource() == removeVehicle) {
				lnkPermit_list.getPermit(name).removePermittedVehicle(modifyVehicleInfo.getText());
				lnkVehicle_list.removeVehicle(modifyVehicleInfo.getText());
				lblAllVehicles.setText("");
				modifyVehicleInfo.setText("");
				JOptionPane.showMessageDialog(null, "Vehicle has been removed");
			}
		}
	}

	private void cancelPermit() {
		if (lnkPermit_list.getPermit(tfCancelPermitHolderName.getText()) != null) {
			System.out.println("canceling permit");
			lnkPermit_list.removePermit(tfCancelPermitHolderName.getText());
			lblMsgCancel.setText("Permit cancelled succesfully!");
			tfCancelPermitHolderName.setText("");
		} else {
			lblMsgCancel.setText("Permit not cancelled succesfully, incorrect permit holder name entered.");
		}
	}

	private void deleteAllWarnings() {
		if (lnkPermit_list.getPermit(tfDeleteWarningPermitHolderName.getText()) != null) {
			String permitHolder = tfDeleteWarningPermitHolderName.getText();
			if (lnkPermit_list.getPermit(permitHolder).getWarnings() > 0) {
				lnkPermit_list.getPermit(permitHolder).clearWarnings();
				lblMsgDelete.setText("All warnings removed succesfully!");
				tfDeleteWarningPermitHolderName.setText("");
			} else {
				lblMsgDelete.setText("Warnings not removed, permit holder has no warnings.");
			}
		} else {
			lblMsgDelete.setText("Warnings not removed, invalid permit holder name entered.");
		}
	}

	private void deleteWarning() {
		if (lnkPermit_list.getPermit(tfDeleteWarningPermitHolderName.getText()) != null) {
			int amountOfWarnings = 0;
			String permitHolder = tfDeleteWarningPermitHolderName.getText();
			amountOfWarnings = lnkPermit_list.getPermit(permitHolder).getWarnings();
			if (amountOfWarnings == 0)
				lblMsgDelete.setText("Warnings not removed, permit holder has no warnings.");

			else if (amountOfWarnings < amountOfWarningsToDelete)
				lblMsgDelete.setText("Warnings not removed, permit holder has less warnings(" + amountOfWarnings
						+ ")  than selected(" + amountOfWarningsToDelete + ").");

			else {
				lnkPermit_list.getPermit(permitHolder).deleteWarning(amountOfWarningsToDelete);
				lblMsgDelete.setText("Warnings removed succesfully!");
				tfDeleteWarningPermitHolderName.setText("");
			}
		} else
			lblMsgDelete.setText("Warnings not removed, invalid permit holder name entered.");
	}

	private void setPermit() {
		// Day Visitor", "Regular visitor", "Permanent visitor", "University member
		msg = cmbPermitList.getSelectedItem().toString();
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

	private void newPermit() {
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
//				lnkPermit_list.createUMP(name, today);
				University_member_permit ump = new University_member_permit(name, today);
				lnkPermit_list.addPermit(ump);
				if (!tfRegNo.getText().isEmpty()) {
					newVehicle(ump);
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
//						lnkPermit_list.createRVP(name, startDate, endDate, name);
						Regular_visitor_permit rvp = new Regular_visitor_permit(name, startDate, endDate, name);
						lnkPermit_list.addPermit(rvp);
						lblMsg1.setText("Regular visitor permit added susccesfully");
						if (!tfRegNo.getText().isEmpty())
							newVehicle(rvp);
						// The lines below should be modified since you could create a permit without a
						// vehicle
						// It should check whether the vehicle already exists in another permit before
						// being added
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
				Permanent_visitor_permit pvp = new Permanent_visitor_permit(name);
				lnkPermit_list.addPermit(pvp); // Add permit to list of permits
//				lnkPermit_list.createPVP(name);
				lblMsg1.setText("Permanent visitor permit added susccesfully");
				if (!tfRegNo.getText().isEmpty())
					newVehicle(pvp);
			}
		}
	}

	public void newDVP() {
		if (tfPermitHolder.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Permit holder name must not be empty");
			tfPermitHolder.setBorder(border);
		} else { // Permit holder name is not empty

			String name = tfPermitHolder.getText();
			if (lnkPermit_list.checkNameExists(name)) {
				JOptionPane.showMessageDialog(null, "Permit holder name already exists");
				tfPermitHolder.setBorder(border);
			} else { // Permit holder name is not found in permit list

				if (isInt(tfStartDate.getText())) { // If date is an integer
					Date startDate = new Date(Integer.parseInt(tfStartDate.getText()));

					if (tfHostName.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Host name cannot be empty");
						tfHostName.setBorder(border);
					} else {
						String hostName = tfHostName.getText();
//						lnkPermit_list.createDVP(name, startDate, name);
						Day_visitor_permit dvp = new Day_visitor_permit(name, startDate, hostName);
						lnkPermit_list.addPermit(dvp);
						// JOptionPane.showMessageDialog(null,"Regular visitor permit added
//										lblMsg1.setText("Regular visitor permit added susccesfully");
						if (!tfRegNo.getText().isEmpty())
							newVehicle(dvp);
					} // end else statement
				} else
					JOptionPane.showMessageDialog(null, "Entered Date(s) are not a valid day number [1 - 365]");

			}

		}
	}

	public void newVehicle(Permit p) {
		String name = p.getName();
		String vehicleNames = tfRegNo.getText();
		String str = vehicleNames;
		str = str.replace(" ", "");
		String[] arrOfStr = str.split(",");
		for (String v : arrOfStr) {
			// if (!lnkPermit_list.vehicleIsRegistered2(v)) CHANGED!
			if (!lnkVehicle_list.isRegistered(v)) {
				Vehicle_info vehicle = new Vehicle_info(v, p); // Create vehicle

				lnkPermit_list.getPermit(name).addPermittedVehicle(vehicle); // Add vehicle to list of permitted
																				// vehicles of permit p
				lnkVehicle_list.addVehicle(vehicle); // Add vehicle to vehicle_list hashtable
			}
		} // end for loop
		System.out.println("Printing all vehicles for each permit:");
		lnkPermit_list.printAllVehicles();
	}

	public void modifyNewVehicle(Permit p) {
		String name = p.getName();
		String vehicleNames = modifyVehicleInfo.getText();
		String str = vehicleNames;
		str = str.replace(" ", "");
		String[] arrOfStr = str.split(",");
		for (String v : arrOfStr) {
			// if (!lnkPermit_list.vehicleIsRegistered2(v)) CHANGED!
			if (!lnkVehicle_list.isRegistered(v)) {
				Vehicle_info vehicle = new Vehicle_info(v, p); // Create vehicle

				lnkPermit_list.getPermit(name).addPermittedVehicle(vehicle); // Add vehicle to list of permitted
				// vehicles of permit p
				lnkVehicle_list.addVehicle(vehicle); // Add vehicle to vehicle_list hashtable
			}
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
		deleteWarningPanel.setLayout(experimentLayout);
		// label and textfield for permit holder name

		JLabel empty = new JLabel("");
		deleteWarningPanel.add(empty);

		lblToday3 = new JLabel(
				"                                           Today is:       " + lnkSystem_status.getToday());
		lblToday3.setFont(lblToday3.getFont().deriveFont(15f));
		lblToday3.setForeground(Color.black);
		lblToday3.setOpaque(true);
		deleteWarningPanel.add(lblToday3);

		lblDeleteWarningPermitHolderName = new JLabel("Permit Holder Name:");
		tfDeleteWarningPermitHolderName = new JTextField("", 25);

		lblNumberSelectedFromComboBox = new JLabel("Select # of warnings");
		deleteWarningPanel.add(lblNumberSelectedFromComboBox);
		Integer[] amountOfWarningsToDelete = { 1, 2, 3 };
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

		for (int i = 0; i < 10; i++) {
			deleteWarningPanel.add(new JLabel(""));
		}
	}

	public void setFourthPanel() {
		GridLayout experimentLayout = new GridLayout(0, 2);

		cancelPermitPanel = new JPanel();
		cancelPermitPanel.setSize(100, 5);
		cancelPermitPanel.setLayout(experimentLayout);
		// label and textfield for permit holder name

		JLabel empty = new JLabel("");
		cancelPermitPanel.add(empty);

		lblToday4 = new JLabel(
				"                                           Today is:       " + lnkSystem_status.getToday());
		lblToday4.setFont(lblToday4.getFont().deriveFont(15f));
		lblToday4.setForeground(Color.black);
		lblToday4.setOpaque(true);
		cancelPermitPanel.add(lblToday4);

		lblCancelPermitHolderName = new JLabel("Permit Holder Name:");

		tfCancelPermitHolderName = new JTextField("", 25);
		tfCancelPermitHolderName.setSize(30, 30);

		cancelPermitPanel.add(lblCancelPermitHolderName);
		cancelPermitPanel.add(tfCancelPermitHolderName);

		cancelPermit = new JButton("Cancel Permit");
		cancelPermit.addActionListener(this);
		cancelPermitPanel.add(cancelPermit);

		lblMsgCancel = new JLabel("");
		cancelPermitPanel.add(lblMsgCancel);

		for (int i = 0; i < 10; i++)
			cancelPermitPanel.add(new JLabel(""));

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
		lblToday5 = new JLabel(" " + lnkSystem_status.getToday());
		lblToday5.setFont(lblToday5.getFont().deriveFont(15f));
		lblPermitHolder = new JLabel("Permit Holder Name: ");
		statusPermitHolder = new JTextField("", 3);
		statusSearch = new JButton("Search");
		statusSearch.addActionListener(this);
//		statusInfo = new JLabel();
		statusInfo = new JTextArea();

		statusPanelTop.add(day);
		statusPanelTop.add(lblToday5);
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
		GridLayout infoLayout3 = new GridLayout(6, 2);
		JPanel modifyTopPanel = new JPanel();
		modifyTopPanel.setLayout(infoLayout3);
		JPanel modifyBotPanel = new JPanel();
		modifyBotPanel.setLayout(infoLayout2);

		String[] permitTypes2 = { "Day Visitor", "Regular visitor", "Permanent visitor", "University member" };
		cmbPermitList2 = new JComboBox(permitTypes2);
		cmbPermitList2.setSelectedIndex(2);
		cmbPermitList2.addActionListener(this);

		// creating and assigning all components
		lblToday6 = new JLabel(" " + lnkSystem_status.getToday());
		JLabel day = new JLabel("Day: ");
		lblPermitHolder = new JLabel("Permit Holder Name: ");
		modifyPermitName = new JTextField();
		JLabel lblmodifyNoOfEntries = new JLabel("No Of Entries: ");
		modifynoOfEntries = new JTextField();
		JLabel lblmodifyWarnings = new JLabel("Warnings : ");
		modifyWarnings = new JTextField();
		JLabel lblmodifyEnteredToday = new JLabel("Entered Today (True/False) : ");
		modifyEnteredToday = new JTextField();
		JLabel lbladdVehicleInfo = new JLabel("Permitted Vehicles: ");
		modifyVehicleInfo = new JTextField();
		lblAllVehicles = new JLabel();
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
		addVehicle = new JButton("Add");
		addVehicle.addActionListener(this);
		removeVehicle = new JButton("remove");
		removeVehicle.addActionListener(this);

		modifyPanel.add(modifyTopPanel);
		modifyPanel.add(modifyBotPanel);
		modifyTopPanel.add(day);
		modifyTopPanel.add(lblToday6);
		modifyTopPanel.add(lblPermitHolder);
		modifyTopPanel.add(modifyPermitName);
		modifyTopPanel.add(searchPermit);
		modifyBotPanel.add(lblmodifyNoOfEntries);
		modifyBotPanel.add(modifynoOfEntries);
		modifyBotPanel.add(lblmodifyWarnings);
		modifyBotPanel.add(modifyWarnings);
		modifyBotPanel.add(lblmodifyEnteredToday);
		modifyBotPanel.add(modifyEnteredToday);
		modifyBotPanel.add(lblChangeStartDate);
		modifyBotPanel.add(modifyStartDate);
		modifyBotPanel.add(lblChangeEndDate);
		modifyBotPanel.add(modifyEndDate);
		modifyBotPanel.add(updatePermit);
		modifyBotPanel.add(new JLabel());
		modifyBotPanel.add(lbladdVehicleInfo);
		modifyBotPanel.add(lblAllVehicles);
		modifyBotPanel.add(modifyVehicleInfo);
		modifyBotPanel.add(new JLabel());
		modifyBotPanel.add(addVehicle);
		modifyBotPanel.add(removeVehicle);
	}

	private void setModifyInfoRVP(String name) {
		lblChangeStartDate.setVisible(true);
		modifyStartDate.setVisible(true);
		lblChangeEndDate.setVisible(true);
		modifyEndDate.setVisible(true);
		modifynoOfEntries.setText("" + lnkPermit_list.getPermit(name).getEntries());
		modifyWarnings.setText("" + lnkPermit_list.getPermit(name).getWarnings());
		modifyEnteredToday.setText("" + lnkPermit_list.getPermit(name).getEnteredToday());
		modifyStartDate.setText(
				String.valueOf(((Regular_visitor_permit) lnkPermit_list.getPermit(name)).getStartDate().getDay()));
		modifyEndDate.setText(
				String.valueOf(((Regular_visitor_permit) lnkPermit_list.getPermit(name)).getEndDate().getDay()));

	}

	private void setModifyInfoPVP(String name) {
		lblChangeEndDate.setVisible(false);
		modifyEndDate.setVisible(false);
		modifynoOfEntries.setText("" + lnkPermit_list.getPermit(name).getEntries());
		modifyWarnings.setText("" + lnkPermit_list.getPermit(name).getWarnings());
		modifyEnteredToday.setText("" + lnkPermit_list.getPermit(name).getEnteredToday());

	}

	private void setModifyInfoDVP(String name) {
		lblChangeEndDate.setVisible(false);
		modifyEndDate.setVisible(false);
		modifynoOfEntries.setText("" + lnkPermit_list.getPermit(name).getEntries());
		modifyWarnings.setText("" + lnkPermit_list.getPermit(name).getWarnings());
		modifyEnteredToday.setText("" + lnkPermit_list.getPermit(name).getEnteredToday());
		modifyStartDate.setText(
				String.valueOf(((Regular_visitor_permit) lnkPermit_list.getPermit(name)).getStartDate().getDay()));

	}

	private void setModifyInfoUVP(String name) {
		lblChangeEndDate.setVisible(false);
		modifyEndDate.setVisible(false);
		modifynoOfEntries.setText("" + lnkPermit_list.getPermit(name).getEntries());
		modifyWarnings.setText("" + lnkPermit_list.getPermit(name).getWarnings());
		modifyEnteredToday.setText("" + lnkPermit_list.getPermit(name).getEnteredToday());
		modifyStartDate.setText(
				String.valueOf(((Regular_visitor_permit) lnkPermit_list.getPermit(name)).getStartDate().getDay()));

	}
	private void clearModifyInfo() {
		modifyPermitName.setText("");
		modifynoOfEntries.setText("");
		modifyWarnings.setText("");
		modifyEnteredToday.setText("");
		modifyStartDate.setText("");
		modifyEndDate.setText("");
		modifyVehicleInfo.setText("");
		lblAllVehicles.setText("");
	}
}
