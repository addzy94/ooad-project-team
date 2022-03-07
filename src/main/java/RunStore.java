import java.util.ArrayList;

public class RunStore {

    private static ArrayList<Staff> staff_pool = new ArrayList<>();
    private static ArrayList<Store> current_stores = new ArrayList<>();
    private static Tracker store_tracker;
    private static Logger day_logger;
    // Main method that simulates the Store
    public static void main(String[] args) {

        /*
        --- IDENTITY ---
        Following 2 lines display Identity Concept; the creation of objects that
        have their own identity.
        --- IDENTITY ---
         */

        //Store Tracker and Day Logger both implement an Observer pattern, of which Clerk is the Subject

        initializeObservers();
        createStores();
        hireClerks();
        registerObservers();

        //Prompt for number of days
        for (int i = 1; i <= 30; i++) {
            simulateDay();
            Store.goToNextDay();
        }
        printSummaries(30);
        //a.run(30); // Runs the store for 30 days
    }

    public static void registerObservers() {
        for (int i = 0; i < staff_pool.size(); i++) {
            ((Clerk)staff_pool.get(i)).registerObserver(store_tracker);
        }
    }

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


        store_tracker.addStaff(staff_pool);


    }

    public static void createStores() {
        Store northside = new Store(3, "Northside FNMS", day_logger); // Creates a store with 3 items of each type of the lowest level
        Store southside = new Store(3, "Southside FNMS", day_logger);


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
        store_tracker.printInfo();
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


