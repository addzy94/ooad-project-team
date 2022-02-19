import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
/*
JUnit version: JUnit 4
 */
public class StoreTest {

    /*
    Initialize a store object for testing
     */
    Store test_store = new Store(1);
    ArrayList<Staff> test_staff = test_store.getStaff();

    @Test
    public void initialize_test() {
        assertEquals(test_staff.size(), 3); // Check if there are really 3 members in the Staff list at this moment
        /*
        Check if the stuff members in the stuff list are all Clerks
         */
        assertTrue(test_staff.get(0) instanceof Clerk);
        assertTrue(test_staff.get(1) instanceof Clerk);
        assertTrue(test_staff.get(2) instanceof Clerk);
    }

    @Test
    public void resetWorkDays_test() {
        test_store.run(1); // Run the store for one day
        assertEquals(test_store.getDay(), 1); // Check if the day counter is now 1

        Clerk test_clerk = (Clerk) test_staff.get(0); // Find the clerk who has worked for a day
        for(int i=0; i<3; i++){
            if(test_staff.get(i).getDaysWorkedInARow() == 1){
                System.out.println("Picked Clerk is: " + test_clerk.getName());
                test_clerk = (Clerk) test_staff.get(i);
            }
        }
        assertEquals(test_clerk.getDaysWorkedInARow(), 1); // Double-check the clerk's dayWorkedInARow counter

        test_store.resetDays(); // Reset work days
        assertEquals(test_clerk.getDaysWorkedInARow(), 0); // Check if resetDays method is working correctly so that it can be used when we reach Sundays
    }
}