import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Clerk extends Staff {

    public Clerk(String name, int daysWorkedInARow) {
        super(name, daysWorkedInARow);
    }

    public void ArriveAtStore(int day) {
        System.out.println(this.name + " arrived at the store on day " + day + ".");
    }

    public void GoToBank(Store s, double currentAmount) {

        double alreadyWithdrawn = s.getAmountWithdrawnFromBank();
        s.setAmountWithdrawnFromBank(alreadyWithdrawn + 1000);
        s.setRegisterAmount(currentAmount + 1000);

        System.out.println(this.getName() + " withdrew 1000$ from the bank, making new register amount as: " + s.getRegisterAmount());
    }

    public void CheckRegister(Store s) {

        double registerMoney = s.getRegisterAmount();
        System.out.println(this.getName() + " counted " + registerMoney + " in the register.");
        if (registerMoney < 75.00) {
            this.GoToBank(s, registerMoney);
        }
    }

    public ArrayList<String> DoInventory(Store s) {

        double inventoryValue = s.calcInventoryValue();
        System.out.println("The inventory value is: " + inventoryValue);

        ArrayList<String> zeroStockItems = s.itemsWithZeroStock();

        return zeroStockItems;
    }

    public void PlaceAnOrder(Store s, ArrayList<String> zeroStockItems) {
        s.generateInventory(3, zeroStockItems, false);
    }

    public void OpenTheStore(Store s) {

        int buyingCustomersCount = Helper.random.nextInt(7) + 4;
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
        if (storeInv.get(itemType).size() == 0) {
            System.out.println("Customer " + customerName + " left the store without buying the " + itemType + " as there was no stock.");
        }
        else {
            int found = Helper.random.nextInt(storeInv.get(itemType).size());
            Item itemChosen = storeInv.get(itemType).get(found);
            double listPrice = itemChosen.getListPrice();
            boolean initialBuyDecision = (Helper.random.nextInt(2) == 0);
            if (!initialBuyDecision) {
                System.out.println("Customer " + customerName + " didn't want to buy the " + itemType + " at the list price.");
                System.out.println("Clerk " + this.getName() + " offered a 10% discount");
                boolean discountBuyDecision = (Helper.random.nextInt(4) != 3);
                if (!discountBuyDecision) {
                    System.out.println("Customer " + customerName + " left the store without buying a " + itemType + " even at discounted price!");
                }
                else {
                    itemChosen.setDaySold(s.day);
                    itemChosen.setSalePrice(.9 * listPrice);
                    System.out.println("Customer " + customerName + " bought a " +
                            Constants.NEW_OR_USED_MAPPING.get(itemChosen.getIsNew()) + " " +
                            itemChosen.getName() + " " + itemType + " in " +
                            Constants.CONDITION_MAPPING.get(itemChosen.getCondition()) +
                            " condition at 10% discount for " + itemChosen.getSalePrice());

                    s.removeFromInventory(itemType, found);
                    s.setRegisterAmount(s.getRegisterAmount() + itemChosen.getSalePrice());
                    s.addToSoldInventory(itemType, itemChosen);
                }
            }
            else {
                itemChosen.setDaySold(s.day);
                itemChosen.setSalePrice(listPrice);
                System.out.println("Customer " + customerName + " bought a " +
                        Constants.NEW_OR_USED_MAPPING.get(itemChosen.getIsNew()) + " " +
                        itemChosen.getName() + " " + itemType + " in " +
                        Constants.CONDITION_MAPPING.get(itemChosen.getCondition()) +
                        " condition at list price for " + itemChosen.getSalePrice());

                s.removeFromInventory(itemType, found);
                s.setRegisterAmount(s.getRegisterAmount() + itemChosen.getSalePrice());
                s.addToSoldInventory(itemType, itemChosen);
            }
        }
    }

    public void SellItemTransaction(Store s, String itemType, String customerName) {

        HashMap<String, ArrayList<Item>> storeInv = s.getInventory();
        Item customerBroughtItem = s.createItem(itemType);
        int condition = customerBroughtItem.getCondition();
        boolean isNew = customerBroughtItem.getIsNew();
        double offeredPrice = Helper.priceEstimator(isNew, condition);

        double regAmount = s.getRegisterAmount();

        if (offeredPrice <= regAmount) {

            boolean initialSellDecision = (Helper.random.nextInt(2) == 0);

            if (!initialSellDecision && (offeredPrice * 1.1) <= regAmount) {
                System.out.println("Customer " + customerName + " didn't want to sell the " + itemType + " at the offered price of " + offeredPrice);
                System.out.println("Clerk " + this.getName() + " offered 10% extra.");
                boolean extraSellDecision = (Helper.random.nextInt(4) != 3);
                if (!extraSellDecision) {
                    System.out.println("Customer " + customerName + " left the store without selling a " + itemType + " even at extra price!");
                }
                else {
                    customerBroughtItem.setDayArrived(s.day);
                    customerBroughtItem.setPurchasePrice(offeredPrice * 1.1);
                    customerBroughtItem.setListPrice(offeredPrice * 1.1 * 2);
                    System.out.println("Customer " + customerName + " sold a " +
                            Constants.NEW_OR_USED_MAPPING.get(customerBroughtItem.getIsNew()) + " " +
                            customerBroughtItem.getName() + " " + itemType + " in " +
                            Constants.CONDITION_MAPPING.get(customerBroughtItem.getCondition()) +
                            " condition at 10% extra for " + customerBroughtItem.getPurchasePrice());

                    s.setRegisterAmount(s.getRegisterAmount() - customerBroughtItem.getPurchasePrice());
                    s.addToInventory(itemType, customerBroughtItem);
                }
            }
            else {
                customerBroughtItem.setDayArrived(s.day);
                customerBroughtItem.setPurchasePrice(offeredPrice);
                customerBroughtItem.setListPrice(offeredPrice * 2);
                System.out.println("Customer " + customerName + " sold a " +
                        Constants.NEW_OR_USED_MAPPING.get(customerBroughtItem.getIsNew()) + " " +
                        customerBroughtItem.getName() + " " + itemType + " in " +
                        Constants.CONDITION_MAPPING.get(customerBroughtItem.getCondition()) +
                        " condition at offered price for " + customerBroughtItem.getPurchasePrice());
            }
        }
        else {
            System.out.println("Not enough money in the register to pay for the item.");
        }
    }

    public int getItemDamageChance() {
        if (this.getName().equals("Velma")) {
            return 94;
        }
        else {
            return 79;
        }
    }

    public void CleanStore(Store s) {

        HashMap<String, ArrayList<Item>> storeInv = s.getInventory();
        HashMap<String, ArrayList<Item>> damagedItems = new HashMap<>();

        for(String itemType: storeInv.keySet()) {
            for (Item item : storeInv.get(itemType)) {
                if (!(Helper.random.nextInt(100) > getItemDamageChance())) {

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
                            " to " + Constants.CONDITION_MAPPING.get(item.getCondition() - 1));
                    System.out.println("The " + item.getName() + " " + itemType +
                            " price degraded from " + item.getListPrice() +
                            " to " + item.getListPrice() * 0.8);
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
    }
}
