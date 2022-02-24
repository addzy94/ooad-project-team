public class SellToClerkCommand implements Command{
    Clerk clerk;
    public SellToClerkCommand(Clerk clerk){
        this.clerk = clerk;
    }
    public void execute(){
        //TODO: sell to the clerk
        // Generate a normal inventory item
        // Sell this item to the clerk
        // User can decide whether to accept the buying price the clerk offers at the steps of the sale
        //clerk.SellItemTransaction();
    }
}
