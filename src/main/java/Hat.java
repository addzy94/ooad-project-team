public class Hat extends Clothing {

    private double hatSize;

    public Hat (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, double hatSize) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.hatSize = hatSize;
    }

    public double getHatSize() {
        return hatSize;
    }

    public void setHatSize(double hatSize) {
        this.hatSize = hatSize;
    }
}
