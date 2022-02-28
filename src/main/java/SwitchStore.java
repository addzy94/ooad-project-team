public class SwitchStore implements Command{
    //TODO: implement switch store method so that the customer can switch store to interact with at any time

    // sample code, not the expected one!
    Clerk clerk;
    public SwitchStore(Clerk clerk){
        this.clerk = clerk;
    }
    public void execute(){
        // both stores run
        // one of clerk has been set with Active
        // when switchStore been executed, set the current clerk to Non active
        // switch to the other clerk, together with the other store's inventory list
    }
}
