public class CommandController {
    Command slot;
    public CommandController(){}

    public void setCommand(Command command){
        slot = command;
    }
    public void buttonPressed(){
        slot.execute();
    }
}
