import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
/*
JUnit version: JUnit 4
 */
public class StoreTest {

    @Test
    public void initialize() {
        Store test_store = new Store(30);

        ArrayList<Staff> test_staff = test_store.getStaff();

        assertEquals(test_staff.size(), 3); // Check if there are really 3 members in the Staff list at this moment
        /*
        Check if the stuff members in the stuff list are all Clerks
         */
        assertTrue(test_staff.get(0) instanceof Clerk);
        assertTrue(test_staff.get(1) instanceof Clerk);
        assertTrue(test_staff.get(2) instanceof Clerk);
//        ArrayList<Staff> staff = new ArrayList<>();
//        Clerk shaggy = new Clerk("Shaggy", 0, 79, new HaphazardTuningStrategy());
//        Clerk velma = new Clerk("Velma", 0, 94, new ManualTuningStrategy());
//        Clerk daphne = new Clerk("Daphne", 0, 36, new ElectronicTuningStrategy());
//        staff.add(shaggy);
//        staff.add(velma);
//        staff.add(daphne);
//        assertEquals(test_store.getStaff().size(), staff.size());
    }
}