public class Flute extends Wind {

    String type;

    public Flute (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, String type) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
