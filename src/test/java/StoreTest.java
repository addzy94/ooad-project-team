import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
    Store test_store_a = new Store(n, "Test Store A", null);
    Store test_store_b = new Store(n, "Test Store B", null);
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
        assertEquals(6, staff_pool.size()); // Check if there are really 6 members in the Staff list at this moment
        /*
        Check if the stuff members in the stuff list are all Clerks
         */
        assertTrue(staff_pool.get(0) instanceof Clerk);
        assertTrue(staff_pool.get(1) instanceof Clerk);
        assertTrue(staff_pool.get(2) instanceof Clerk);
        assertTrue(staff_pool.get(3) instanceof Clerk);
        assertTrue(staff_pool.get(4) instanceof Clerk);
        assertTrue(staff_pool.get(5) instanceof Clerk);
    }

    @Test
    public void generateInventory_Test() {
        HashMap<String, ArrayList<Item>> test_inventory = test_store_a.getInventory();
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
        test_store_a.addToRegistry(test_store_a.getInventory(), itemType, test_item);

        // Check the size of the current inventory list
        HashMap<String, ArrayList<Item>> test_inventory = test_store_a.getInventory();
        int test_inventory_size_now = 0;
        for(String itemType_new: test_inventory.keySet()) {
            for (Item i: test_inventory.get(itemType_new)) {
                test_inventory_size_now++;
            }
        }
        assertEquals(current_size + 1, test_inventory_size_now); // Check if the current list size is only 1 more than the original
    }

    @Test
    public void storeConnection_test() {
        // Connect those two stores together so they always have the reference to each other
        test_store_a.setOtherStore(test_store_b);
        test_store_b.setOtherStore(test_store_a);
        assertEquals(6, staff_pool.size()); // check if there's still 6 members in the pool after the last initialization test
        // Verify that the two stores are indeed related to each other
        assertEquals(test_store_b, test_store_a.getOtherStore());
        assertEquals(test_store_a, test_store_b.getOtherStore());

        // Assign a clerk to store A and a different clerk to store B
        Clerk shaggy = new Clerk("Shaggy", 0, 20, new HaphazardTuningStrategy());
        Clerk peter = new Clerk("Peter", 0, 5, new ManualTuningStrategy());
        test_store_a.setClerkToday(shaggy);
        test_store_b.setClerkToday(peter);
        // Verify that the there's always a way to keep tracking the reference to another clerk as well
        // So we can use them for switchStore command that need constant switch from one store together with one clerk to another store together with another clerk
        assertEquals(peter.getName(), test_store_a.getOtherStore().getClerkToday().getName());
        assertEquals(shaggy.getName(), test_store_b.getOtherStore().getClerkToday().getName());
        assertEquals(peter.getStore(), test_store_a.getOtherStore().getClerkToday().getStore());
        assertEquals(shaggy.getStore(), test_store_b.getOtherStore().getClerkToday().getStore());
        assertEquals(peter, test_store_a.getOtherStore().getClerkToday());
        assertEquals(shaggy, test_store_b.getOtherStore().getClerkToday());
    }
}