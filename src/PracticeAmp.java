public class PracticeAmp extends Accessory {

    double wattage;

    public PracticeAmp (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, double wattage) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.wattage = wattage;
    }

    public double getWattage() {
        return wattage;
    }

    public void setWattage(double wattage) {
        this.wattage = wattage;
    }
}
