public class AskClerkNameCommand implements Command{
    Clerk clerk;
    public AskClerkNameCommand(Clerk clerk){
        this.clerk = clerk;
    }
    public void execute(){
        System.out.println("--------------------ASK FOR CLERK'S NAME COMMAND--------------------");
        System.out.println("The customer wanted to know the Clerk's name. ");
        System.out.println("The Clerk's name is: " + clerk.getName() + ".");
    }
}
