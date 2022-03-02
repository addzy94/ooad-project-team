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
        // Generate one required item for the customer
        String customerBroughtItem = Helper.customerRequirements(Constants.CLASS_NAMES, 1).get(0);
        System.out.println("-----");
        // Just call the clerk to handle the sell item transaction for the rest of the steps
        clerk.SellItemTransactionCustom(store, customerBroughtItem, customerName);
        System.out.println();
    }
}
