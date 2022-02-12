public class Player extends Item {

    private boolean isEqualized;

    Player (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, boolean isEqualized) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.isEqualized = isEqualized;
    }

    public boolean getIsEqualized() {
        return isEqualized;
    }

    public void setIsEqualized(boolean isEqualized) {
        this.isEqualized = isEqualized;
    }
}
