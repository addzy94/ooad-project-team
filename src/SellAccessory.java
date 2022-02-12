import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SellAccessory extends SellDecorator {
    Item item;
    protected double ExtraSalePrice;

    public SellAccessory(Item item, Store s, String itemType, String customerName) {
        this.item = item;

        // find a GigBag item from the current inventory list
        HashMap<String, ArrayList<Item>> storeInv = s.getInventory();
        List<Item> filteredList = storeInv.get(itemType).stream().filter(x -> x.getDayArrived() <= s.getDay()).toList();
        if (filteredList.size() == 0) { // No stock for GigBag item
            ExtraSalePrice = 0;
            System.out.println("Customer " + customerName + " also wanted to buy a " + itemType + " but there was no stock.");
        }
        else {
            // Get a random GigBag which is at the store.
            Item itemChosen = filteredList.get(Helper.random.nextInt(filteredList.size()));
            double listPrice = itemChosen.getListPrice();
            itemChosen.setDaySold(s.getDay());
            // Sell it at list price directly.
            itemChosen.setSalePrice(listPrice);
            System.out.println("Customer " + customerName + " also bought a " +
                    Constants.NEW_OR_USED_MAPPING.get(itemChosen.getIsNew()) + " " +
                    itemChosen.getName() + " " + itemType + " in " +
                    Constants.CONDITION_MAPPING.get(itemChosen.getCondition()) +
                    " condition at list price for $" + Helper.round(itemChosen.getSalePrice()));

            s.removeFromInventory(itemType, itemChosen);
            s.addToSoldInventory(itemType, itemChosen);
            ExtraSalePrice = itemChosen.getSalePrice();
        }
    }
    protected double getSalePrice() {
        return item.getSalePrice() + ExtraSalePrice;
    }
}