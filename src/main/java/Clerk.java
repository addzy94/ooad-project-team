import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/*
--- INHERITANCE ---
    Clerk worker type inherits from Staff and gets attributes like name and days worked which
    is an example of inheritance.
--- INHERITANCE ---
*/

public class Clerk extends Staff implements Subject{

    private int damageChance;
    private TuningStrategy tuningStrategy;
    private ArrayList<Observer> observers;
    private String message;
    private int numberItemsBought;
    private int numberOfItemsSold;
    private Store currentStore;

    public Clerk(String name, int daysWorkedInARow, int damage_chance, TuningStrategy tuningStrategy) {
        super(name, daysWorkedInARow);
        this.damageChance = damage_chance;
        this.tuningStrategy = tuningStrategy;
        this.observers = new ArrayList<>();
    }

    public void ArriveAtStore(Store s) {

        System.out.println("--------------------ARRIVAL--------------------");

        int day = Store.getDay();

        // Change day from [0,29] to [1,30]
        setMessage("Clerk " + this.getName() + " arrived at " + s.getStoreName() + " on day " + (day) + ".");
        System.out.println("Clerk " + this.getName() + " arrived at " + s.getStoreName() + " on day " + (day) + ".");

        // Clerk needs to check orderedItems list
        HashMap<String, ArrayList<Item>> orderedList = s.getOrderedItems();
        HashMap<String, ArrayList<Item>> arrivesToday = new HashMap<>();

        for (String itemType: orderedList.keySet()) {
            for (Item item: orderedList.get(itemType)) {

                // If an item arrives today, add it to the temperate arrivesToday hashmap
                if (item.getDayArrived() == Store.getDay()) {
                    System.out.println("New item: " + item.getName() + " has arrived! Clerk " + this.getName() + " added it to the stock.");
                    if (arrivesToday.containsKey(itemType)) {
                        arrivesToday.get(itemType).add(item);
                    } else {
                        arrivesToday.put(itemType, new ArrayList<Item>(Arrays.asList(item)));
                    }
                }
            }
        }

        // From the list of ordered items that arrive today, add them to inventory list, and remove them from ordered item list.
        for(String itemType: arrivesToday.keySet()) {
            for (Item item : arrivesToday.get(itemType)) {
                s.addToRegistry(s.getInventory(), itemType, item);
                s.removeFromRegistry(s.getOrderedItems(), itemType, item);
            }
        }
    }

    /*
    --- COHESION ---
    The clerk class takes care of the going to bank action and is unrelated to the Store class.
    This is an example of Cohesion at play.
    --- COHESION ---
     */
    public void GoToBank(Store s, double currentAmount) {

        double alreadyWithdrawn = s.getAmountWithdrawnFromBank();
        s.setAmountWithdrawnFromBank(alreadyWithdrawn + 1000);
        s.setRegisterAmount(currentAmount + 1000);

        System.out.println("Clerk " + this.getName() + " withdrew $1000 from the bank, making new register amount as: $" + Helper.round(s.getRegisterAmount()));
        setMessage("Clerk " + this.getName() + " withdrew $1000 from the bank, making new register amount as: $" + Helper.round(s.getRegisterAmount()));
    }

    public void CheckRegister(Store s) {

        System.out.println("--------------------REGISTER CHECK--------------------");

        double registerMoney = Helper.round(s.getRegisterAmount());
        System.out.println("Clerk " + this.getName() + " counted $" + registerMoney + " in the register.");
        setMessage("Clerk " + this.getName() + " counted $" + registerMoney + " in the register.");
        if (registerMoney < 75.00) {
            this.GoToBank(s, registerMoney);
        }
    }

    public ArrayList<String> DoInventory(Store s) {

        System.out.println("--------------------INVENTORY STOCK VALUE CHECK--------------------");

        int damagedByTuning = 0;
        int totalInventory = s.getInventory().size();
        setMessage("There were " + totalInventory + " items in inventory at " + s.getStoreName() + ".");
        double inventoryValue = Helper.round(s.calcInventoryValue());
        System.out.println("Clerk " + this.getName() + " calculated the inventory value to be: $" + inventoryValue);
        setMessage("The inventory value was $" + inventoryValue);
        ArrayList<String> zeroStockItems = s.itemsWithZeroStock();

        // Not stocking clothing items anymore
        zeroStockItems.removeAll(Constants.CLOTHING_ITEM_TYPES);

        System.out.println("--------------------MUSIC INVENTORY TUNING--------------------");

        // Clerks start to tune the Player, Wind, and Stringed Items
        HashMap<String, ArrayList<Item>> storeInv = s.getInventory();
        HashMap<String, ArrayList<Item>> tunedItems = new HashMap<>();

        boolean priorTune;
        boolean postTune;

        for(String itemType: storeInv.keySet()) {
            for (Item item : storeInv.get(itemType)) {

                if (item instanceof Wind || item instanceof Player || item instanceof Stringed) {

                    if (tunedItems.containsKey(itemType)) {
                        tunedItems.get(itemType).add(item);
                    } else {
                        tunedItems.put(itemType, new ArrayList<Item>(Arrays.asList(item)));
                    }
                }
            }
        }

        for(String itemType: tunedItems.keySet()) {
            for (Item item : tunedItems.get(itemType)) {

                s.removeFromRegistry(storeInv, itemType, item);
                Item tunedItem;

                if (item instanceof Wind) {
                    priorTune = ((Wind) item).getIsAdjusted();
                    tunedItem = tuningStrategy.tune(item);
                    postTune = ((Wind) item).getIsAdjusted();
                }

                else if (item instanceof Player) {
                    priorTune = ((Player) item).getIsEqualized();
                    tunedItem = tuningStrategy.tune(item);
                    postTune = ((Player) item).getIsEqualized();
                }

                else {
                    priorTune = ((Stringed) item).getIsTuned();
                    tunedItem = tuningStrategy.tune(item);
                    postTune = ((Stringed) item).getIsTuned();
                }

                // Remove the item when it breaks

                if (priorTune == true && postTune == false && Helper.random.nextInt(10) == 0) {
                    degradeItem(s, itemType, item, "tuning");
                    damagedByTuning = damagedByTuning + 1;
                }

                else if (priorTune != postTune) {
                    System.out.println(item.getName() + " " + itemType +
                            " changed it's property from " + priorTune + " to " + postTune + ".");
                    s.addToRegistry(storeInv, itemType, tunedItem);
                }

                else {
                    System.out.println("Attempted to tune " + item.getName() + " " + itemType +
                            " but couldn't change it's property which was " + priorTune + ".");
                    s.addToRegistry(storeInv, itemType, tunedItem);
                }
            }
        }
        setMessage(damagedByTuning + " items were damaged during tuning at " + s.getStoreName() + ".");
        return zeroStockItems;
    }

