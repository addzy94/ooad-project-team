import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RunStore {

    // Main method that simulates the Store
    public static void main(String[] args) {
        Store a = new Store();
        a.initialize(3);
        a.run(30);
    }
}
