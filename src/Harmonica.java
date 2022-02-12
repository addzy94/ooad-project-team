public class Harmonica extends Wind {

    private String key;

    public Harmonica (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, boolean adjusted, String key) {
        super(name, purchasePrice, dayArrived, condition, isNew, adjusted);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}