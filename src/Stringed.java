public abstract class Stringed extends Instrument {

    private boolean isElectric;

    private boolean tuned;

    Stringed (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, boolean isElectric, boolean tuned) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.isElectric = isElectric;
        this.tuned = tuned;
    }

    boolean getIsElectric() {
        return isElectric;
    }

    void setIsElectric(boolean isElectric) {
        this.isElectric = isElectric;
    }
}