    public void PlaceAnOrder(Store s, ArrayList<String> zeroStockItems) {

        System.out.println("--------------------PLACING ORDERS--------------------");

        s.generateInventory(3, zeroStockItems, false);
        setMessage("3 new items were ordered by " + s.getStoreName() + " on day " + Store.getDay());
    }

    public void OpenTheStoreAuto(Store s) {

        System.out.println("--------------------STORE BUSINESS--------------------");

        numberItemsBought = 0;
        numberOfItemsSold = 0;

        int poissonResult = -1;
        int mean = 3;
        double sum = 0.0;
        while (sum < 1.0) { //https://hpaulkeeler.com/simulating-poisson-random-variables-direct-method/
            sum = sum + ((-1.0 / mean) * Math.log(Helper.random.nextDouble()));
            poissonResult++;
        }

        int buyingCustomersCount = poissonResult + 2; // Arriving buying customers dictated by a Poisson distribution
        int sellingCustomerCount = Helper.random.nextInt(4) + 1;

        ArrayList<String> buyingCustomerRequirements = Helper.customerRequirements(Constants.CLASS_NAMES, buyingCustomersCount);
        ArrayList<String> sellingCustomerRequirements = Helper.customerRequirements(Constants.CLASS_NAMES, sellingCustomerCount);

        for (int i = 0; i < buyingCustomersCount; i++) {
            String customerName = Constants.CUSTOMER_NAMES.get(Helper.random.nextInt(Constants.CUSTOMER_NAMES.size()));
            String customerRequiredItem = buyingCustomerRequirements.get(i);
            System.out.println("-----");
            BuyItemTransaction(s, customerRequiredItem, customerName);
        }

        for (int i = 0; i < sellingCustomerCount; i++) {
            String customerName = Constants.CUSTOMER_NAMES.get(Helper.random.nextInt(Constants.CUSTOMER_NAMES.size()));
            String customerBroughtItem = sellingCustomerRequirements.get(i);
            System.out.println("-----");
            SellItemTransaction(s, customerBroughtItem, customerName);
        }
        setMessage(numberItemsBought + " items were bought by " + s.getStoreName() +".");
        setMessage(numberOfItemsSold + " items were sold by " + s.getStoreName() +".");
    }

