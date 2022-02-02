public class Harmonica extends Wind {

    String key;

    Harmonica (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, String key) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.key = key;
    }
}