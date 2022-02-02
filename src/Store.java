public class Store {

    public void initialize() {
        /*
        Is Going to Create all the Initial Objects in the Store
        on the 0th Day.
         */

        //Following Code is for testing. Will be created Randomly in the future.
        Guitar a = new Guitar("Gibson Les", 500, 0, 0, false, true);
        a.setIsNew(true);
        a.setIsElectric(false);
        System.out.println(Boolean.toString(a.getIsNew()) + " " + Boolean.toString(a.getIsElectric()));
    }

    public void run() {
        /*
        Runs the Store for 30 Days.
         */

    }
}
