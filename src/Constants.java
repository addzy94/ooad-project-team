import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Constants {

    // Condition Values
    public static int POOR = 1;
    public static int FAIR = 2;
    public static int GOOD = 3;
    public static int VERY_GOOD = 4;
    public static int EXCELLENT = 5;

    // New Or Used
    public static boolean NEW = true;
    public static boolean USED = false;

    // Clerks
    public static int SHAGGY = 0;
    public static int VELMA = 1;

    // Days of the Week
    public static int MONDAY = 1;
    public static int TUESDAY = 2;
    public static int WEDNESDAY = 3;
    public static int THURSDAY = 4;
    public static int FRIDAY = 5;
    public static int SATURDAY = 6;
    public static int SUNDAY = 7;

    //create hashmap/dictionary for saving and assigning those pre-defined string values as int
    public static HashMap<Integer, String> CONDITION_MAPPING = new HashMap<>();
    public static HashMap<Integer, Double> CONDITION_PRICE_MAPPING = new HashMap<>();
    public static HashMap<Boolean, String> NEW_OR_USED_MAPPING = new HashMap<>();
    public static HashMap<Integer, String> CLERK_MAPPING = new HashMap<>();
    public static HashMap<Integer, String> DAY_MAPPING = new HashMap<>();

    public static HashMap<String, Class[]> CLASS_PARAMETER_MAPPING = new HashMap<>();

    public static ArrayList<String> MUSIC_NAMES = new ArrayList<>(Arrays.asList(
            "Get Lucky",
            "One Step Forward",
            "Virtual Diva",
            "American Boy",
            "Thunder",
            "Swalla",
            "Kashmir",
            "Bleed It Out"
    ));

    public static ArrayList<String> INSTRUMENT_NAMES = new ArrayList<>(Arrays.asList(
            "Gibson Les Paul Standard",
            "Lucille",
            "Muddywood",
            "Guild Starfire",
            "Rickenbacker 4001S",
            "Fender Jazz Bass",
            "Don Junior",
            "Dark Zapper",
            "Smooth Noodle"
    ));

    public static ArrayList<String> BAND_NAMES = new ArrayList<>(Arrays.asList(
            "Led Zeppelin",
            "AC/DC",
            "Maroon 5"
    ));

    public static ArrayList<String> ALBUM_NAMES = new ArrayList<>(Arrays.asList(
            "Led Zeppelin IV",
            "Highway to Hell",
            "The Fourth World"
    ));

    public static ArrayList<String> PLAYER_NAMES = new ArrayList<>(Arrays.asList(
            "RocketRon 5000",
            "Runner 200",
            "Bose 100",
            "AudioTechnica M50TH",
            "Sony MX",
            "JBL 700BT"
    ));

    public static ArrayList<String> FLUTE_TYPES = new ArrayList<>(Arrays.asList(
            "Standard",
            "Piccolo",
            "Harmony"
    ));

    public static ArrayList<String> HARMONICA_KEYS = new ArrayList<>(Arrays.asList(
            "Ab",
            "A",
            "Bb",
            "B",
            "C",
            "Dd",
            "D",
            "Eb",
            "E",
            "F",
            "F#",
            "G"
    ));

    public static ArrayList<String> SAXOPHONE_TYPES = new ArrayList<>(Arrays.asList(
            "Soprano",
            "Alto",
            "Tenor",
            "Baritone"
    ));

    public static ArrayList<String> CLOTHING_NAMES = new ArrayList<>(Arrays.asList(
            "LVMH Red Dragon",
            "Tommy Hilfiger 200",
            "United Colors of Benetton Shiner"
    ));

    public static ArrayList<String> ACCESSORY_NAMES = new ArrayList<>(Arrays.asList(
            "Beautiful 100",
            "Melancholy 50",
            "Morose 400",
            "Abstraction 300"
    ));

    public static ArrayList<String> STRING_TYPES = new ArrayList<>(Arrays.asList(
            "Steel and Nickel",
            "Brass and Bronze",
            "Nylon"
    ));

    public static ArrayList<String> CLASS_NAMES = new ArrayList<>(Arrays.asList(
            "PaperScore",
            "CD",
            "Vinyl",
            "CDPlayer",
            "RecordPlayer",
            "MP3Player",
            "Guitar",
            "Bass",
            "Mandolin",
            "Flute",
            "Harmonica",
            "Hat",
            "Shirt",
            "Bandana",
            "PracticeAmp",
            "Cable",
            "Strings",
            "Saxophone",
            "Cassette",
            "CassettePlayer",
            "GigBag"
            ));

    public static ArrayList<String> CLOTHING_ITEM_TYPES = new ArrayList<>(Arrays.asList("Hat", "Shirt", "Bandana"));

    public static ArrayList<String> CUSTOMER_NAMES = new ArrayList<>(Arrays.asList(
            "Tom Cruise",
            "Clint Eastwood",
            "Leonardo DiCaprio",
            "Keanu Reeves",
            "Mark Wahlberg",
            "Jhonny Cage",
            "Natalie Portman",
            "Andy Samberg",
            "Brad Pitt"
    ));

    static void generateMaps() {

        CONDITION_MAPPING.put(POOR, "Poor");
        CONDITION_MAPPING.put(FAIR, "Fair");
        CONDITION_MAPPING.put(GOOD, "Good");
        CONDITION_MAPPING.put(VERY_GOOD, "Very Good");
        CONDITION_MAPPING.put(EXCELLENT, "Excellent");

        CONDITION_PRICE_MAPPING.put(POOR, 0.25);
        CONDITION_PRICE_MAPPING.put(FAIR, 0.35);
        CONDITION_PRICE_MAPPING.put(GOOD, 0.5);
        CONDITION_PRICE_MAPPING.put(VERY_GOOD, 0.75);
        CONDITION_PRICE_MAPPING.put(EXCELLENT, 1.0);

        DAY_MAPPING.put(MONDAY, "Monday");
        DAY_MAPPING.put(TUESDAY, "Tuesday");
        DAY_MAPPING.put(WEDNESDAY, "Wednesday");
        DAY_MAPPING.put(THURSDAY, "Thursday");
        DAY_MAPPING.put(FRIDAY, "Friday");
        DAY_MAPPING.put(SATURDAY, "Saturday");
        DAY_MAPPING.put(SUNDAY, "Sunday");

        CLERK_MAPPING.put(SHAGGY, "Shaggy");
        CLERK_MAPPING.put(VELMA, "Velma");

        NEW_OR_USED_MAPPING.put(NEW, "New");
        NEW_OR_USED_MAPPING.put(USED, "Used");

        // Music
        Class[] PAPER_SCORE = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, String.class, String.class};
        Class[] CD = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, String.class, String.class};
        Class[] VINYL = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, String.class, String.class};
        Class[] CASSETTE = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, String.class, String.class};

        // Player
        Class[] CD_PLAYER = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE};
        Class[] MP3_PLAYER = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE};
        Class[] RECORD_PLAYER = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE};
        Class[] CASSETTE_PLAYER = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE};

        // Instrument - Stringed
        Class[] GUITAR = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE};
        Class[] BASS = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE};
        Class[] MANDOLIN = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE};

        // Instrument - Wind
        Class[] FLUTE = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE, String.class};
        Class[] HARMONICA = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE, String.class};
        Class[] SAXOPHONE = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE, String.class};

        // Clothing
        Class[] HAT = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Double.TYPE};
        Class[] SHIRT = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Double.TYPE};
        Class[] BANDANA = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE};

        // Accessory
        Class[] PRACTICE_AMP = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Double.TYPE};
        Class[] CABLE = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Double.TYPE};
        Class[] STRINGS = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, String.class};
        Class[] GIG_BAG = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE};

        // Music
        CLASS_PARAMETER_MAPPING.put("PaperScore", PAPER_SCORE);
        CLASS_PARAMETER_MAPPING.put("CD", CD);
        CLASS_PARAMETER_MAPPING.put("Vinyl", VINYL);
        CLASS_PARAMETER_MAPPING.put("Cassette", CASSETTE);
        // Player
        CLASS_PARAMETER_MAPPING.put("CDPlayer", CD_PLAYER);
        CLASS_PARAMETER_MAPPING.put("RecordPlayer", RECORD_PLAYER);
        CLASS_PARAMETER_MAPPING.put("MP3Player", MP3_PLAYER);
        CLASS_PARAMETER_MAPPING.put("CassettePlayer", CASSETTE_PLAYER);
        // Instrument - Stringed
        CLASS_PARAMETER_MAPPING.put("Guitar", GUITAR);
        CLASS_PARAMETER_MAPPING.put("Bass", BASS);
        CLASS_PARAMETER_MAPPING.put("Mandolin", MANDOLIN);
        // Instrument - Wind
        CLASS_PARAMETER_MAPPING.put("Flute", FLUTE);
        CLASS_PARAMETER_MAPPING.put("Harmonica", HARMONICA);
        CLASS_PARAMETER_MAPPING.put("Saxophone", SAXOPHONE);
        // Clothing
        CLASS_PARAMETER_MAPPING.put("Hat", HAT);
        CLASS_PARAMETER_MAPPING.put("Shirt", SHIRT);
        CLASS_PARAMETER_MAPPING.put("Bandana", BANDANA);
        // Accessory
        CLASS_PARAMETER_MAPPING.put("PracticeAmp", PRACTICE_AMP);
        CLASS_PARAMETER_MAPPING.put("Cable", CABLE);
        CLASS_PARAMETER_MAPPING.put("Strings", STRINGS);
        CLASS_PARAMETER_MAPPING.put("GigBag", GIG_BAG);
    }
}
