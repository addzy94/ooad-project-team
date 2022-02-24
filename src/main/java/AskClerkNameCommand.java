public class AskClerkNameCommand implements Command{
    Clerk clerk;
    public AskClerkNameCommand(Clerk clerk){
        this.clerk = clerk;
    }
    public void execute(){
        System.out.println("The Clerk's name is: " + clerk.getName() + ".");
    }
}
