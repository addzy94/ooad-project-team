public class AskClerkTimeCommand implements Command{
    Clerk clerk;
    public AskClerkTimeCommand(Clerk clerk){
        this.clerk = clerk;
    }
    public void execute(){
        System.out.println("The Clerk " + clerk.getName() + " said: Now the time is " + java.time.LocalTime.now());
        System.out.println();
    }
}
