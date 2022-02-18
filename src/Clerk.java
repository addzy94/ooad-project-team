import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
--- INHERITANCE ---
    Clerk worker type inherits from Staff and gets attributes like name and days worked which
    is an example of inheritance.
--- INHERITANCE ---
*/

public class Clerk extends Staff {

    private int damageChance;

    public Clerk(String name, int daysWorkedInARow, int damage_chance) {
        super(name, daysWorkedInARow);
        damageChance = damage_chance;
    }

    public void ArriveAtStore(Store s) {
        System.out.println("Clerk " + this.getName() + " decided to work on day " + s.getDay() + ".");
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

        System.out.println("Clerk " + this.getName() + " withdrew $1000 from the bank, making new register amount as: " + Helper.round(s.getRegisterAmount()) + "$");
    }

    public void CheckRegister(Store s) {

        double registerMoney = Helper.round(s.getRegisterAmount());
        System.out.println("Clerk " + this.getName() + " counted $" + registerMoney + " in the register.");
        if (registerMoney < 75.00) {
            this.GoToBank(s, registerMoney);
        }
    }

    public ArrayList<String> DoInventory(Store s) {

        double inventoryValue = Helper.round(s.calcInventoryValue());
        System.out.println("Clerk " + this.getName() + " calculated the inventory value to be: $" + inventoryValue);

        ArrayList<String> zeroStockItems = s.itemsWithZeroStock();

        return zeroStockItems;
    }

    public void PlaceAnOrder(Store s, ArrayList<String> zeroStockItems) {
        s.generateInventory(3, zeroStockItems, false);
    }

    public void OpenTheStore(Store s) {

        int poissonResult = -1;
        int mean = 3;
        double sum = 0.0;
        while (sum < 1.0) { //https://hpaulkeeler.com/simulating-poisson-random-variables-direct-method/
            sum = sum + ((-1.0/mean)*Math.log(Helper.random.nextDouble()));
            poissonResult++;
        }

        int buyingCustomersCount = poissonResult + 2;
        int sellingCustomerCount = Helper.random.nextInt(4) + 1;

        ArrayList<String> buyingCustomerRequirements = Helper.customerRequirements(Constants.CLASS_NAMES, buyingCustomersCount);
        ArrayList<String> sellingCustomerRequirements = Helper.customerRequirements(Constants.CLASS_NAMES, sellingCustomerCount);

        for (int i = 0; i < buyingCustomersCount; i++) {
            String customerName = Constants.CUSTOMER_NAMES.get(Helper.random.nextInt(Constants.CUSTOMER_NAMES.size()));
            String customerRequiredItem = buyingCustomerRequirements.get(i);
            BuyItemTransaction(s, customerRequiredItem, customerName);
        }

        for (int i = 0; i < sellingCustomerCount; i++) {
            String customerName = Constants.CUSTOMER_NAMES.get(Helper.random.nextInt(Constants.CUSTOMER_NAMES.size()));
            String customerBroughtItem = sellingCustomerRequirements.get(i);
            SellItemTransaction(s, customerBroughtItem, customerName);
        }
    }

