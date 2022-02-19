public class ManualTuningStrategy implements TuningStrategy {

    @Override
    public Item tune(Item item) {

        int chance = Helper.random.nextInt(10);
        boolean setting = true;

        // 20% of the time we set the property to false
        if (chance < 2) { setting = false; }

        if (item instanceof Player) {
            ((Player) item).setIsEqualized(setting);
        }

        else if (item instanceof Stringed) {
            ((Stringed) item).setIsTuned(setting);
        }

        else if (item instanceof Wind) {
            ((Wind) item).setIsAdjusted(setting);
        }

        return item;
    }
}
