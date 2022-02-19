public class RunStore {

    // Main method that simulates the Store
    public static void main(String[] args) {
        Store a = new Store(3); // Creates a store with 3 items of each type of the lowest level
        a.run(30); // Runs the store for 30 days
    }
}
