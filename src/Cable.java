public class Cable extends Accessory {

    double length;

    public Cable (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, double length) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