    // a customized OpenTheStore method that handles the decision by taking user input
    public void OpenTheStoreCustom(Store s, Store otherStore) {

        System.out.println("--------------------STORE BUSINESS--------------------");

        numberItemsBought = 0;
        numberOfItemsSold = 0;

        boolean isServing = s.getIsServing(); // Check if the user has entered 7 for stopping the OpenTheStoreCustom method for all stores

        if(isServing){ // If the store is still providing service for customer
            boolean isCurrentStore = true; // Check if the customer is shopping in the current store that this clerk stays in.
            Clerk otherClerk = otherStore.getClerkToday();

            System.out.println("This store for today is: " + s.getStoreName());
            System.out.println("The clerk for today is: " + this.getName() + " who has sold " + this.getNumberOfItemsSold() + " and bought " + this.getNumberOfItemsBought());
            System.out.println("The other store for today is: " + otherStore.getStoreName());
            System.out.println("The other clerk for today is: " + otherClerk.getName() + " who has sold " + otherClerk.getNumberOfItemsSold() + " and bought " + this.getNumberOfItemsBought());

            String command = "";
            String customerName = "(controlled by user)";
            // Prepare commands
            CommandController remote = new CommandController();

            BuyFromClerkCommand buyFromClerk = new BuyFromClerkCommand(this, s, customerName);
            SellToClerkCommand sellToClerk = new SellToClerkCommand(this, s, customerName);
            AskClerkNameCommand askClerkName = new AskClerkNameCommand(this);
            AskClerkTimeCommand askClerkTime = new AskClerkTimeCommand(this);
            BuyCustomGuitarKitFromClerkCommand buyCustomGuitarKitFromClerk = new BuyCustomGuitarKitFromClerkCommand(this, s, customerName);
            SwitchStore switchStore = new SwitchStore(s, otherStore, buyFromClerk, sellToClerk, askClerkName, askClerkTime, buyCustomGuitarKitFromClerk, customerName);

            // Print menu for OpenStore process
            System.out.println("Please Choose From The Following Actions:");
            System.out.println("1. Switch Store");
            System.out.println("2. Ask For Clerk's Name");
            System.out.println("3. Ask For The Current Time");
            System.out.println("4. Sell An Item");
            System.out.println("5. Buy An Item");
            System.out.println("6. Buy A Custom Guitar Kit");
            System.out.println("7. End The Shopping Process");
            System.out.println();

            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter The Corresponding Number Of Your Choice:");
            command = myObj.nextLine();  // Read user input
            // Run the interactions until the user enters 7 to quit.
            while(! command.equals("7")){
                // If the customer selects switching a store
                if(command.equals("1")){
//                remote.setCommand(switchStore);
//                remote.buttonPressed();

                    
                    if(isCurrentStore){ // if the customer is currently at this store, we switch command's reference to the other clerk and the other store
                        System.out.println("Customer switched to " + otherStore.getStoreName());
                        setMessage("Customer switched to " + otherStore.getStoreName());
                        System.out.println("Now The Store is: " + otherStore.getStoreName()); //Delete now?
                        System.out.println();

                        buyFromClerk = new BuyFromClerkCommand(otherClerk, otherStore, customerName);
                        sellToClerk = new SellToClerkCommand(otherClerk, otherStore, customerName);
                        askClerkName = new AskClerkNameCommand(otherClerk);
                        askClerkTime = new AskClerkTimeCommand(otherClerk);
                        buyCustomGuitarKitFromClerk = new BuyCustomGuitarKitFromClerkCommand(otherClerk, otherStore, customerName);

                        isCurrentStore = false; // re-assign the bool parameter to mark that the customer is currently shopping in the other store
                    }
                    else{ // if if the customer is currently at this store, we switch command's reference back to this clerk and this store
                        System.out.println("Customer switched to " + s.getStoreName());
                        setMessage("Customer switched to " + s.getStoreName());
                        System.out.println("Now The Store is: " + s.getStoreName());
                        System.out.println();

                        buyFromClerk = new BuyFromClerkCommand(this, s, customerName);
                        sellToClerk = new SellToClerkCommand(this, s, customerName);
                        askClerkName = new AskClerkNameCommand(this);
                        askClerkTime = new AskClerkTimeCommand(this);
                        buyCustomGuitarKitFromClerk = new BuyCustomGuitarKitFromClerkCommand(this, s, customerName);

                        isCurrentStore = true; // re-assign the bool parameter to mark that the customer is currently shopping in this store
                    }

                }

                // If the customer selects asking for clerk's name
                else if(command.equals("2")){
                    remote.setCommand(askClerkName);
                    remote.buttonPressed();
                }

                // If the customer selects asking the clerk what time it is
                else if(command.equals("3")){
                    remote.setCommand(askClerkTime);
                    remote.buttonPressed();
                }

                // If the customer selects selling an item to the clerk
                else if(command.equals("4")){
                    remote.setCommand(sellToClerk);
                    remote.buttonPressed();
                }

                // If the customer selects buying an item from the clerk
                else if(command.equals("5")){
                    remote.setCommand(buyFromClerk);
                    remote.buttonPressed();
                }

                // If the customer selects buying a custom guitar git from the clerk
                else if(command.equals("6")){
                    remote.setCommand(buyCustomGuitarKitFromClerk);
                    remote.buttonPressed();
                }

                // Otherwise wrong command, please try again
                else{
                    System.out.println("Wrong command! Please try again!");
                    System.out.println();
                }

                // Asking for the next input
                System.out.println("Enter The Corresponding Number Of Your Choice:");
                command = myObj.nextLine();
            }

            // By the time when user pressed 7 to end this interactions
            System.out.println("Customer decided to end the interactions.");
            System.out.println();
            // Set isServing to false for all stores
            s.setIsServing(false);
            otherStore.setIsServing(false);

            setMessage(numberItemsBought + " items were bought by " + s.getStoreName() +".");
            setMessage(numberOfItemsSold + " items were sold by " + s.getStoreName() +".");

            setMessage(otherClerk.getNumberOfItemsBought() + " items were bought by " + otherStore.getStoreName() +".");
            setMessage(otherClerk.getNumberOfItemsSold() + " items were sold by " + otherStore.getStoreName() +".");
        }

        else{
            System.out.println(s.getStoreName() + "has closed since the user entered has already ended the interactions.");
        }

    }

