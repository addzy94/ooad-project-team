import java.util.ArrayList;
import java.util.HashMap;

/*
Decorator Pattern that tracks an item and modifies its getSalPrice method,
which returns not only the item's sale price
but also the sale price of all the add-on items.
 */
public class SellAccessory extends SellDecorator {
    protected double extraSalePrice;

    public SellAccessory(Item item, Store s, String itemType, String customerName, Clerk c) {
        this.item = item;

        // Find an item from the current inventory list
        HashMap<String, ArrayList<Item>> inventory = s.getInventory();
        HashMap<String, ArrayList<Item>> soldLogBook = s.getSoldLogBook();

        // Get list and size of required items
        ArrayList<Item> itemsOfRequiredType = inventory.get(itemType);
        int sizeOfRequiredItems = itemsOfRequiredType.size();

        if (sizeOfRequiredItems == 0) { // Store has run out of stock of the required accessory
            extraSalePrice = 0;
            System.out.println("Customer " + customerName + " also wanted to buy a " + itemType + " but there was no stock.");
        }
        else {

            // Get a random accessory of type 'itemType' from the store.
            Item accessoryItemChosen = inventory.get(itemType).get(Helper.random.nextInt(sizeOfRequiredItems));
            double listPrice = accessoryItemChosen.getListPrice();
            accessoryItemChosen.setDaySold(Store.getDay());

            // Sell it at list price directly.
            accessoryItemChosen.setSalePrice(listPrice);
            System.out.println("Customer " + customerName + " also bought a " +
                    Constants.NEW_OR_USED_MAPPING.get(accessoryItemChosen.getIsNew()) + " " +
                    accessoryItemChosen.getName() + " " + itemType + " in " +
                    Constants.CONDITION_MAPPING.get(accessoryItemChosen.getCondition()) +
                    " condition at list price for $" + Helper.round(accessoryItemChosen.getSalePrice()));

            s.removeFromRegistry(inventory, itemType, accessoryItemChosen);
            s.addToRegistry(soldLogBook, itemType, accessoryItemChosen);
            extraSalePrice = accessoryItemChosen.getSalePrice();

            c.setNumberOfItemsSold(c.getNumberOfItemsSold() + 1);
        }
    }

    @Override
    String getName() { // Do need to override all the methods even if you are not modifying anything in it
        return item.getName(); //this.name = this.item.getName(), otherwise this.name = null, and will return null to the original item
    }

    @Override
    int getDaySold() {
        return item.getDaySold();
    }

    @Override
    double getSalePrice() {
        /*
        If we call this decorator class two times then it will re-define this method (which acts as Reference Chain)
        Hence like getSalePrice (newest) = getSalePrice (last one) + extraSalePrice
                                         = [getSalePrice(original one) + extraSalePrice (last one) ] + extraSalePrice (this one)
         */
        return item.getSalePrice() + extraSalePrice;
    }

    @Override
    double getPurchasePrice() {
        return item.getPurchasePrice();
    }

    @Override
    double getListPrice() {
        return item.getListPrice();
    }

    @Override
    boolean getIsNew() {
        return item.getIsNew();
    }

    @Override
    int getDayArrived() {
        return item.getDayArrived();
    }

    @Override
    int getCondition() {
        return item.getCondition();
    }
}