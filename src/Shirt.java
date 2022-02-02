public class Shirt extends Clothing {

    double shirtSize;

    Shirt (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, double shirtSize) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.shirtSize = shirtSize;
    }

    public double getShirtSize() {
        return shirtSize;
    }

    public void setShirtSize(int shirtSize) {
        this.shirtSize = shirtSize;
    }
}
