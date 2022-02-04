import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Store {

    int day;
    double registerAmount;
    double amountWithdrawnFromBank;
    HashMap<String, ArrayList<Item>> inventory;
    HashMap<String, ArrayList<Item>> soldLogBook;
    ArrayList<Staff> staff;

    Store() {
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

        Constants.generateMaps(); // Declares all the constants and initializes them

        generateInventory(numberofObjects, Constants.CLASS_NAMES, true);

        Clerk shaggy = new Clerk("Shaggy", 0);
        Clerk velma = new Clerk("Velma", 0);

        this.staff = new ArrayList<>();

        staff.add((Staff) shaggy);
        staff.add((Staff) velma);
    }

    public void generateInventory(int numberofObjects, ArrayList<String> itemTypes, boolean isStartDay) {

        //handle the condition when input parameter in string does not belong to the classes we already defined
        try {
            for (String itemType: itemTypes) {
                for(int i = 0; i < numberofObjects; i++) {
                    Item item = createItem(itemType);
                    if (isStartDay) {
                        addToInventory(itemType, item);
                    }
                    else {
                        item.setDayArrived(day + Helper.random.nextInt(3) + 1);
                        addToInventory(itemType, item);
                    }
                        //if we can afford the item at this moment, buy it, and put it into the inventory list directly with a dayArrive counter been set
                        if (item.getPurchasePrice() <= getRegisterAmount()) {
                            item.setDayArrived(day + Helper.random.nextInt(3) + 1);
                            this.setRegisterAmount(this.getRegisterAmount() - item.getPurchasePrice());
                            addToInventory(itemType, item);
                        }
                        //else, don't buy it since we are out of founds
                        else {
                            System.out.println("Couldn't purchase the " + item.getName() + " " + itemType + " as we were out of funds.");
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
            //initialize a list of class in the following way: [String, Int, bla, bla] for later use of initializing the corresponding item type
            Class[] parameters = Constants.CLASS_PARAMETER_MAPPING.get(itemType);
            //intialize the corresponding class object based on the given String itemType
            Class classObj = Class.forName(itemType);
            //combine the two lines above for really generating a constructor
            Constructor constructor = classObj.getConstructor(parameters);
            //generate an object by calling Helper class that helps you put all the necessary parameter (price, day, etc) for generating it.
            classInstance = constructor.newInstance(Helper.getParams(itemType, this.day).toArray());
        }
        catch(Exception e) {
            System.out.println("Errors");
            e.printStackTrace();
        }

        return ((Item) classInstance);
    }

    public void addToInventory(String itemType, Item item) {
        //check if there's a key in the hashtable for storing the corresponding item time
        if (inventory.containsKey(itemType)) {
            inventory.get(itemType).add(item);
        }
        else {
            inventory.put(itemType, new ArrayList<Item>(Arrays.asList(item)));
        }
    }

    public void removeFromInventory(String itemType, int itemIndex) {

        if (inventory.get(itemType).size() > itemIndex) {
            inventory.get(itemType).remove(itemIndex);
        }
        else {
            System.out.println("Error! Item is not found in the inventory!");
        }

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
                    System.out.println("\t Purchase Price: " + i.getPurchasePrice());
                    System.out.println("\t List Price: " + i.getListPrice());
                    System.out.println("\t New? " + Constants.NEW_OR_USED_MAPPING.get(i.getIsNew()));
                    System.out.println("\t Day Arrived: " + i.getDayArrived());
                    System.out.println("\t Condition: " + Constants.CONDITION_MAPPING.get(i.getCondition()));
                    System.out.println("\t Sale Price: " + i.getSalePrice());
                    System.out.println("\t Day Sold: " + i.getDaySold());

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

            int dayOfTheWeek = i % 7;
            String day = Constants.DAY_MAPPING.get(dayOfTheWeek);
            if (dayOfTheWeek == 0) {
                System.out.println("On Sunday, no one worked.");
                resetDays();
            }
            else {
                Clerk c = chooseClerk();
                c.ArriveAtStore(i);
                c.CheckRegister(this);
                ArrayList<String> zeroStockItems = c.DoInventory(this);
                c.PlaceAnOrder(this, zeroStockItems);
                c.OpenTheStore(this);
                c.CleanStore(this);
                c.LeaveTheStore(this);
            }
        this.day += 1;
        }
    }

    //handled by Sitong Lu
    public Clerk chooseClerk() {
        //chose randomly from the current staff list
        int clerkValue = Helper.random.nextInt(staff.size());
        //re-choose a new clerk until he/she hasn't worked in a row for 3 days already
        while(staff.get(clerkValue).daysWorkedInARow != 3){
            clerkValue = Helper.random.nextInt(staff.size());
        }
        //get that clerk using clerk id
        Clerk c = (Clerk) staff.get(clerkValue);
        //set that clerk as active worker for today
        c.setIsActiveWorker(true);
        //call incrementDayWrokedInRow method for handling the details of adding work days and assigning other clerks' work days to 0
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