    public void BuyItemTransaction(Store s, String itemType, String customerName) {

        HashMap<String, ArrayList<Item>> inventory = s.getInventory();
        HashMap<String, ArrayList<Item>> soldLogBook = s.getSoldLogBook();

        ArrayList<Item> itemsOfRequiredType = inventory.get(itemType);
        int sizeOfRequiredItems = itemsOfRequiredType.size();

        if (sizeOfRequiredItems == 0) {
            System.out.println("Customer " + customerName + " left the store without buying the " + itemType + " as there was no stock.");
        }
        else {

            // Get a random item, and it's list price from the store
            Item itemChosen = itemsOfRequiredType.get(Helper.random.nextInt(sizeOfRequiredItems));
            double listPrice = itemChosen.getListPrice();

            /*
            Initialize the initialBuyDecision and discountBuyDecision in a different method
            so we can vary the value due to different conditions
             */
            boolean[] buyDecisionArray = GenerateBuyDecision(itemChosen);
            boolean initialBuyDecision = buyDecisionArray[0];

            if (!initialBuyDecision) {
                System.out.println("Customer " + customerName + " didn't want to buy the " + itemType + " at the list price.");
                System.out.println("Clerk " + this.getName() + " offered a 10% discount");
                boolean discountBuyDecision = buyDecisionArray[1];
                if (!discountBuyDecision) {
                    System.out.println("Customer " + customerName + " left the store without buying a " + itemType + " even at discounted price!");
                }
                else {
                    itemChosen.setDaySold(Store.getDay());
                    itemChosen.setSalePrice(.9 * listPrice);
                    System.out.println("Customer " + customerName + " bought a " +
                            Constants.NEW_OR_USED_MAPPING.get(itemChosen.getIsNew()) + " " +
                            itemChosen.getName() + " " + itemType + " in " +
                            Constants.CONDITION_MAPPING.get(itemChosen.getCondition()) +
                            " condition at 10% discount for $" + Helper.round(itemChosen.getSalePrice()));

                    // Add that item to the soldLogBook
                    s.removeFromRegistry(inventory, itemType, itemChosen);
                    s.addToRegistry(soldLogBook, itemType, itemChosen);
                    numberOfItemsSold = numberOfItemsSold + 1;

                    /*
                    Call OptionalSell method, which contains applying decorator pattern to itemChosen for modifying its sale price
                    if the customer is going to buy more than one item at once
                    */

                    // Get initial item price
                    double initialItemPrice = itemChosen.getSalePrice();

                    itemChosen = OptionalBuy(s, itemChosen, customerName); // Run optional sell method for adding the item's sale price under special scenario.

                    if (itemChosen.getSalePrice() > initialItemPrice) {
                        System.out.println("With add-ons, the total price was $" + Helper.round(itemChosen.getSalePrice()));
                    }
                    else { System.out.println("No add-ons were bought."); }
                    s.setRegisterAmount(s.getRegisterAmount() + itemChosen.getSalePrice()); // Sets the register amount to the sum of the prices of all the items in the transaction

                }
            }
            else {
                itemChosen.setDaySold(Store.getDay());
                itemChosen.setSalePrice(listPrice);
                System.out.println("Customer " + customerName + " bought a " +
                        Constants.NEW_OR_USED_MAPPING.get(itemChosen.getIsNew()) + " " +
                        itemChosen.getName() + " " + itemType + " in " +
                        Constants.CONDITION_MAPPING.get(itemChosen.getCondition()) +
                        " condition at list price for $" + Helper.round(itemChosen.getSalePrice()));

                // Add that item to the soldLogBook
                s.removeFromRegistry(inventory, itemType, itemChosen);
                s.addToRegistry(soldLogBook, itemType, itemChosen);
                numberOfItemsSold = numberOfItemsSold + 1;

                /*
                Call OptionalSell method, which contains applying decorator pattern to itemChosen for modifying its sale price
                if the customer is going to buy more than one item at once
                */
                double initialItemPrice = itemChosen.getSalePrice();

                itemChosen = OptionalBuy(s, itemChosen, customerName); // Run optional sell method for adding the item's sale price under special scenario.

                if (itemChosen.getSalePrice() > initialItemPrice) {
                    System.out.println("With add-ons, the total price was $" + Helper.round(itemChosen.getSalePrice()));
                }
                else { System.out.println("No add-ons were bought."); }
                s.setRegisterAmount(s.getRegisterAmount() + itemChosen.getSalePrice()); // Sets the register amount to the sum of the prices of all the items in the transaction

            }

        }
    }

    public Item OptionalBuy(Store s, Item itemChosen, String customerName){
        /*
        Applies decorator pattern to itemChosen for modifying its sale price
        if the customer is going to buy more than one item at once
        */

        // Extra condition built for Project 3:
        if (itemChosen.getDaySold() != -1){ // If item chosen already been sold
            if (itemChosen instanceof Stringed){ // If a stringed instrument is sold, there is a chance of selling accessories as well.
                boolean isElectric = ((Stringed) itemChosen).getIsElectric();

                if (isElectric){ // If the stringed instrument is electric, there is:
                    System.out.println("The stringed instrument Customer " + customerName + " bought is electric.");
                    int possible_result = Helper.random.nextInt(100);
                    if (possible_result < 20){ // a 20% chance of selling a single GigBag
                        itemChosen = new SellAccessory(itemChosen, s, "GigBag", customerName, this);
                    }
                    possible_result = Helper.random.nextInt(100); // re-generate another result
                    if (possible_result < 25){ // a 25% chance of selling a single PracticeAmp
                        itemChosen = new SellAccessory(itemChosen, s, "PracticeAmp", customerName, this);
                    }
                    possible_result = Helper.random.nextInt(100); // re-generate another result
                    if (possible_result < 30){ // a 30% chance of selling 1 or 2 Cables
                        int numberOfTimes = Helper.random.nextInt(2); // generate a value that is either 0 or 1
                        for (int i = numberOfTimes; i < 2; i++){ // Run the loop for 1 or 2 times
                            itemChosen = new SellAccessory(itemChosen, s, "Cable", customerName, this);
                        }
                    }
                    possible_result = Helper.random.nextInt(100); // re-generate another result
                    if (possible_result < 40){ // a 40% chance of selling 1 to 3 Strings.
                        int numberOfTimes = Helper.random.nextInt(3); // generate a value that is either 0, 1, or 2
                        for (int i = numberOfTimes; i < 3; i++){ // Run the loop for 1 or 2 or 3 times
                            itemChosen = new SellAccessory(itemChosen, s, "Strings", customerName, this);
                            //System.out.println("Now price is: "+ itemChosen.getSalePrice());
                        }
                    }
                }

                else{ // If the Stringed instrument is not electric, there is:
                    System.out.println("The stringed instrument Customer " + customerName + " bought is not electric.");
                    // Each of these chances of an additional sale is reduced by 10%.
                    int possible_result = Helper.random.nextInt(100);
                    if (possible_result < 10){ // a 10% chance of selling a single GigBag
                        itemChosen = new SellAccessory(itemChosen, s, "GigBag", customerName, this);
                    }
                    possible_result = Helper.random.nextInt(100); // re-generate another result
                    if (possible_result < 15){ // a 15% chance of selling a single PracticeAmp
                        itemChosen = new SellAccessory(itemChosen, s, "PracticeAmp", customerName, this);
                    }
                    possible_result = Helper.random.nextInt(100); // re-generate another result
                    if (possible_result < 20){ // a 20% chance of selling 1 or 2 Cables
                        itemChosen = new SellAccessory(itemChosen, s, "Cable", customerName, this);
                    }
                    possible_result = Helper.random.nextInt(100); // re-generate another result
                    if (possible_result < 30){ // a 30% chance of selling 1 to 3 Strings.
                        itemChosen = new SellAccessory(itemChosen, s, "Strings", customerName, this);
                    }
                }
            }
        }
        return itemChosen;
    }

