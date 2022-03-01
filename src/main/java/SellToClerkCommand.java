public class SellToClerkCommand implements Command{
    Clerk clerk;
    Store store;
    String customerName;
    public SellToClerkCommand(Clerk clerk, Store store, String customerName){
        this.clerk = clerk;
        this.store = store;
        this.customerName = customerName;
    }
    public void execute(){
        //TODO: sell to the clerk
        // Generate a normal inventory item
        // Sell this item to the clerk
        // User can decide whether to accept the buying price the clerk offers at the steps of the sale

        // Generate one required item for the customer
        String customerBroughtItem = Helper.customerRequirements(Constants.CLASS_NAMES, 1).get(0);
        System.out.println("-----");
        clerk.SellItemTransactionCustom(store, customerBroughtItem, customerName);
        System.out.println();
    }
}
