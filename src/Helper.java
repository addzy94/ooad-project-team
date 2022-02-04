import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Helper {

    public static Random random = new Random();

    public static int FlipNumber(int i) {
        return (i == 0) ? 1 : 0;
    }

    public static double priceEstimator(boolean isNew, int condition) {

        double price = Helper.random.nextDouble(50) + 1;
        double multiplier = 1;
        multiplier *= Constants.CONDITION_PRICE_MAPPING.get(condition);
        if (!isNew) {
            multiplier *= 0.80;
        }

        price = price * multiplier;
        return price;
    }

    public static ArrayList<Object> getParams(String itemType, int day) {
        /*
        Generates randomized but relevant parameters for each type of Item passed as 'classType'.
         */

        ArrayList<Object> params = new ArrayList<>(Arrays.asList(
                Helper.random.nextDouble(49) + 1, // PurchasePrice
                day, // Day Arrived
                Helper.random.nextInt(5) + 1, // Condition (1 to 5 levels)
                Helper.random.nextBoolean())); // IsNewOrUsed (true is New)
        switch (itemType) {
            case "PaperScore":
            case "CD":
            case "Vinyl":
                params.add(0, Constants.MUSIC_NAMES.get(Helper.random.nextInt(Constants.MUSIC_NAMES.size())));
                params.add(Constants.BAND_NAMES.get(Helper.random.nextInt(Constants.BAND_NAMES.size())));
                params.add(Constants.ALBUM_NAMES.get(Helper.random.nextInt(Constants.ALBUM_NAMES.size())));
                break;
            case "CDPlayer":
            case "RecordPlayer":
            case "MP3Player":
                params.add(0, Constants.PLAYER_NAMES.get(Helper.random.nextInt(Constants.PLAYER_NAMES.size())));
                break;
            case "Guitar":
            case "Bass":
            case "Mandolin":
                params.add(0, Constants.INSTRUMENT_NAMES.get(Helper.random.nextInt(Constants.INSTRUMENT_NAMES.size())));
                params.add(Helper.random.nextBoolean());
                break;
            case "Flute":
                params.add(0, Constants.INSTRUMENT_NAMES.get(Helper.random.nextInt(Constants.INSTRUMENT_NAMES.size())));
                params.add(Constants.FLUTE_TYPES.get(Helper.random.nextInt(Constants.FLUTE_TYPES.size())));
                break;
            case "Harmonica":
                params.add(0, Constants.INSTRUMENT_NAMES.get(Helper.random.nextInt(Constants.INSTRUMENT_NAMES.size())));
                params.add(Constants.HARMONICA_KEYS.get(Helper.random.nextInt(Constants.HARMONICA_KEYS.size())));
                break;
            case "Hat":
                params.add(0, Constants.CLOTHING_NAMES.get(Helper.random.nextInt(Constants.CLOTHING_NAMES.size())));
                params.add(Helper.random.nextDouble(8) + 6);
                break;
            case "Shirt":
                params.add(0, Constants.CLOTHING_NAMES.get(Helper.random.nextInt(Constants.CLOTHING_NAMES.size())));
                params.add(Helper.random.nextDouble(48) + 36);
                break;
            case "Bandana":
                params.add(0, Constants.CLOTHING_NAMES.get(Helper.random.nextInt(Constants.CLOTHING_NAMES.size())));
                break;
            case "PracticeAmp":
                params.add(0, Constants.ACCESSORY_NAMES.get(Helper.random.nextInt(Constants.ACCESSORY_NAMES.size())));
                params.add(Helper.random.nextDouble(200) + 20);
                break;
            case "Cable":
                params.add(0, Constants.ACCESSORY_NAMES.get(Helper.random.nextInt(Constants.ACCESSORY_NAMES.size())));
                params.add(Helper.random.nextDouble(40) + 20);
                break;
            case "Strings":
                params.add(0, Constants.ACCESSORY_NAMES.get(Helper.random.nextInt(Constants.ACCESSORY_NAMES.size())));
                params.add(Constants.STRING_TYPES.get(Helper.random.nextInt(Constants.STRING_TYPES.size())));
        }
        return params;
    }

    public static ArrayList<String> customerRequirements(ArrayList<String> itemTypes, int numberOfItems) {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < numberOfItems; i++) {
            items.add(itemTypes.get(Helper.random.nextInt(itemTypes.size())));
        }
        return items;
    }
}