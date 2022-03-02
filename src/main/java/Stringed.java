public abstract class Stringed extends Instrument {

    //Stringed(){} // Default constructor set for creating decorator pattern

    private boolean isElectric;

    private boolean isTuned;

    public Stringed() {
        super();
    }

    Stringed (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, boolean isElectric, boolean isTuned) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.isElectric = isElectric;
        this.isTuned = isTuned;
    }

    boolean getIsElectric() {
        return isElectric;
    }

    void setIsElectric(boolean isElectric) {
        this.isElectric = isElectric;
    }

    boolean getIsTuned() {
        return isTuned;
    }

    void setIsTuned(boolean isTuned) {
        this.isTuned = isTuned;
    }
}
