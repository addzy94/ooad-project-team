public abstract class Wind extends Instrument {

    private boolean isAdjusted;

    Wind(String name, double purchasePrice, int dayArrived, int condition, boolean isNew, boolean isAdjusted) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.isAdjusted = isAdjusted;
    }

    public boolean getIsAdjusted() {
        return isAdjusted;
    }

    public void setIsAdjusted(boolean isAdjusted) {
        this.isAdjusted = isAdjusted;
    }
}
