import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Store {

    double registerAmount;
    double amountWithdrawnFromBank;
    HashMap<String, ArrayList<Item>> inventory;
    HashMap<String, ArrayList<Item>> soldInventory;
    ArrayList<Staff> staff;

    Store() {
    }

    public void initialize(int numberofObjects) {

        /*
        Is Going to create 'numberofObjects' Objects of each type in the Store
        on the 0th Day.
         */

        this.registerAmount = 0;
        this.amountWithdrawnFromBank = 0;
        generateInventory(numberofObjects, Constants.CLASS_NAMES, 0, true);

        Clerk shaggy = new Clerk("Shaggy", 0);
        Clerk velma = new Clerk("Velma", 0);

        this.staff = new ArrayList<>();

        staff.add((Staff) shaggy);
        staff.add((Staff) velma);
    }

    public void generateInventory(int numberofObjects, ArrayList<String> itemTypes, int day, boolean isStartDay) {

        Constants.generateMaps(); // Declares all the constants and initializes them
        this.inventory = new HashMap<>();

        try {
            for (String className: itemTypes) {
                Class[] parameters = Constants.CLASS_PARAMETER_MAPPING.get(className);

                Class classObj = Class.forName(className);
                Constructor constructor = classObj.getConstructor(parameters);

                for(int i = 0; i < numberofObjects; i++) {
                    Object classInstance = constructor.newInstance(Helper.getParams(className, day, isStartDay).toArray());
                    Item it = ((Item) classInstance);
                    if (inventory.containsKey(className)) {
                        inventory.get(className).add(it);
                    }
                    else {
                        inventory.put(className, new ArrayList<Item>(Arrays.asList(it)));
                    }
                }
            }
        }

        catch(Exception e) {
            System.out.println("Errors");
            e.printStackTrace();
        }
    }

    public void displayInventory() {
        /*
        Displays everything in the inventory
         */

        for(String itemType: inventory.keySet()) {
            System.out.println("Type of Item: " + itemType);
            for (Item i: inventory.get(itemType)) {
                if (i.getDaySold() == -1) { // If it is not yet sold, display it.
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
            for (Item i : inventory.get(itemType)) {
                if (i.getDaySold() == -1) {
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
                c.PlaceAnOrder(this, zeroStockItems, i);
            }

        }
    }

    public Clerk chooseClerk() {
        int clerkValue = Helper.random.nextInt(2);
        Clerk c = (Clerk) staff.get(clerkValue);
        if (c.getDaysWorkedInARow() == 3) {
            c.setIsActiveWorker(false);
            c.setDaysWorkedInARow(0);

            c = (Clerk) staff.get(Helper.FlipNumber(clerkValue));
        }
        c.setIsActiveWorker(true);
        c.incrementDaysWorkedInARow();
        return c;
    }

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
}
