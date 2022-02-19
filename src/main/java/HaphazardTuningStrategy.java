public class HaphazardTuningStrategy implements TuningStrategy {

    @Override
    public Item tune(Item item) {

        boolean changeProperty = Helper.random.nextBoolean();

        if (changeProperty) {

            if (item instanceof Player) {
                if (((Player) item).getIsEqualized()) {
                    ((Player) item).setIsEqualized(false);
                }
                else {
                    ((Player) item).setIsEqualized(true);
                }
            }

            else if (item instanceof Stringed) {
                if (((Stringed) item).getIsTuned()) {
                    ((Stringed) item).setIsTuned(false);
                }
                else {
                    ((Stringed) item).setIsTuned(true);
                }
            }

            else if (item instanceof Wind) {
                if (((Wind) item).getIsAdjusted()) {
                    ((Wind) item).setIsAdjusted(false);
                }
                else {
                    ((Wind) item).setIsAdjusted(true);
                }
            }
        }

        return item;
    }
}
