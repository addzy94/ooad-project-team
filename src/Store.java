import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Store {

    int registerAmount;
    HashMap<String, ArrayList<Item>> inventory;

    Random random;

    Store() {
    }

    public void initialize(int numberofObjects) {

        /*
        Is Going to create 'numberofObjects' Objects of each type in the Store
        on the 0th Day.
         */

        Constants.generateMaps(); // Declares all the constants and initializes them
        this.random = new Random();
        this.inventory = new HashMap<>();

        try {
            for (String className : Constants.CLASS_NAMES) {
                Class[] parameters = Constants.CLASS_PARAMETER_MAPPING.get(className);

                Class classObj = Class.forName(className);
                Constructor constructor = classObj.getConstructor(parameters);

                for(int i = 0; i < numberofObjects; i++) {
                    Object classInstance = constructor.newInstance(getParams(className, i).toArray());
                    Item it = ((Item) classInstance);
                    if (inventory.containsKey(className)) {
                        inventory.get(className).add(it);
                    }
                    else {
                        inventory.put(className, new ArrayList<Item>(Arrays.asList(it)));
                    }
                }
            }
        }

        catch(Exception e) {
            System.out.println("Errors");
            e.printStackTrace();
        }
    }

    public ArrayList<Object> getParams(String classType, int i) {
        /*
        Generates randomized but relevant parameters for each type of Item.
         */

        ArrayList<Object> params = new ArrayList<>(Arrays.asList(
                random.nextDouble(500) + 500,
                0,
                random.nextInt(5) + 1,
                random.nextBoolean()));
        switch (classType) {
            case "PaperScore":
            case "CD":
            case "Vinyl":
                params.add(0, Constants.MUSIC_NAMES.get(random.nextInt(Constants.MUSIC_NAMES.size())));
                params.add(Constants.BAND_NAMES.get(random.nextInt(Constants.BAND_NAMES.size())));
                params.add(Constants.ALBUM_NAMES.get(random.nextInt(Constants.ALBUM_NAMES.size())));
                break;
            case "CDPlayer":
            case "RecordPlayer":
            case "MP3Player":
                params.add(0, Constants.PLAYER_NAMES.get(random.nextInt(Constants.PLAYER_NAMES.size())));
                break;
            case "Guitar":
            case "Bass":
            case "Mandolin":
                params.add(0, Constants.INSTRUMENT_NAMES.get(random.nextInt(Constants.INSTRUMENT_NAMES.size())));
                params.add(random.nextBoolean());
                break;
            case "Flute":
                params.add(0, Constants.INSTRUMENT_NAMES.get(random.nextInt(Constants.INSTRUMENT_NAMES.size())));
                params.add(Constants.FLUTE_TYPES.get(random.nextInt(Constants.FLUTE_TYPES.size())));
                break;
            case "Harmonica":
                params.add(0, Constants.INSTRUMENT_NAMES.get(random.nextInt(Constants.INSTRUMENT_NAMES.size())));
                params.add(Constants.HARMONICA_KEYS.get(random.nextInt(Constants.HARMONICA_KEYS.size())));
                break;
            case "Hat":
                params.add(0, Constants.CLOTHING_NAMES.get(random.nextInt(Constants.CLOTHING_NAMES.size())));
                params.add(random.nextDouble(8) + 6);
                break;
            case "Shirt":
                params.add(0, Constants.CLOTHING_NAMES.get(random.nextInt(Constants.CLOTHING_NAMES.size())));
                params.add(random.nextDouble(48) + 36);
                break;
            case "Bandana":
                params.add(0, Constants.CLOTHING_NAMES.get(random.nextInt(Constants.CLOTHING_NAMES.size())));
                break;
            case "PracticeAmp":
                params.add(0, Constants.ACCESSORY_NAMES.get(random.nextInt(Constants.ACCESSORY_NAMES.size())));
                params.add(random.nextDouble(200) + 20);
                break;
            case "Cable":
                params.add(0, Constants.ACCESSORY_NAMES.get(random.nextInt(Constants.ACCESSORY_NAMES.size())));
                params.add(random.nextDouble(40) + 20);
                break;
            case "Strings":
                params.add(0, Constants.ACCESSORY_NAMES.get(random.nextInt(Constants.ACCESSORY_NAMES.size())));
                params.add(Constants.STRING_TYPES.get(random.nextInt(Constants.STRING_TYPES.size())));
        }
        return params;
    }

    public void displayInventory() {
        /*
        Displays everything in the inventory
         */

        for(String itemType: inventory.keySet()) {
            System.out.println("Type of Item: " + itemType);
            for (Item i: inventory.get(itemType)) {
                if (i.getDaySold() == -1) { // If it is not yet sold, display it.
                    System.out.println("\t Name: " + i.getName());
                    System.out.println("\t Purchase Price: " + i.getPurchasePrice());
                    System.out.println("\t List Price: " + i.getListPrice());
                    System.out.println("\t New? " + Constants.NEW_OR_USED_MAPPING.get(i.getIsNew()));
                    System.out.println("\t Day Arrived: " + i.getDayArrived());
                    System.out.println("\t Condition: " + Constants.CONDITION_MAPPING.get(i.getCondition()));
                    System.out.println("\t Sale Price: " + i.getSalePrice());
                    System.out.println("\t Day Sold: " + i.getDaySold());

                    if (i instanceof Music) {
                        System.out.println("\t Band Name: " + ((Music) i).getBand());
                        System.out.println("\t Album Name: " + ((Music) i).getAlbum());
                    } else if (i instanceof Instrument) {

                        if (i instanceof Stringed) {
                            System.out.println("\t Is Electric: " + ((Stringed) i).getIsElectric());
                        } else if (i instanceof Wind) {

                            if (i instanceof Flute) {
                                System.out.println("\t Flute Type: " + ((Flute) i).getType());
                            } else if (i instanceof Harmonica) {
                                System.out.println("\t Harmonica Key: " + ((Harmonica) i).getKey());
                            }
                        }
                    } else if (i instanceof Clothing) {

                        if (i instanceof Hat) {
                            System.out.println("\t Hat Size: " + ((Hat) i).getHatSize());
                        } else if (i instanceof Shirt) {
                            System.out.println("\t Harmonica Key: " + ((Shirt) i).getShirtSize());
                        }
                    } else if (i instanceof Accessory) {

                        if (i instanceof PracticeAmp) {
                            System.out.println("\t Hat Size: " + ((PracticeAmp) i).getWattage());
                        } else if (i instanceof Cable) {
                            System.out.println("\t Harmonica Key: " + ((Cable) i).getLength());
                        } else if (i instanceof Strings) {
                            System.out.println("\t Harmonica Key: " + ((Strings) i).getType());
                        }
                    }
                    System.out.println();
                }
            }
        }
    }

    public void run(int numberOfDays) {
        /*
        Runs the Store for 'numberOfDays' Days.
         */
    }

    public int getRegisterAmount() {
        return registerAmount;
    }

    public void setRegisterAmount(int registerAmount) {
        this.registerAmount = registerAmount;
    }
}
