public class Flute extends Wind {

    String type;

    Flute (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, String type) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.type = type;
    }
}
