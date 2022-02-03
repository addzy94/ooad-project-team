import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Constants {

    // Condition Values
    public static int POOR = 0;
    public static int FAIR = 1;
    public static int GOOD = 2;
    public static int VERY_GOOD = 3;
    public static int EXCELLENT = 4;

    // New Or Used
    public static boolean NEW = true;
    public static boolean USED = false;

    public static HashMap<Integer, String> CONDITION_MAPPING = new HashMap<>();
    public static HashMap<Boolean, String> NEW_OR_USED_MAPPING = new HashMap<>();

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

    static void generateMaps() {

        CONDITION_MAPPING.put(POOR, "Poor");
        CONDITION_MAPPING.put(FAIR, "Fair");
        CONDITION_MAPPING.put(GOOD, "Good");
        CONDITION_MAPPING.put(VERY_GOOD, "Very Good");
        CONDITION_MAPPING.put(EXCELLENT, "Excellent");

        NEW_OR_USED_MAPPING.put(NEW, "New");
        NEW_OR_USED_MAPPING.put(USED, "Used");

        Class[] MUSIC_PARAMETER_TYPE = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, String.class, String.class};
        Class[] DEFAULT_PARAMETER_TYPE = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE};
        Class[] BOOLEAN_PARAMETER_TYPE = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE};
        Class[] DOUBLE_PARAMETER_TYPE = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Double.TYPE};
        Class[] STRING_PARAMETER_TYPE = {String.class, Double.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, String.class};

        CLASS_PARAMETER_MAPPING.put("PaperScore", MUSIC_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("CD", MUSIC_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("Vinyl", MUSIC_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("CDPlayer", DEFAULT_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("RecordPlayer", DEFAULT_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("MP3Player", DEFAULT_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("Guitar", BOOLEAN_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("Bass", BOOLEAN_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("Mandolin", BOOLEAN_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("Flute", STRING_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("Harmonica", STRING_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("Hat", DOUBLE_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("Shirt", DOUBLE_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("Bandana", DEFAULT_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("PracticeAmp", DOUBLE_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("Cable", DOUBLE_PARAMETER_TYPE);
        CLASS_PARAMETER_MAPPING.put("Strings", STRING_PARAMETER_TYPE);
    }
}