    public void BuyItemTransactionCustom(Store s, String itemType, String customerName) {

        HashMap<String, ArrayList<Item>> inventory = s.getInventory();
        HashMap<String, ArrayList<Item>> soldLogBook = s.getSoldLogBook();

        ArrayList<Item> itemsOfRequiredType = inventory.get(itemType);
        int sizeOfRequiredItems = itemsOfRequiredType.size();

        if (sizeOfRequiredItems == 0) {
            System.out.println("Customer " + customerName + " didn't buy the " + itemType + " as there was no stock.");
        }
        else {

            // Get a random item, and it's list price from the store
            Item itemChosen = itemsOfRequiredType.get(Helper.random.nextInt(sizeOfRequiredItems));
            double listPrice = itemChosen.getListPrice();


            // New way for generating initialSellDecision for Project 4:

            boolean initialBuyDecision = false; // Initialize the sell decision here

            System.out.println("Do you want to buy this " + itemChosen.getName() + " at the list price: $" + listPrice + "?");
            System.out.println("1. Yes!");
            System.out.println("2. No!");

            String command = "";
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Please Enter The Corresponding Number Of Your Choice:");
            command = myObj.nextLine();  // Read user input

            while(! command.equals("1") && ! command.equals("2")){
                // Asking for the next input if they haven't input 1 or 2
                System.out.println("Invalid Input! Please Re-Enter It:");
                command = myObj.nextLine();
            }

            if(command.equals("1")){
                initialBuyDecision = true;
            }
            else{
                initialBuyDecision = false;
            }

            //Back to the usual code used in Project 3

            if (!initialBuyDecision) {
                System.out.println("Customer " + customerName + " didn't want to buy the " + itemType + " at the list price.");
                System.out.println("Clerk " + this.getName() + " offered a 10% discount");

                // New way for generating initialSellDecision for Project 4:

                boolean discountBuyDecision = false; // Initialize the sell decision here

                System.out.println("Do you want to buy this " + itemChosen.getName() + " at the discounted price: $" + listPrice * 0.9 + "?");
                System.out.println("1. Yes!");
                System.out.println("2. No!");

                System.out.println("Please Enter The Corresponding Number Of Your Choice:");
                command = myObj.nextLine();  // Read user input

                while(! command.equals("1") && ! command.equals("2")){
                    // Asking for the next input if they haven't input 1 or 2
                    System.out.println("Invalid Input! Please Re-Enter It:");
                    command = myObj.nextLine();
                }

                if(command.equals("1")){
                    discountBuyDecision = true;
                }
                else{
                    discountBuyDecision = false;
                }

                // Back to normal code used in Project 3
                if (!discountBuyDecision) {
                    System.out.println("Customer " + customerName + " didn't buy a " + itemType + " even at discounted price!");
                }
                else {
                    itemChosen.setDaySold(Store.getDay());
                    itemChosen.setSalePrice(.9 * listPrice);
                    System.out.println("Customer " + customerName + " bought a " +
                            Constants.NEW_OR_USED_MAPPING.get(itemChosen.getIsNew()) + " " +
                            itemChosen.getName() + " " + itemType + " in " +
                            Constants.CONDITION_MAPPING.get(itemChosen.getCondition()) +
                            " condition at 10% discount for $" + Helper.round(itemChosen.getSalePrice()));

                    // Add that item to the soldLogBook
                    s.removeFromRegistry(inventory, itemType, itemChosen);
                    s.addToRegistry(soldLogBook, itemType, itemChosen);
                    numberOfItemsSold = numberOfItemsSold + 1;
                    s.setRegisterAmount(s.getRegisterAmount() + itemChosen.getSalePrice()); // Sets the register amount to the sum of the prices of all the items in the transaction
                }
            }
            else {
                itemChosen.setDaySold(Store.getDay());
                itemChosen.setSalePrice(listPrice);
                System.out.println("Customer " + customerName + " bought a " +
                        Constants.NEW_OR_USED_MAPPING.get(itemChosen.getIsNew()) + " " +
                        itemChosen.getName() + " " + itemType + " in " +
                        Constants.CONDITION_MAPPING.get(itemChosen.getCondition()) +
                        " condition at list price for $" + Helper.round(itemChosen.getSalePrice()));

                // Add that item to the soldLogBook, no extra add-on in this case
                s.removeFromRegistry(inventory, itemType, itemChosen);
                s.addToRegistry(soldLogBook, itemType, itemChosen);
                numberOfItemsSold = numberOfItemsSold + 1;
                s.setRegisterAmount(s.getRegisterAmount() + itemChosen.getSalePrice()); // Sets the register amount to the sum of the prices of all the items in the transaction

            }

        }
    }


