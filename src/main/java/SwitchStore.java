public class SwitchStore implements Command{
    //TODO: implement switch store method so that the customer can switch store to interact with at any time

    // sample code, not the expected one!
    Clerk clerk;
    public SwitchStore(Clerk clerk){
        //find the other clerk that is still active
        this.clerk = clerk;
        //this.clerk = otherClerk;
    }
    public void execute(){
        // both stores run
        // one of clerk has been set with Active
        // when switchStore been executed, set the current clerk to Non active
        // switch to the other clerk, together with the other store's inventory list

        //find the other clerk that is still active
        //and also get the other store as well
        //otherClerk.openStoreCustom(otherStore, xxx,xxx);
    }
}
