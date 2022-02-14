import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/*
Decorator Pattern that tracks an item and modifies its getSalPrice method,
which returns not only the item's sale price
but also the sale price of all the add-on items.
 */
public class SellAccessory extends SellDecorator {
    Item item;
    protected double extraSalePrice;

    public SellAccessory(Item item, Store s, String itemType, String customerName) {
        this.item = item;

        // find a item from the current inventory list
        HashMap<String, ArrayList<Item>> storeInv = s.getInventory();
        List<Item> filteredList = storeInv.get(itemType).stream().filter(x -> x.getDayArrived() <= s.getDay()).toList();
        if (filteredList.size() == 0) { // No stock for GigBag item
            extraSalePrice = 0;
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
            extraSalePrice = itemChosen.getSalePrice();
        }
    }
    protected double getSalePrice() {
        /*
        If we call this decorator class two times then it will RECURSIVELY re-define this method
        Hence like getSalePrice (newest) = getSalePrice (last one) + extraSalePrice
                                         = [getSalePrice(original one) + extraSalePrice (last one) ] + extraSalePrice (this one)
         */
        return item.getSalePrice() + extraSalePrice;



    }
}