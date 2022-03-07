import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
/*
JUnit version: JUnit 4
 */
public class StoreTest {

    /*
    Initialize a store object for testing
     */
    int n = 2; // Number of items that will be initialized per item type
    Store test_store = new Store(n, "Test Store", null);
    private static ArrayList<Staff> staff_pool = new ArrayList<>();

    public static void hireClerks() {

        Clerk shaggy = new Clerk("Shaggy", 0, 20, new HaphazardTuningStrategy());
        Clerk velma = new Clerk("Velma", 0, 5, new ManualTuningStrategy());
        Clerk daphne = new Clerk("Daphne", 0, 10, new ElectronicTuningStrategy());
        Clerk scooby = new Clerk("Scooby", 0, 20, new HaphazardTuningStrategy());
        Clerk peter = new Clerk("Peter", 0, 5, new ManualTuningStrategy());
        Clerk danny = new Clerk("Danny", 0, 10, new ElectronicTuningStrategy());

        staff_pool.add(shaggy);
        staff_pool.add(velma);
        staff_pool.add(daphne);
        staff_pool.add(scooby);
        staff_pool.add(peter);
        staff_pool.add(danny);

        Store.setStaff(staff_pool);
    }

    @Test
    public void initialize_test() {
        hireClerks();
        assertEquals(6, staff_pool.size()); // Check if there are really 3 members in the Staff list at this moment
        /*
        Check if the stuff members in the stuff list are all Clerks
         */
        assertTrue(staff_pool.get(0) instanceof Clerk);
        assertTrue(staff_pool.get(1) instanceof Clerk);
        assertTrue(staff_pool.get(2) instanceof Clerk);
    }

    @Test
    public void generateInventory_Test() {
        HashMap<String, ArrayList<Item>> test_inventory = test_store.getInventory();
        /*
        Loop through the whole inventory hashmap which contains bunch of array list
        and count the total number of inventories stored in the hashmap
        */
        int test_inventory_size = 0;
        for(String itemType: test_inventory.keySet()) {
            for (Item i: test_inventory.get(itemType)) {
                test_inventory_size++;
            }
        }
        assertEquals(Constants.CLASS_NAMES.size() * n, test_inventory_size); // Check if the size of the inventory list is equal to the number of item types * n
    }

    @Test
    public void addToInventory_Test() {
        int current_size = Constants.CLASS_NAMES.size() * n; // Set a parameter for holding the current inventory size before adding new item
        /*
        Create a new CD object and add it into the inventory list
        */
        String itemType = "CD";
        Item test_item = Helper.createItem(itemType);
        test_item.setDayArrived(Store.getDay());
        int condition = test_item.getCondition();
        boolean isNew = test_item.getIsNew();
        double offeredPrice = Helper.priceEstimator(isNew, condition);
        test_item.setDayArrived(Store.getDay());
        test_item.setPurchasePrice(offeredPrice);
        test_item.setListPrice(offeredPrice);
        test_store.addToRegistry(test_store.getInventory(), itemType, test_item);

        // Check the size of the current inventory list
        HashMap<String, ArrayList<Item>> test_inventory = test_store.getInventory();
        int test_inventory_size_now = 0;
        for(String itemType_new: test_inventory.keySet()) {
            for (Item i: test_inventory.get(itemType_new)) {
                test_inventory_size_now++;
            }
        }
        assertEquals(current_size + 1, test_inventory_size_now); // Check if the current list size is only 1 more than the original
    }
    @Test
    public void resetWorkDays_test() {
        test_store.runDay(); // Run the store for one day
        assertEquals(1, Store.getDay()); // Check if the day counter is now 1

        Clerk test_clerk = (Clerk) staff_pool.get(0); // Find the clerk who has worked for a day
        for(Staff s : staff_pool){
            if(s.getDaysWorkedInARow() == 1){
                test_clerk = (Clerk) s;
            }
        }
        assertEquals(1, test_clerk.getDaysWorkedInARow()); // Double-check the clerk's dayWorkedInARow counter

        test_store.resetDays(); // Reset work days
        assertEquals(0, test_clerk.getDaysWorkedInARow()); // Check if resetDays method is working correctly so that it can be used when we reach Sundays
    }
}