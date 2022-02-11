public abstract class Wind extends Instrument {

    private boolean adjusted;

    Wind (String name, double purchasePrice, int dayArrived, int condition, boolean isNew) {
        super(name, purchasePrice, dayArrived, condition, isNew);

        /*
        Should we set this as false by default?
        Since customer may auto bring items to store (auto-generated) that might have been adjusted already
        Maybe we need to modify customerRequirements method in Helper class as well?
        */
        this.adjusted = false; // Set adjusted as false by default (by the time wind instrument added into inventory list)
    }

    public boolean getAdjusted() {
        return adjusted;
    }

    public void setAdjusteded(boolean adjusted) {
        this.adjusted = adjusted;
    }
}
