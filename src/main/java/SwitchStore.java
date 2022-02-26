public class SwitchStore implements Command{
    //TODO: implement switch store method so that the customer can switch store to interact with at any time

    // sample code, not the expected one!
    Clerk clerk;
    public SwitchStore(Clerk clerk){
        this.clerk = clerk;
    }
    public void execute(){
        System.out.println("--------------------ASK FOR CLERK'S NAME COMMAND--------------------");
        System.out.println("The customer wanted to know the Clerk's name. ");
        System.out.println("The Clerk's name is: " + clerk.getName() + ".");
    }
}
