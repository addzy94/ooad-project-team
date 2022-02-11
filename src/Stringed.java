public abstract class Stringed extends Instrument {

    private boolean isElectric;

    private boolean tuned;

    Stringed (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, boolean isElectric) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.isElectric = isElectric;

        /*
        Should we set this as false by default?
        Since customer may auto bring items to store (auto-generated) that might have been tuned already
        Maybe we need to modify customerRequirements method in Helper class as well?
        */
        this.tuned = false; // Set tuned as false by default (by the time stringed instrument added into inventory list)
    }

    boolean getIsElectric() {
        return isElectric;
    }

    void setIsElectric(boolean isElectric) {
        this.isElectric = isElectric;
    }
}