    public boolean[] GenerateBuyDecision(Item itemChosen){
        boolean initialBuyDecision = (Helper.random.nextInt(2) == 0); // 50% of chance of buying an item by default
        boolean discountBuyDecision = (Helper.random.nextInt(4) < 3); // 50% + 25% = 75%, 3/4 of chance of buying the item with discount by default
        if (itemChosen instanceof Player){
            if (((Player) itemChosen).getIsEqualized()){ // If a player has been equalized, increase the chance by 10%
                initialBuyDecision = (Helper.random.nextInt(5) < 3); // 60%, or 3/5
                discountBuyDecision = (Helper.random.nextInt(20) < 17); // 60% + 25% = 85%, or 17/20
            }
        }
        else if (itemChosen instanceof Stringed){
            if (((Stringed) itemChosen).getIsTuned()){ // If a Stringed instrument has been tuned, increase the chance by 15%
                initialBuyDecision = (Helper.random.nextInt(20) < 13); // 65%, or 13/20
                discountBuyDecision = (Helper.random.nextInt(10) < 9); // 65% + 25% = 90%, or 9/10
            }
        }
        else if (itemChosen instanceof Wind){
            if (((Wind) itemChosen).getIsAdjusted()){ // If a Wind instrument has been adjusted, increase the chance by 20%
                initialBuyDecision = (Helper.random.nextInt(10) < 7); // 70%, or 7/10
                discountBuyDecision = (Helper.random.nextInt(20) < 19); // 70% + 25% = 95%, or 19/20
            }
        }
        boolean[] decisionList = new boolean[2];
        decisionList[0] = initialBuyDecision;
        decisionList[1] = discountBuyDecision;
        return decisionList;
    }

    public boolean HaveClothingStock(Store s, ArrayList<String> clothingItems) {

        boolean clothingStock = false;

        HashMap<String, ArrayList<Item>> storeInv = s.getInventory();

        for (String itemType: clothingItems) {
            if (storeInv.get(itemType).size() > 0) {
                clothingStock = true;
                break;
            }
        }
        return clothingStock;
    }

    public void SellItemTransaction(Store s, String itemType, String customerName) {

        HashMap<String, ArrayList<Item>> inventory = s.getInventory();
        Item customerBroughtItem = Helper.createItem(itemType);
        customerBroughtItem.setDayArrived(Store.getDay());

        // If the customer is selling a clothing item type
        if (Constants.CLOTHING_ITEM_TYPES.contains(itemType)) {
            // If we no longer hold stock of any clothing item
            if (HaveClothingStock(s, Constants.CLOTHING_ITEM_TYPES) == false) {
                System.out.println("Customer " + customerName + " was turned away because we are no longer accepting clothing items.");
                return;
            }
        }

        int condition = customerBroughtItem.getCondition();
        boolean isNew = customerBroughtItem.getIsNew();
        double offeredPrice = Helper.priceEstimator(isNew, condition);

        /*
         New condition for Project 3:
         if the item is from customer
         randomly set condition for equalized/tuned/adjusted as true/false
        */
        if (customerBroughtItem instanceof Player){
            ((Player) customerBroughtItem).setIsEqualized(Helper.random.nextBoolean());
        }
        else if (customerBroughtItem instanceof Stringed){
            ((Stringed) customerBroughtItem).setIsTuned(Helper.random.nextBoolean());
        }
        else if (customerBroughtItem instanceof Wind){
            ((Wind) customerBroughtItem).setIsAdjusted(Helper.random.nextBoolean());
        }

        double regAmount = s.getRegisterAmount();

        if (offeredPrice <= regAmount) {

            boolean initialSellDecision = (Helper.random.nextInt(2) == 0);

            if (!initialSellDecision && (offeredPrice * 1.1) <= regAmount) {
                System.out.println("Customer " + customerName + " didn't want to sell the " + itemType + " at the offered price of $" + Helper.round(offeredPrice));
                System.out.println("Clerk " + this.getName() + " offered 10% extra.");
                boolean extraSellDecision = (Helper.random.nextInt(4) != 3);
                if (!extraSellDecision) {
                    System.out.println("Customer " + customerName + " left the store without selling a " + itemType + " even at extra price!");
                }
                else {
                    customerBroughtItem.setDayArrived(Store.getDay());
                    customerBroughtItem.setPurchasePrice(offeredPrice * 1.1);
                    customerBroughtItem.setListPrice(offeredPrice * 1.1 * 2);
                    System.out.println("Customer " + customerName + " sold a " +
                            Constants.NEW_OR_USED_MAPPING.get(customerBroughtItem.getIsNew()) + " " +
                            customerBroughtItem.getName() + " " + itemType + " in " +
                            Constants.CONDITION_MAPPING.get(customerBroughtItem.getCondition()) +
                            " condition at 10% extra for $" + Helper.round(customerBroughtItem.getPurchasePrice()));

                    s.setRegisterAmount(s.getRegisterAmount() - customerBroughtItem.getPurchasePrice());
                    s.addToRegistry(inventory, itemType, customerBroughtItem);
                    numberItemsBought = numberItemsBought + 1;
                }
            }
            else {
                customerBroughtItem.setDayArrived(Store.getDay());
                customerBroughtItem.setPurchasePrice(offeredPrice);
                customerBroughtItem.setListPrice(offeredPrice * 2);
                System.out.println("Customer " + customerName + " sold a " +
                        Constants.NEW_OR_USED_MAPPING.get(customerBroughtItem.getIsNew()) + " " +
                        customerBroughtItem.getName() + " " + itemType + " in " +
                        Constants.CONDITION_MAPPING.get(customerBroughtItem.getCondition()) +
                        " condition at offered price for $" + Helper.round(customerBroughtItem.getPurchasePrice()));
                        
                s.setRegisterAmount(s.getRegisterAmount() - customerBroughtItem.getPurchasePrice());
                s.addToRegistry(inventory, itemType, customerBroughtItem);
                numberItemsBought = numberItemsBought + 1;
            }
        }
        else {
            System.out.println("Not enough money in the register to pay for the item.");
        }
    }

