public class Flute extends Wind {

    private String type;

    public Flute (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, String type, boolean adjusted) {
        super(name, purchasePrice, dayArrived, condition, isNew, adjusted);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
