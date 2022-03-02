public abstract class Instrument extends Item {

    //Instrument(){} // Default constructor set for creating decorator pattern

    Instrument (String name, double purchasePrice, int dayArrived, int condition, boolean isNew) {
        super(name, purchasePrice, dayArrived, condition, isNew);
    }

    public Instrument() {

    }
}