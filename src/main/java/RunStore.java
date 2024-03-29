import java.util.ArrayList;

public class RunStore {

    private static ArrayList<Staff> staff_pool = new ArrayList<>();
    private static ArrayList<Store> current_stores = new ArrayList<>();
    private static Tracker store_tracker;
    private static Logger day_logger;
    // Main method that simulates the Store
    public static void main(String[] args) {
        //Store Tracker and Day Logger both implement an Observer pattern, of which Clerk is the Subject
        initializeObservers();
        createStores();
        hireClerks();
        registerObservers();

        int days = Helper.random.nextInt(30 - 10) + 10; // Generate run days in [10,30]

        //Prompt for the given number of days
        for (int i = 1; i <= days; i++) {
            simulateDay();
            Store.goToNextDay();
        }
        /* Run a special day that allows the user to control the shopping process
           No need to check if this special day is Sunday or not!
        */
        simulateSpecialDay();
        // Print out summary for all days
        printSummaries(days + 1);
    }

    public static void registerObservers() {
        for (int i = 0; i < staff_pool.size(); i++) {
            ((Clerk)staff_pool.get(i)).registerObserver(store_tracker);
        }
    }

    public static void hireClerks() {
        // Initialize clerks
        Clerk shaggy = new Clerk("Shaggy", 0, 20, new HaphazardTuningStrategy());
        Clerk velma = new Clerk("Velma", 0, 5, new ManualTuningStrategy());
        Clerk daphne = new Clerk("Daphne", 0, 10, new ElectronicTuningStrategy());
        Clerk scooby = new Clerk("Scooby", 0, 20, new HaphazardTuningStrategy());
        Clerk peter = new Clerk("Peter", 0, 5, new ManualTuningStrategy());
        Clerk danny = new Clerk("Danny", 0, 10, new ElectronicTuningStrategy());
        // Add them to te staff pool
        staff_pool.add(shaggy);
        staff_pool.add(velma);
        staff_pool.add(daphne);
        staff_pool.add(scooby);
        staff_pool.add(peter);
        staff_pool.add(danny);
        // Assign this pool to the universal store class, so that every individual store now have access to this pool
        Store.setStaff(staff_pool);

        store_tracker.addStaff(staff_pool);
    }

    public static void createStores() {
        Store northside = new Store(3, "Northside FNMS", day_logger); // Creates a store with 3 items of each type of the lowest level
        Store southside = new Store(3, "Southside FNMS", day_logger);

        northside.setOtherStore(southside);
        southside.setOtherStore(northside);

        store_tracker.addStore(northside);
        store_tracker.addStore(southside);

        current_stores.add(northside);
        current_stores.add(southside);
    }

    public static void simulateDay() {
        Store.sicknessCheck();
        for (int i = 0; i < current_stores.size(); i++) {
            current_stores.get(i).chooseClerk();
        }

        for (int i = 0; i < current_stores.size(); i++) {
            current_stores.get(i).runDay();
        }
    }

    // Similar to simulateDay method but with multiple different processes
    public static void simulateSpecialDay() {
        Store.sicknessCheck();
        for (int i = 0; i < current_stores.size(); i++) {
            current_stores.get(i).chooseClerk();
        }
        day_logger.instantiate(-1, null, true);
        // Run preparation process for all stores before calling OpenTheStoreCustom method
        for (int i = 0; i < current_stores.size(); i++) {
            Clerk currentClerk = current_stores.get(i).getClerkToday();
            current_stores.get(i).prepareSpecialDay(currentClerk);
        }
        // Run OpenTheStoreCustom method for all stores
        for (int i = 0; i < current_stores.size(); i++) {
            Clerk currentClerk = current_stores.get(i).getClerkToday();
            current_stores.get(i).shopSpecialDay(currentClerk);
        }
        // Let the track print info after running this special day, instead of calling it the end of simulateDay method
        store_tracker.printInfo();
        day_logger.close();
    }

    public static void printSummaries(int days) {
        for (int i = 0; i < current_stores.size(); i++) {
            current_stores.get(i).printSummary(days);
        }
    }

    public static void initializeObservers() {
        store_tracker = Tracker.getTracker();
        day_logger = Logger.getLogger();
    }

}


