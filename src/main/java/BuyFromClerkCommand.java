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
        // Generate one required item for the customer
        String customerRequiredItem = Helper.customerRequirements(Constants.CLASS_NAMES, 1).get(0);
        System.out.println("-----");
        clerk.BuyItemTransactionCustom(store, customerRequiredItem, customerName);
        System.out.println();
    }
}