    public void SellItemTransactionCustom(Store s, String itemType, String customerName) {

        HashMap<String, ArrayList<Item>> inventory = s.getInventory();
        Item customerBroughtItem = Helper.createItem(itemType);
        customerBroughtItem.setDayArrived(Store.getDay());
        // If the customer is selling a clothing item type
        if (Constants.CLOTHING_ITEM_TYPES.contains(itemType)) {
            // If we no longer hold stock of any clothing item
            if (HaveClothingStock(s, Constants.CLOTHING_ITEM_TYPES) == false) {
                System.out.println("Customer " + customerName + " was turned away because we are no longer accepting clothing items.");
                return;
            }
        }

        int condition = customerBroughtItem.getCondition();
        boolean isNew = customerBroughtItem.getIsNew();
        double offeredPrice = Helper.priceEstimator(isNew, condition);

        /*
         New condition for Project 3:
         if the item is from customer
         randomly set condition for equalized/tuned/adjusted as true/false
        */
        if (customerBroughtItem instanceof Player){
            ((Player) customerBroughtItem).setIsEqualized(Helper.random.nextBoolean());
        }
        else if (customerBroughtItem instanceof Stringed){
            ((Stringed) customerBroughtItem).setIsTuned(Helper.random.nextBoolean());
        }
        else if (customerBroughtItem instanceof Wind){
            ((Wind) customerBroughtItem).setIsAdjusted(Helper.random.nextBoolean());
        }

        double regAmount = s.getRegisterAmount();

        if (offeredPrice <= regAmount) {

            // New way for generating initialSellDecision for Project 4:

            boolean initialSellDecision = false; // Initialize the sell decision here

            System.out.println("Do you want to sell this " + customerBroughtItem.getName() + " at the offered price: $" + offeredPrice + "?");
            System.out.println("1. Yes!");
            System.out.println("2. No!");

            String command = "";
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Please Enter The Corresponding Number Of Your Choice:");
            command = myObj.nextLine();  // Read user input

            while(! command.equals("1") && ! command.equals("2")){
                // Asking for the next input if they haven't input 1 or 2
                System.out.println("Invalid Input! Please Re-Enter It:");
                command = myObj.nextLine();
            }

            if(command.equals("1")){
                initialSellDecision = true;
            }
            else{
                initialSellDecision = false;
            }


            // Continue using code from Project 3:

            if (!initialSellDecision && (offeredPrice * 1.1) <= regAmount) {
                System.out.println("Customer " + customerName + " didn't want to sell the " + itemType + " at the offered price of $" + Helper.round(offeredPrice));
                System.out.println("Clerk " + this.getName() + " offered 10% extra.");


                // New way for generating extraSellDecision for Project 4:

                boolean extraSellDecision = false; // Intialize the extra sell decision here

                System.out.println("Do you want to sell this " + customerBroughtItem.getName() + " at the newest offered price: $" + offeredPrice * 1.1 + "?");
                System.out.println("1. Yes!");
                System.out.println("2. No!");

                System.out.println("Please Enter The Corresponding Number Of Your Choice:");
                command = myObj.nextLine();  // Read user input

                while(! command.equals("1") && ! command.equals("2")){
                    // Asking for the next input if they haven't input 1 or 2
                    System.out.println("Invalid Input! Please Re-Enter It:");
                    command = myObj.nextLine();
                }

                if(command.equals("1")){
                    extraSellDecision = true;
                }
                else{
                    extraSellDecision = false;
                }


                // Continue using code from Project 3:
                if (!extraSellDecision) {
                    System.out.println("Customer " + customerName + " didn't sell a " + itemType + " even at extra price!");
                }
                else {
                    customerBroughtItem.setDayArrived(Store.getDay());
                    customerBroughtItem.setPurchasePrice(offeredPrice * 1.1);
                    customerBroughtItem.setListPrice(offeredPrice * 1.1 * 2);
                    System.out.println("Customer " + customerName + " sold a " +
                            Constants.NEW_OR_USED_MAPPING.get(customerBroughtItem.getIsNew()) + " " +
                            customerBroughtItem.getName() + " " + itemType + " in " +
                            Constants.CONDITION_MAPPING.get(customerBroughtItem.getCondition()) +
                            " condition at 10% extra for $" + Helper.round(customerBroughtItem.getPurchasePrice()));

                    s.setRegisterAmount(s.getRegisterAmount() - customerBroughtItem.getPurchasePrice());
                    s.addToRegistry(inventory, itemType, customerBroughtItem);
                    numberItemsBought = numberItemsBought + 1;
                }
            }
            else {
                customerBroughtItem.setDayArrived(Store.getDay());
                customerBroughtItem.setPurchasePrice(offeredPrice);
                customerBroughtItem.setListPrice(offeredPrice * 2);
                System.out.println("Customer " + customerName + " sold a " +
                        Constants.NEW_OR_USED_MAPPING.get(customerBroughtItem.getIsNew()) + " " +
                        customerBroughtItem.getName() + " " + itemType + " in " +
                        Constants.CONDITION_MAPPING.get(customerBroughtItem.getCondition()) +
                        " condition at offered price for $" + Helper.round(customerBroughtItem.getPurchasePrice()));

                s.setRegisterAmount(s.getRegisterAmount() - customerBroughtItem.getPurchasePrice());
                s.addToRegistry(inventory, itemType, customerBroughtItem);
                numberItemsBought = numberItemsBought + 1;
            }
        }
        else {
            System.out.println("Not enough money in the register to pay for the item.");
        }
    }

