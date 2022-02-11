public class Player extends Item {

    private boolean equalized;

    Player (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, boolean equalized) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.equalized = equalized;
    }

    public boolean getEqualized() {
        return equalized;
    }

    public void setEqualized(boolean equalized) {
        this.equalized = equalized;
    }
}
