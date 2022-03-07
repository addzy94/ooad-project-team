import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Store {

    private String storeName;

    
    private static int day;

    private double registerAmount;
    private double amountWithdrawnFromBank;

    private HashMap<String, ArrayList<Item>> inventory;
    private HashMap<String, ArrayList<Item>> soldLogBook;
    private HashMap<String, ArrayList<Item>> orderedItems;

    private static ArrayList<Staff> staff;
    private Logger day_logger;
    private Store otherStore;
    private Clerk clerkToday;

    private boolean isServing = true;

    Store(int n, String name, Logger logger) {
        // Assign 3 objects per item (lowest subclass) by the time we initialize a store
        this.initialize(n);
        storeName = name;
        day_logger = logger;
    }

    public Store getOtherStore(){
        return this.otherStore;
    }

    public void setOtherStore(Store otherStore){
        this.otherStore = otherStore;
    }

    public Clerk getClerkToday(){
        return this.clerkToday;
    }

    public void setClerkToday(Clerk clerkToday){
        this.clerkToday = clerkToday;
    }

    public boolean getIsServing(){
        return this.isServing;
    }

    public void setIsServing(boolean isServing){
        this.isServing = isServing;
    }

    public void initialize(int numberofObjects) {

        /*
        Is Going to create 'numberofObjects' Objects of each type in the Store
        on the 0th Day.
         */

        day = 1;
        this.registerAmount = 0;
        this.amountWithdrawnFromBank = 0;

        this.inventory = new HashMap<>();
        this.soldLogBook = new HashMap<>();
        this.orderedItems = new HashMap<>();
        //this.staff = new ArrayList<>();

        Constants.generateMaps(); // Declares all the constants and initializes them

        generateInventory(numberofObjects, Constants.CLASS_NAMES, true);
    }

    public void generateInventory(int numberOfObjects, ArrayList<String> itemTypes, boolean isStartDay) {

        // Handles the condition when input parameter in string does not belong to the classes we already defined
        try {
            for (String itemType: itemTypes) {
                for(int i = 0; i < numberOfObjects; i++) {
                    Item item = Helper.createItem(itemType);
                    item.setDayArrived(getDay());
                    // Just add the item to the inventory if it's the first day.
                    if (isStartDay) {
                        addToRegistry(inventory, itemType, item);
                    }
                    else {
                        // If we can afford the item at this moment, buy it
                        if (item.getPurchasePrice() <= getRegisterAmount()) {
                            System.out.println("The store purchased the " + item.getName() +
                                    " " + itemType + " which costs " + Helper.round(item.getPurchasePrice()) + "$");
                            item.setDayArrived(day + Helper.random.nextInt(3) + 1);

                            // Pay for the item using the register
                            this.setRegisterAmount(this.getRegisterAmount() - item.getPurchasePrice());

                            // Add the item to orderedItems
                            addToRegistry(orderedItems, itemType, item);
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
            classInstance = constructor.newInstance(Helper.getParams(itemType).toArray());
        }
        catch(Exception e) {
            System.out.println("Errors");
            e.printStackTrace();
        }

        return ((Item) classInstance);
    }

    public void addToRegistry(HashMap<String, ArrayList<Item>> registry, String itemType, Item item) {

        /*
        --- POLYMORPHISM ---
            All of the items generated are of its own kind and exhibit polymorphic behavior.
            But as they are all from the type 'Item', so they can be added to the 'inventory' HashMap.
        --- POLYMORPHISM ---
         */

        // Check if there's a key in the hashtable for storing the corresponding item time
        if (registry.containsKey(itemType)) {
            registry.get(itemType).add(item);
        }
        else {
            registry.put(itemType, new ArrayList<Item>(Arrays.asList(item)));
        }
    }

    public void removeFromRegistry(HashMap<String, ArrayList<Item>> registry, String itemType, Item item) {
        registry.get(itemType).remove(item);
    }

    public void displayInventory() {
        /*
        Displays everything in the inventory
         */

        for(String itemType: inventory.keySet()) {
            System.out.println("Type of Item: " + itemType);
            for (Item i: inventory.get(itemType)) {
                if (i.getDaySold() == -1 && i.getDayArrived() <= day) { // If it is not yet sold, display it.
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
                if (i.getDayArrived() <= getDay()) {
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

    public void runDay() {
        /*
        Runs the Store for One Day
         */
        day_logger.instantiate(getDay(), this, false);
        System.out.println();
        System.out.println("Day "+getDay()+":");
        int dayOfTheWeek = getDay() % 7;
        if (dayOfTheWeek == 0) {
            System.out.println("On Sunday, no one worked.");
            resetDays();
        }
        else {
            clerkToday.registerObserver(day_logger);
            clerkToday.ArriveAtStore(this);
            clerkToday.CheckRegister(this);
            ArrayList<String> zeroStockItems = clerkToday.DoInventory(this);
            clerkToday.PlaceAnOrder(this, zeroStockItems);

            clerkToday.OpenTheStoreAuto(this);
            clerkToday.CleanStore(this);
            clerkToday.LeaveTheStore(this);
            clerkToday.removeObserver(day_logger);
            clerkToday.setStore(null);
            this.setClerkToday(null);
        }
        day_logger.close();
    }

    // Split the normal runDay method into the following two parts:
    // prepareSpecialDay that handles every step before openTheStore method
    public void prepareSpecialDay(Clerk c) {
        /*
        Runs the Store for One Day
         */
        System.out.println();
        System.out.println("Day "+getDay()+":");
        clerkToday.registerObserver(day_logger);
        clerkToday.ArriveAtStore(this);
        clerkToday.CheckRegister(this);

        ArrayList<String> zeroStockItems = clerkToday.DoInventory(this);
        clerkToday.PlaceAnOrder(this, zeroStockItems);
    }

    // shopSpecialDay that handles every step after preparation process
    public void shopSpecialDay(Clerk c) {
        /*
        Runs the Store for The Last Day.
         */
        clerkToday.OpenTheStoreCustom(this, this.getOtherStore());
        clerkToday.CleanStore(this);
        clerkToday.LeaveTheStore(this);
        clerkToday.removeObserver(day_logger);
        clerkToday.setStore(null);
        this.setClerkToday(null);
    }

    public Clerk chooseClerk() {
        // Choose randomly from the current staff list
        Clerk c = (Clerk) staff.get(Helper.random.nextInt(staff.size()));
        // Re-choose a new clerk if he/she has already worked more than 2 days

        if (c.getDaysWorkedInARow() > 2 || c.getSick() || c.getIsActiveWorker()) {
            // Keep choosing a clerk until you choose someone who hasn't worked 3 days or is not sick
            while (c.getDaysWorkedInARow() == 3 || c.getSick() || c.getIsActiveWorker()) {
                c = (Clerk) staff.get(Helper.random.nextInt(staff.size()));
            }
        }

        // Set that clerk as active worker for today
        c.setIsActiveWorker(true);
        c.setStore(this);
        // Call incrementDayWorkedInRow method for handling the details of adding work days and assigning other clerks' work days to 0
        c.incrementDaysWorkedInARow(this);

        // Assign number of item sold and bought to 0 by the beginning of the day
        c.setNumberOfItemsSold(0);
        c.setNumberOfItemsBought(0);

        //clerkToday = c;
        this.setClerkToday(c);

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
        System.out.println("--- FINAL SUMMARY FOR STORE " + getStoreName() + " ---");
        System.out.println("The amount of money in the register at the end of " + days + " days was $" + Helper.round(this.registerAmount) + "\n");
        System.out.println("The amount of money added to the register from going to the bank during this time was $" + this.amountWithdrawnFromBank + "\n");
        System.out.println("The items remaining in inventory were as follows:");
        printInventory();
        System.out.println("\nThe total value of all these items is $" + Helper.round(calcInventoryValue()));
        System.out.println("\nThe sold items during this period were as follows:");
        printSoldItems();
        System.out.println("\nThe total value of all the sold items was $" + Helper.round(calcSoldValue()));
        System.out.println();
    }

    public void printInventory() {
        for(String itemType: inventory.keySet()) {
            for (Item i: inventory.get(itemType)) {
                System.out.println(i.getName() + ": $" + Helper.round(i.getPurchasePrice()));
            }
        }
    }

    public void printSoldItems() {
        for (int j = 0; j <= getDay(); j++) {
            for(String itemType: soldLogBook.keySet()) {
                for (Item i: soldLogBook.get(itemType)) {
                    if (i.getDaySold() == j) {
                        System.out.println("On day " + (i.getDaySold()) + ", a " + i.getName() + " sold for $" + Helper.round(i.getSalePrice()));
                    }
                }
            }
        }
    }
  
    public static void sicknessCheck() {
        for (Staff s: staff) {
            s.setSick(false);
        }

        double chanceOfSick = Helper.random.nextDouble();
        if (chanceOfSick <= 0.1) {
            Clerk c = (Clerk) staff.get(Helper.random.nextInt(staff.size()));
            c.setSick(true);
            System.out.println(c.getName() + " was sick on day " + getDay());
            double chanceOfSick2 = Helper.random.nextDouble();
            if (chanceOfSick2 <= 0.1) {
                boolean anotherClerk = false;
                Clerk c2 = (Clerk) staff.get(Helper.random.nextInt(staff.size()));
                while (!anotherClerk) {
                    c2 = (Clerk) staff.get(Helper.random.nextInt(staff.size()));
                    if (c2.getSick() == false) {
                        anotherClerk = true;
                    }
                }

                c2.setSick(true);
                System.out.println(c2.getName() + " was also sick on day " + getDay());
            }
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

    public HashMap<String, ArrayList<Item>> getOrderedItems() {
        return orderedItems;
    }

    public HashMap<String, ArrayList<Item>> getSoldLogBook() {
        return soldLogBook;
    }

    public static int getDay() {
        return day;
    }

    public static void goToNextDay() {
        day = day + 1;
    }

    public static ArrayList<Staff> getStaff() {
        return staff;
    }

    public static void setStaff(ArrayList<Staff> new_staff) {
        staff = new_staff;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String new_name) {
        storeName = new_name;
    }
}