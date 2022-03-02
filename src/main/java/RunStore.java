import java.util.ArrayList;

public class RunStore {

    // Main method that simulates the Store
    public static void main(String[] args) {
        Store northside = new Store(3, "Northside FNMS"); // Creates a store with 3 items of each type of the lowest level
        Store southside = new Store(3, "Southside FNMS");

        northside.setOtherStore(southside);
        southside.setOtherStore(northside);

        Clerk shaggy = new Clerk("Shaggy", 0, 20, new HaphazardTuningStrategy());
        Clerk velma = new Clerk("Velma", 0, 5, new ManualTuningStrategy());
        Clerk daphne = new Clerk("Daphne", 0, 10, new ElectronicTuningStrategy());
        Clerk scoovy = new Clerk("Shaggy", 0, 20, new HaphazardTuningStrategy());
        Clerk peter = new Clerk("Velma", 0, 5, new ManualTuningStrategy());
        Clerk danny = new Clerk("Daphne", 0, 10, new ElectronicTuningStrategy());

        ArrayList<Staff> new_staff = new ArrayList<>();

        new_staff.add(shaggy);
        new_staff.add(velma);
        new_staff.add(daphne);
        new_staff.add(scoovy);
        new_staff.add(peter);
        new_staff.add(danny);

        northside.setStaff(new_staff);

        northside.hireClerk(shaggy);
        northside.hireClerk(velma);
        northside.hireClerk(daphne);
        northside.hireClerk(scoovy);
        northside.hireClerk(peter);
        northside.hireClerk(danny);

        southside.hireClerk(shaggy);
        southside.hireClerk(velma);
        southside.hireClerk(daphne);
        southside.hireClerk(scoovy);
        southside.hireClerk(peter);
        southside.hireClerk(danny);

        //Prompt for number of days
        for (int i = 1; i <= 30; i++) {
            northside.sicknessCheck();
            Clerk a = northside.chooseClerk();
            Clerk b = southside.chooseClerk();
            northside.runDay(a);
            southside.runDay(b);
        }
        //Extra day
        Clerk a = northside.chooseClerk();
        Clerk b = southside.chooseClerk();
        northside.runSpecialDay(a);
        southside.runSpecialDay(b);


        northside.printSummary(30);
        southside.printSummary(30);
        //a.run(30); // Runs the store for 30 days
    }
}
