public class SwitchStore implements Command{
    //TODO: implement switch store method so that the customer can switch store to interact with at any time

    // sample code, not the expected one!
    Store store;
    public SwitchStore(Store thisStore, Store otherStore){
        //find the other store
        this.store = otherStore;
    }
    public void execute(){
        // both stores run
        // one of clerk has been set with Active
        // when switchStore been executed, set the current clerk to Non active
        // switch to the other clerk, together with the other store's inventory list

        //find the other clerk that is still active
        //and also get the other store as well
        //otherClerk.openStoreCustom(otherStore, xxx,xxx);

//        BuyFromClerkCommand buyFromClerk = new BuyFromClerkCommand(this, s, customerName);
//        SellToClerkCommand sellToClerk = new SellToClerkCommand(this, s, customerName);
//        AskClerkNameCommand askClerkName = new AskClerkNameCommand(this);
//        AskClerkTimeCommand askClerkTime = new AskClerkTimeCommand(this);
//        BuyCustomGuitarKitFromClerkCommand buyCustomGuitarKitFromClerk= new BuyCustomGuitarKitFromClerkCommand(this);
    }
}
