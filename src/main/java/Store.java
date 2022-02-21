import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Store {
    
    private int day;
    private double registerAmount;
    private double amountWithdrawnFromBank;
    private HashMap<String, ArrayList<Item>> inventory;
    private HashMap<String, ArrayList<Item>> soldLogBook;
    private ArrayList<Staff> staff;
    private Tracker store_tracker;
    private Logger day_logger;

    Store(int n) {
        // Assign 3 objects per item (lowest subclass) by the time we initialize a store
        this.initialize(n);
    }

    public void initialize(int numberofObjects) {

        /*
        Is Going to create 'numberofObjects' Objects of each type in the Store
        on the 0th Day.
         */

        this.day = 0;
        this.registerAmount = 0;
        this.amountWithdrawnFromBank = 0;
        this.inventory = new HashMap<>();
        this.soldLogBook = new HashMap<>();
        this.staff = new ArrayList<>();

        Constants.generateMaps(); // Declares all the constants and initializes them

        generateInventory(numberofObjects, Constants.CLASS_NAMES, true);

        /*
        --- IDENTITY ---
        Following 2 lines display Identity Concept; the creation of objects that
        have their own identity.
        --- IDENTITY ---
         */

        Clerk shaggy = new Clerk("Shaggy", 0, 79, new HaphazardTuningStrategy());
        Clerk velma = new Clerk("Velma", 0, 94, new ManualTuningStrategy());
        Clerk daphne = new Clerk("Daphne", 0, 36, new ElectronicTuningStrategy());

        staff.add(shaggy);
        staff.add(velma);
        staff.add(daphne);

        //Store Tracker and Day Logger both implement an Observer pattern, of which Clerk is the Subject

        store_tracker = new Tracker();
        store_tracker.addStaff(shaggy);
        store_tracker.addStaff(velma);
        store_tracker.addStaff(daphne);

        shaggy.registerObserver(store_tracker);
        velma.registerObserver(store_tracker);
        daphne.registerObserver(store_tracker);

        day_logger = new Logger();
    }

    public void generateInventory(int numberofObjects, ArrayList<String> itemTypes, boolean isStartDay) {

        // Handles the condition when input parameter in string does not belong to the classes we already defined
        try {
            for (String itemType: itemTypes) {
                for(int i = 0; i < numberofObjects; i++) {
                    Item item = createItem(itemType);
                    // Just add the item to the inventory if it's the first day.
                    if (isStartDay) {
                        addToInventory(itemType, item);
                    }
                    else {
                        // If we can afford the item at this moment, buy it, and put it into the inventory list directly with a dayArrive counter been set
                        if (item.getPurchasePrice() <= getRegisterAmount()) {
                            System.out.println("The store purchased the " + item.getName() +
                                    " " + itemType + " which costs " + Helper.round(item.getPurchasePrice()) + "$");
                            item.setDayArrived(day + Helper.random.nextInt(3) + 1);
                            this.setRegisterAmount(this.getRegisterAmount() - item.getPurchasePrice());
                            addToInventory(itemType, item);
                        }
                        // Can't buy it, since we are out of funds
                        else {
                            System.out.println("Couldn't purchase the " + item.getName() +
                                    " " + itemType + " which costs " + Helper.round(item.getPurchasePrice()) +
                                    " as we have only " + Helper.round(this.getRegisterAmount()) + "$");
                        }
                    }
                }
            }
        }
        catch(Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }

    public Item createItem(String itemType) {

        Object classInstance = null;

        try {
            // Initialize a list of class in the following way: [String, Int, bla, bla] for later use of initializing the corresponding item type
            Class[] parameters = Constants.CLASS_PARAMETER_MAPPING.get(itemType);
            // Initialize the corresponding class object based on the given String itemType
            Class classObj = Class.forName(itemType);
            // Combine the two lines above for really generating a constructor
            Constructor constructor = classObj.getConstructor(parameters);
            // Generate an object by calling Helper class that helps you put all the necessary parameter (price, day, etc) for generating it.
            classInstance = constructor.newInstance(Helper.getParams(itemType, this.day).toArray());
        }
        catch(Exception e) {
            System.out.println("Errors");
            e.printStackTrace();
        }

        return ((Item) classInstance);
    }

    public void addToInventory(String itemType, Item item) {

        /*
        --- POLYMORPHISM ---
            All of the items generated are of its own kind and exhibit polymorphic behavior.
            But as they are all from the type 'Item', so they can be added to the 'inventory' HashMap.
        --- POLYMORPHISM ---
         */

        // Check if there's a key in the hashtable for storing the corresponding item time
        if (inventory.containsKey(itemType)) {
            inventory.get(itemType).add(item);
        }
        else {
            inventory.put(itemType, new ArrayList<Item>(Arrays.asList(item)));
        }
    }

    public void removeFromInventory(String itemType, Item item) {
        inventory.get(itemType).remove(item);
    }

    public void addToSoldInventory(String itemType, Item item) {
        if (soldLogBook.containsKey(itemType)) {
            soldLogBook.get(itemType).add(item);
        }
        else {
            soldLogBook.put(itemType, new ArrayList<Item>(Arrays.asList(item)));
        }
    }

    public void displayInventory() {
        /*
        Displays everything in the inventory
         */

        for(String itemType: inventory.keySet()) {
            System.out.println("Type of Item: " + itemType);
            for (Item i: inventory.get(itemType)) {
                if (i.getDaySold() == -1 && i.getDayArrived() <= this.day) { // If it is not yet sold, display it.
                    System.out.println("\t Name: " + i.getName());
                    System.out.println("\t Purchase Price: " + Helper.round(i.getPurchasePrice()) + "$");
                    System.out.println("\t List Price: " + Helper.round(i.getListPrice()) + "$");
                    System.out.println("\t New? " + Constants.NEW_OR_USED_MAPPING.get(i.getIsNew()));
                    System.out.println("\t Day Arrived: " + i.getDayArrived());
                    System.out.println("\t Condition: " + Constants.CONDITION_MAPPING.get(i.getCondition()));

                    if (i instanceof Music) {
                        System.out.println("\t Band Name: " + ((Music) i).getBand());
                        System.out.println("\t Album Name: " + ((Music) i).getAlbum());
                    } else if (i instanceof Instrument) {

                        if (i instanceof Stringed) {
                            System.out.println("\t Is Electric: " + ((Stringed) i).getIsElectric());
                        } else if (i instanceof Wind) {

                            if (i instanceof Flute) {
                                System.out.println("\t Flute Type: " + ((Flute) i).getType());
                            } else if (i instanceof Harmonica) {
                                System.out.println("\t Harmonica Key: " + ((Harmonica) i).getKey());
                            }
                        }
                    } else if (i instanceof Clothing) {

                        if (i instanceof Hat) {
                            System.out.println("\t Hat Size: " + ((Hat) i).getHatSize());
                        } else if (i instanceof Shirt) {
                            System.out.println("\t Shirt Size: " + ((Shirt) i).getShirtSize());
                        }
                    } else if (i instanceof Accessory) {

                        if (i instanceof PracticeAmp) {
                            System.out.println("\t Wattage: " + ((PracticeAmp) i).getWattage());
                        } else if (i instanceof Cable) {
                            System.out.println("\t Cable Length: " + ((Cable) i).getLength());
                        } else if (i instanceof Strings) {
                            System.out.println("\t String Type: " + ((Strings) i).getType());
                        }
                    }
                    System.out.println();
                }
            }
        }
    }

    public double calcInventoryValue() {

        double inventoryVal = 0;

        for(String itemType: inventory.keySet()) {
            for (Item i: inventory.get(itemType)) {
                if (i.getDayArrived() <= this.getDay()) {
                    inventoryVal += i.getPurchasePrice();
                }
            }
        }
        return inventoryVal;
    }

    public double calcSoldValue() {

        double soldVal = 0;

        for(String itemType: soldLogBook.keySet()) {
            for (Item i: soldLogBook.get(itemType)) {
                soldVal += i.getSalePrice();
            }
        }
        return soldVal;
    }

    public ArrayList<String> itemsWithZeroStock() {

        ArrayList<String> zeroStockItems = new ArrayList<>();

        for(String itemType: inventory.keySet()) {
            if (inventory.get(itemType).size() == 0) {
                zeroStockItems.add(itemType);
            }
        }
        return zeroStockItems;
    }

    public void run(int numberOfDays) {
        /*
        Runs the Store for 'numberOfDays' Days.
         */
        for(int i = 1; i <= numberOfDays; i++) {
            day_logger.instantiate(i);
            System.out.println("Day "+i+":");
            int dayOfTheWeek = i % 7;
            if (dayOfTheWeek == 0) {
                System.out.println("On Sunday, no one worked.");
                resetDays();
            }
            else {
                sicknessCheck();
                Clerk c = chooseClerk();
                c.registerObserver(day_logger);
                c.ArriveAtStore(this);
                c.CheckRegister(this);

                ArrayList<String> zeroStockItems = c.DoInventory(this);
                c.PlaceAnOrder(this, zeroStockItems);

                c.OpenTheStore(this);
                c.CleanStore(this);
                c.LeaveTheStore(this);
                c.removeObserver(day_logger);
            }
            this.day += 1;
            //add an extra line for separating days
            day_logger.close();
            System.out.println();
            store_tracker.printInfo(this);
            System.out.println();
        }
        
        printSummary(numberOfDays);
    }

    public Clerk chooseClerk() {
        
    
        // Choose randomly from the current staff list
        Clerk c = (Clerk) staff.get(Helper.random.nextInt(staff.size()));
        // Re-choose a new clerk if he/she has already worked more than 2 days
        
        if (c.getDaysWorkedInARow() > 2 || c.getSick()) {
        // Keep choosing a clerk until you choose someone who hasn't worked 3 days or is not sick
            while (c.getDaysWorkedInARow() == 3 || c.getSick()) {
                c = (Clerk) staff.get(Helper.random.nextInt(staff.size()));
            }
        }
        
        // Set that clerk as active worker for today
        c.setIsActiveWorker(true);
        // Call incrementDayWorkedInRow method for handling the details of adding work days and assigning other clerks' work days to 0
        c.incrementDaysWorkedInARow(this);
        return c;
    }

    //only works on Sundays
    public void resetDays() {
        for(Staff s: staff) {
            s.setIsActiveWorker(false);
            s.setDaysWorkedInARow(0);
        }
    }
    
    public void printSummary(int days) {
        System.out.println("--- FINAL SUMMARY ---");
        System.out.println("The amount of money in the register at the end of " + days + " days was $" + Helper.round(this.registerAmount) + "\n");
        System.out.println("The amount of money added to the register from going to the bank during this time was $" + this.amountWithdrawnFromBank + "\n");
        System.out.println("The items remaining in inventory were as follows:");
        printInventory();
        System.out.println("\nThe total value of all these items is $" + Helper.round(calcInventoryValue()));
        System.out.println("\nThe sold items during this period were as follows:");
        printSoldItems();
        System.out.println("\nThe total value of all the sold items was $" + Helper.round(calcSoldValue()));
    }

    public void printInventory() {
        for(String itemType: inventory.keySet()) {
            for (Item i: inventory.get(itemType)) {
                System.out.println(i.getName() + ": $" + Helper.round(i.getPurchasePrice()));
            }
        }
    }

    public void printSoldItems() {
        for (int j = 0; j <= this.getDay(); j++) {
            for(String itemType: soldLogBook.keySet()) {
                for (Item i: soldLogBook.get(itemType)) {
                    if (i.getDaySold() == j) {
                        // Change day from [0,29] to [1,30]
                        System.out.println("On day " + (i.getDaySold() + 1) + ", a " + i.getName() + " sold for $" + Helper.round(i.getSalePrice()));
                    }
                }
            }
        }
    }

    public void sicknessCheck() {
        
        for (Staff s: staff) {
            s.setSick(false);
        }

        double chanceOfSick = Helper.random.nextDouble();
        if (chanceOfSick <= 0.1) {
            Clerk c = (Clerk) staff.get(Helper.random.nextInt(staff.size()));
            c.setSick(true);
            System.out.println(c.getName() + "was sick on day " + this.getDay());
        }
        
    }

    public double getRegisterAmount() {
        return registerAmount;
    }

    public void setRegisterAmount(double registerAmount) {
        this.registerAmount = registerAmount;
    }

    public double getAmountWithdrawnFromBank() {
        return amountWithdrawnFromBank;
    }

    public void setAmountWithdrawnFromBank(double amountWithdrawnFromBank) {
        this.amountWithdrawnFromBank = amountWithdrawnFromBank;
    }

    public HashMap<String, ArrayList<Item>> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, ArrayList<Item>> inventory) {
        this.inventory = inventory;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public ArrayList<Staff> getStaff() {
        return staff;
    }

    public void setStaff(ArrayList<Staff> staff) {
        this.staff = staff;
    }
}
