public abstract class Stringed extends Instrument {

    private boolean isElectric;

    Stringed (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, boolean isElectric) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.isElectric = isElectric;
    }

    boolean getIsElectric() {
        return isElectric;
    }

    void setIsElectric(boolean isElectric) {
        this.isElectric = isElectric;
    }
}
