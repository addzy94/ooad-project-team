public abstract class Wind extends Instrument {

    private boolean adjusted;

    Wind (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, boolean adjusted) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.adjusted = adjusted;
    }

    public boolean getAdjusted() {
        return adjusted;
    }

    public void setAdjusteded(boolean adjusted) {
        this.adjusted = adjusted;
    }
}