    public void BuyItemTransaction(Store s, String itemType, String customerName) {

        HashMap<String, ArrayList<Item>> storeInv = s.getInventory();
        List<Item> filteredList = storeInv.get(itemType).stream().filter(x -> x.getDayArrived() <= s.getDay()).toList();
        if (filteredList.size() == 0) {
            System.out.println("Customer " + customerName + " left the store without buying the " + itemType + " as there was no stock.");
        }
        else {
            // Get items which are at the store.
            Item itemChosen = filteredList.get(Helper.random.nextInt(filteredList.size()));
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
                    itemChosen.setDaySold(s.getDay());
                    itemChosen.setSalePrice(.9 * listPrice);
                    System.out.println("Customer " + customerName + " bought a " +
                            Constants.NEW_OR_USED_MAPPING.get(itemChosen.getIsNew()) + " " +
                            itemChosen.getName() + " " + itemType + " in " +
                            Constants.CONDITION_MAPPING.get(itemChosen.getCondition()) +
                            " condition at 10% discount for $" + Helper.round(itemChosen.getSalePrice()));

                    /*
                    Call OptionalSell method, which contains applying decorator pattern to itemChosen for modifying its sale price
                    if the customer is going to buy more than one item at once
                     */
                    itemChosen = OptionalSell(s, itemChosen, customerName); // Run optional sell method for adding the item's sale price under special scenario.
                    s.removeFromInventory(itemType, itemChosen);
                    s.setRegisterAmount(s.getRegisterAmount() + itemChosen.getSalePrice());
                    s.addToSoldInventory(itemType, itemChosen);
                }
            }
            else {
                itemChosen.setDaySold(s.getDay());
                itemChosen.setSalePrice(listPrice);
                System.out.println("Customer " + customerName + " bought a " +
                        Constants.NEW_OR_USED_MAPPING.get(itemChosen.getIsNew()) + " " +
                        itemChosen.getName() + " " + itemType + " in " +
                        Constants.CONDITION_MAPPING.get(itemChosen.getCondition()) +
                        " condition at list price for $" + Helper.round(itemChosen.getSalePrice()));

                /*
                Call OptionalSell method, which contains applying decorator pattern to itemChosen for modifying its sale price
                if the customer is going to buy more than one item at once
                */
                itemChosen = OptionalSell(s, itemChosen, customerName); // Run optional sell method for adding the item's sale price under special scenario.
                s.removeFromInventory(itemType, itemChosen);
                s.setRegisterAmount(s.getRegisterAmount() + itemChosen.getSalePrice());
                s.addToSoldInventory(itemType, itemChosen);
            }

        }
    }

    public Item OptionalSell(Store s, Item itemChosen, String customerName){
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
                        itemChosen = new SellAccessory(itemChosen, s, "GigBag", customerName);
                    }
                    possible_result = Helper.random.nextInt(100); // re-generate another result
                    if (possible_result < 25){ // a 25% chance of selling a single PracticeAmp
                        itemChosen = new SellAccessory(itemChosen, s, "PracticeAmp", customerName);
                    }
                    possible_result = Helper.random.nextInt(100); // re-generate another result
                    if (possible_result < 30){ // a 30% chance of selling 1 or 2 Cables
                        int numberOfTimes = Helper.random.nextInt(2); // generate a value that is either 0 or 1
                        for (int i = numberOfTimes; i < 2; i++){ // Run the loop for 1 or 2 times
                            itemChosen = new SellAccessory(itemChosen, s, "Cable", customerName);
                            //System.out.println("Now price is: "+ itemChosen.getSalePrice());
                        }
                    }
                    possible_result = Helper.random.nextInt(100); // re-generate another result
                    if (possible_result < 40){ // a 40% chance of selling 1 to 3 Strings.
                        int numberOfTimes = Helper.random.nextInt(3); // generate a value that is either 0, 1, or 2
                        for (int i = numberOfTimes; i < 3; i++){ // Run the loop for 1 or 2 or 3 times
                            itemChosen = new SellAccessory(itemChosen, s, "Strings", customerName);
                            //System.out.println("Now price is: "+ itemChosen.getSalePrice());
                        }
                    }
                }

                else{ // If the Stringed instrument is not electric, there is:
                    System.out.println("The stringed instrument Customer " + customerName + " bought is not electric.");
                    // Each of these chances of an additional sale is reduced by 10%.
                    int possible_result = Helper.random.nextInt(100);
                    if (possible_result < 10){ // a 10% chance of selling a single GigBag
                        itemChosen = new SellAccessory(itemChosen, s, "GigBag", customerName);
                    }
                    possible_result = Helper.random.nextInt(100); // re-generate another result
                    if (possible_result < 15){ // a 15% chance of selling a single PracticeAmp
                        itemChosen = new SellAccessory(itemChosen, s, "PracticeAmp", customerName);
                    }
                    possible_result = Helper.random.nextInt(100); // re-generate another result
                    if (possible_result < 20){ // a 20% chance of selling 1 or 2 Cables
                        itemChosen = new SellAccessory(itemChosen, s, "Cable", customerName);
                    }
                    possible_result = Helper.random.nextInt(100); // re-generate another result
                    if (possible_result < 30){ // a 30% chance of selling 1 to 3 Strings.
                        itemChosen = new SellAccessory(itemChosen, s, "Strings", customerName);
                    }
                }

            }
        }
        return itemChosen;
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

    public void SellItemTransaction(Store s, String itemType, String customerName) {

        HashMap<String, ArrayList<Item>> storeInv = s.getInventory();
        Item customerBroughtItem = s.createItem(itemType);
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
                    customerBroughtItem.setDayArrived(s.getDay());
                    customerBroughtItem.setPurchasePrice(offeredPrice * 1.1);
                    customerBroughtItem.setListPrice(offeredPrice * 1.1 * 2);
                    System.out.println("Customer " + customerName + " sold a " +
                            Constants.NEW_OR_USED_MAPPING.get(customerBroughtItem.getIsNew()) + " " +
                            customerBroughtItem.getName() + " " + itemType + " in " +
                            Constants.CONDITION_MAPPING.get(customerBroughtItem.getCondition()) +
                            " condition at 10% extra for " + Helper.round(customerBroughtItem.getPurchasePrice()));

                    s.setRegisterAmount(s.getRegisterAmount() - customerBroughtItem.getPurchasePrice());
                    s.addToInventory(itemType, customerBroughtItem);
                }
            }
            else {
                customerBroughtItem.setDayArrived(s.getDay());
                customerBroughtItem.setPurchasePrice(offeredPrice);
                customerBroughtItem.setListPrice(offeredPrice * 2);
                System.out.println("Customer " + customerName + " sold a " +
                        Constants.NEW_OR_USED_MAPPING.get(customerBroughtItem.getIsNew()) + " " +
                        customerBroughtItem.getName() + " " + itemType + " in " +
                        Constants.CONDITION_MAPPING.get(customerBroughtItem.getCondition()) +
                        " condition at offered price for $" + Helper.round(customerBroughtItem.getPurchasePrice()));
            }
        }
        else {
            System.out.println("Not enough money in the register to pay for the item.");
        }
    }

    public int getItemDamageChance() {
        return damageChance;
    }

    public void CleanStore(Store s) {

        HashMap<String, ArrayList<Item>> storeInv = s.getInventory();
        HashMap<String, ArrayList<Item>> damagedItems = new HashMap<>();

        // Announcement
        System.out.println(this.getName() + " started cleaning the store.");

        for(String itemType: storeInv.keySet()) {
            for (Item item : storeInv.get(itemType)) {
                if ((Helper.random.nextInt(100) > getItemDamageChance())) {

                    if (damagedItems.containsKey(itemType)) {
                        damagedItems.get(itemType).add(item);
                    } else {
                        damagedItems.put(itemType, new ArrayList<Item>(Arrays.asList(item)));
                    }
                }
            }
        }

        for(String itemType: damagedItems.keySet()) {
            for (Item item : damagedItems.get(itemType)) {
                System.out.println(this.getName() + " damaged an item.");
                if (item.getCondition() == 1) {
                    System.out.println("The " + item.getName() + " " + itemType +
                            " broke and was removed from inventory.");
                    storeInv.get(itemType).remove(item);
                }
                else {
                    storeInv.get(itemType).remove(item);
                    System.out.println("The " + item.getName() + " " + itemType +
                            " degraded from " + Constants.CONDITION_MAPPING.get(item.getCondition()) +
                            " ($" + Helper.round(item.getListPrice()) + ")" +
                            " to " + Constants.CONDITION_MAPPING.get(item.getCondition() - 1) +
                            " ($" + Helper.round(item.getListPrice() * 0.8) + ")");
                    item.setCondition(item.getCondition() - 1);
                    item.setListPrice(item.getListPrice() * 0.8);

                    storeInv.get(itemType).add(item);
                }
            }
        }
        s.setInventory(storeInv);
    }

    public void LeaveTheStore(Store s) {
        System.out.println(this.getName() + " left the store for the day.");
        this.setIsActiveWorker(false);
    }

    
}
