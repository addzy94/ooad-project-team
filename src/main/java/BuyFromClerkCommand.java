public class BuyFromClerkCommand implements Command{
    Clerk clerk;
    Store store;
    String customerName;
    public BuyFromClerkCommand(Clerk clerk, Store store, String customerName){
        this.clerk = clerk;
        this.store = store;
        this.customerName = customerName;
    }
    public void execute(){
        //TODO: buy from the clerk
        // Generate an inventory item that the user wants to buy
        // Buy this item type from the clerk
        // User can decide whether to accept the selling price the clerk offers at the steps of the sale

        // Generate one required item for the customer
        String customerRequiredItem = Helper.customerRequirements(Constants.CLASS_NAMES, 1).get(0);
        System.out.println("-----");
        clerk.BuyItemTransaction(store, customerRequiredItem, customerName);
        System.out.println();
    }
}
