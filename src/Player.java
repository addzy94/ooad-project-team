public class Player extends Item {

    private boolean equalized;

    Player (String name, double purchasePrice, int dayArrived, int condition, boolean isNew) {
        super(name, purchasePrice, dayArrived, condition, isNew);

        /*
        Should we set this as false by default?
        Since customer may auto bring items to store (auto-generated) that might have been equalized already
        Maybe we need to modify customerRequirements method in Helper class as well?
        */
        this.equalized = false; // Set equalized as false by default (by the time player added into inventory list)

    }

    public boolean getEqualized() {
        return equalized;
    }

    public void setEqualized(boolean equalized) {
        this.equalized = equalized;
    }
}
