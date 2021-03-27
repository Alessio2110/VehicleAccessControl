/* Generated by Together */ 
//Created by: Alessio, Callum, Polina, Scott

/**
 * This is an outline of a main program to build the operational infrastructure of PACSUS.
 * Look at the description and code for the main method.
 */
public class Main {
    /**
     * This main method builds the operational infrastructure of PACSUS as it starts up:
     *
     * The fixed internal "model" classes holding data are instantiated: one instance each of
     * System status, Vehicle list and Permit list.
     *
     * Boundary class objects are instantiated, supplied with references to the model classes.
     * They are presumed to put themselves on display, and register as Observers as necessary.
     */
    public static void main(String[] args) {
        System_status systemStatus = new System_status();
        Vehicle_list vehicleList = new Vehicle_list();
        Permit_list permitList = new Permit_list();

        Timer timer = new Timer(systemStatus, permitList);     // Frame, boundary class

        Barrier barrier1 = new Barrier(systemStatus, vehicleList, permitList);  // Frame, boundary class
        Barrier barrier2 = new Barrier(systemStatus, vehicleList, permitList);
          // Repeat for as many barriers as required

        Campus_security campusSecurity1 = new Campus_security(systemStatus, vehicleList);  // Frame, boundary class
//        Campus_security campusSecurity2 = new Campus_security(systemStatus, vehicleList);
          // Repeat for as many campus security screens as required

        Administration_office adminOffice1 = new Administration_office(systemStatus, vehicleList, permitList);  // Frame, boundary class
//        Administration_office adminOffice2 = new Administration_office(systemStatus, vehicleList, permitList);
          // Repeat for as many admin office screens as required
    } // main
}
