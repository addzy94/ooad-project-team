public class BuyFromClerkCommand implements Command{
    Clerk clerk;
    public BuyFromClerkCommand(Clerk clerk){
        this.clerk = clerk;
    }
    public void execute(){
        //TODO: buy from the clerk
        // Generate an inventory item that the user wants to buy - normal inventory??
        // Buy this item type from the clerk
        // User can decide whether to accept the selling price the clerk offers at the steps of the sale
        //clerk.BuyItemTransaction();
    }
}
