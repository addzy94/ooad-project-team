public class ElectronicTuningStrategy implements TuningStrategy {

    @Override
    public Item tune(Item item) {

        if (item instanceof Player) {
            ((Player) item).setIsEqualized(true);
        }

        else if (item instanceof Stringed) {
            ((Stringed) item).setIsTuned(true);
        }

        else if (item instanceof Wind) {
            ((Wind) item).setIsAdjusted(true);
        }

        return item;
    }
}