    public void CleanStore(Store s) {

        System.out.println("--------------------CLEANING STORE ITEMS--------------------");

        HashMap<String, ArrayList<Item>> inventory = s.getInventory();
        HashMap<String, ArrayList<Item>> damagedItems = new HashMap<>();
        int damaged_count = 0;

        // Announcement of Cleaning beginning
        System.out.println(this.getName() + " started cleaning the store.");

        for(String itemType: inventory.keySet()) {
            for (Item item : inventory.get(itemType)) {
                if ((Helper.random.nextInt(100) < this.getItemDamageChance())) {
                    damaged_count = damaged_count + 1;
                    if (damagedItems.containsKey(itemType)) {
                        damagedItems.get(itemType).add(item);
                    } else {
                        damagedItems.put(itemType, new ArrayList<Item>(Arrays.asList(item)));
                    }
                }
            }
        }

        // From the list of damaged items
        for(String itemType: damagedItems.keySet()) {
            for (Item item : damagedItems.get(itemType)) {
                degradeItem(s, itemType, item, "cleaning");
            }
        }
        setMessage(damaged_count + " items were damaged during cleaning at " + s.getStoreName() + ".");
    }

    public void degradeItem(Store s, String itemType, Item item, String activity) {

        HashMap<String, ArrayList<Item>> inventory = s.getInventory();

        System.out.println(this.getName() + " damaged an item during " + activity + ".");

        if (item.getCondition() == 1) {
            System.out.println("The " + item.getName() + " " + itemType + " broke and was removed from inventory.");
            s.removeFromRegistry(inventory, itemType, item);
        }

        else {
            // Take the item from inventory
            s.removeFromRegistry(inventory, itemType, item);

            // Calculate the changes
            int originalCondition = item.getCondition();
            int newCondition = originalCondition - 1;

            double originalListPrice = item.getListPrice();
            double newListPrice = originalListPrice * 0.8;

            // Announce what changed
            System.out.println("The " + item.getName() + " " + itemType +
                    " degraded from " + Constants.CONDITION_MAPPING.get(originalCondition) +
                    " ($" + Helper.round(item.getListPrice()) + ")" +
                    " to " + Constants.CONDITION_MAPPING.get(newCondition) +
                    " ($" + Helper.round(newListPrice) + ")");

            // Set the changes
            item.setCondition(newCondition);
            item.setListPrice(newListPrice);

            // Add item back to inventory with the changes
            s.addToRegistry(inventory, itemType, item);
        }
    }

    public void LeaveTheStore(Store s) {

        System.out.println("--------------------CLOSING THE STORE FOR THE DAY--------------------");

        System.out.println(this.getName() + " left " + s.getStoreName() + " for the day.");
        setMessage(this.getName() + " left " + s.getStoreName() + " for the day.");
        System.out.println();
        this.setIsActiveWorker(false);
    }

    // Clerk - Setters and Getters

    public int getItemDamageChance() {
        return damageChance;
    }

    public int getNumberOfItemsSold() {
        return numberOfItemsSold;
    }

    public void setNumberOfItemsSold(int numberOfItemsSold) {
        this.numberOfItemsSold = numberOfItemsSold;
    }

    public int getNumberOfItemsBought() {
        return numberItemsBought;
    }

    public void setNumberOfItemsBought(int numberOfItemsBought) {
        this.numberItemsBought = numberOfItemsBought;
    }

    public void setStore(Store s) {
        currentStore = s;
    }

    public Store getStore() {
        return currentStore;
    }

    // Observer Methods

    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer o: observers) {
            o.update(this);
        }
    }

    // Observer - Setters and Getters

    public void setMessage(String msg) {
        message = msg;
        notifyObservers();
    }

    public String getMessage() {
        return message;
    }
}
