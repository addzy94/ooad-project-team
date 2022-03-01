public class RunStore {

    // Main method that simulates the Store
    public static void main(String[] args) {
        Store a = new Store(3); // Creates a store with 3 items of each type of the lowest level
        int days = Helper.random.nextInt(30 - 10 + 1) + 10; // Generate number of days to run between 10 and 30
        a.run(days); // Runs the store with that given number of days automatically, plus an extra day that allows the user to play as a customer
    }
}
