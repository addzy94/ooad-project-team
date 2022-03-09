import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BuyCustomGuitarKitFromClerkCommand implements Command {
    Clerk clerk;
    Store store;
    String customerName;

    public BuyCustomGuitarKitFromClerkCommand(Clerk clerk, Store store, String customerName) {
        this.clerk = clerk;
        this.store = store;
        this.customerName = customerName;
    }

    public void execute() {
        //TODO: buy custom guitar kit from the clerk
        // Generate a custom guitar kit that the user wants to buy
        // Buy this item type from the clerk
        // User can decide whether to accept the selling price the clerk offers at the steps of the sale

        GuitarFactory guitarFactory;
        CustomStoreGuitar customStoreGuitar;
        String partPrefix;

        // Part Prices initially 0
        double partAPrice = 0;
        double partBPrice = 0;
        double partCPrice = 0;

        double overallPrice = 0;

        if (this.store.getStoreName().equals("Northside FNMS")) {
            guitarFactory = new NorthSideFactory();
            partPrefix = "North";
        } else {
            guitarFactory = new SouthSideFactory();
            partPrefix = "South";
        }

        customStoreGuitar = new CustomStoreGuitar(guitarFactory);

        Scanner myObj = new Scanner(System.in);
        String command = null;

        ArrayList<String> validChoices = new ArrayList<>(Arrays.asList("1", "2", "3"));

        BridgeTypes bridgeType = null;
        KnobSetTypes knobSetType = null;
        NeckTypes neckType = null;
        CoversTypes coversType = null;
        PickGuardTypes pickGuardType = null;
        PickUpsTypes pickUpsType = null;

        ArrayList<Double> partPrices;

        partPrices = Helper.generatePartPrices(3);

        // Run the interactions until the user a valid
        while (!validChoices.contains(command)) {

            Helper.optionsDisplay(partPrefix, "Bridge", partPrices);
            command = myObj.nextLine();

            if (command.equals("1")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    bridgeType = BridgeTypes.NORTH_BRIDGE_ONE;
                } else {
                    bridgeType = BridgeTypes.SOUTH_BRIDGE_FOUR;
                }

            }
            else if (command.equals("2")) {
                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    bridgeType = BridgeTypes.NORTH_BRIDGE_TWO;
                } else {
                    bridgeType = BridgeTypes.SOUTH_BRIDGE_FIVE;
                }
            }
            else if (command.equals("3")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    bridgeType = BridgeTypes.NORTH_BRIDGE_THREE;
                } else {
                    bridgeType = BridgeTypes.SOUTH_BRIDGE_SIX;
                }
            }
            else {
                System.out.println("Wrong option! Please try again!");
            }
        }

        command = null;
        partPrices = Helper.generatePartPrices(3);

        // Run the interactions until the user a valid
        while (!validChoices.contains(command)) {

            Helper.optionsDisplay(partPrefix, "Knob Set",  partPrices);
            command = myObj.nextLine();

            if (command.equals("1")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    knobSetType = KnobSetTypes.NORTH_KNOBSET_ONE;
                } else {
                    knobSetType = KnobSetTypes.SOUTH_KNOBSET_FOUR;
                }
            }
            else if (command.equals("2")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    knobSetType = KnobSetTypes.NORTH_KNOBSET_TWO;
                } else {
                    knobSetType = KnobSetTypes.SOUTH_KNOBSET_FIVE;
                }
            }
            else if (command.equals("3")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    knobSetType = KnobSetTypes.NORTH_KNOBSET_THREE;
                } else {
                    knobSetType = KnobSetTypes.SOUTH_KNOBSET_SIX;
                }
            }
            else {
                System.out.println("Wrong option! Please try again!");
            }
        }

        command = null;
        partPrices = Helper.generatePartPrices(3);

        // Run the interactions until the user a valid
        while (!validChoices.contains(command)) {

            Helper.optionsDisplay(partPrefix, "Neck",  partPrices);
            command = myObj.nextLine();

            if (command.equals("1")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    neckType = NeckTypes.NORTH_NECK_ONE;
                } else {
                    neckType = NeckTypes.SOUTH_NECK_FOUR;
                }
            }
            else if (command.equals("2")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    neckType = NeckTypes.NORTH_NECK_TWO;
                } else {
                    neckType = NeckTypes.SOUTH_NECK_FIVE;
                }
            }
            else if (command.equals("3")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    neckType = NeckTypes.NORTH_NECK_THREE;
                } else {
                    neckType = NeckTypes.SOUTH_NECK_SIX;
                }
            }
            else {
                System.out.println("Wrong option! Please try again!");
            }
        }
        command = null;
        partPrices = Helper.generatePartPrices(3);

        // Run the interactions until the user a valid
        while (!validChoices.contains(command)) {

            Helper.optionsDisplay(partPrefix, "Covers",  partPrices);
            command = myObj.nextLine();

            if (command.equals("1")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    coversType = CoversTypes.NORTH_COVERS_ONE;
                } else {
                    coversType = CoversTypes.SOUTH_COVERS_FOUR;
                }
            }
            else if (command.equals("2")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    coversType = CoversTypes.NORTH_COVERS_TWO;
                } else {
                    coversType = CoversTypes.SOUTH_COVERS_FIVE;
                }
            }
            else if (command.equals("3")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    coversType = CoversTypes.NORTH_COVERS_THREE;
                } else {
                    coversType = CoversTypes.SOUTH_COVERS_SIX;
                }
            }
            else {
                System.out.println("Wrong option! Please try again!");
            }
        }

        command = null;
        partPrices = Helper.generatePartPrices(3);

        // Run the interactions until the user a valid
        while (!validChoices.contains(command)) {

            Helper.optionsDisplay(partPrefix, "Pick Guard",  partPrices);
            command = myObj.nextLine();

            if (command.equals("1")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    pickGuardType = PickGuardTypes.NORTH_PICKGUARD_ONE;
                } else {
                    pickGuardType = PickGuardTypes.SOUTH_PICKGUARD_FOUR;
                }
            }
            else if (command.equals("2")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    pickGuardType = PickGuardTypes.NORTH_PICKGUARD_TWO;
                } else {
                    pickGuardType = PickGuardTypes.SOUTH_PICKGUARD_FIVE;
                }
            }
            else if (command.equals("3")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    pickGuardType = PickGuardTypes.NORTH_PICKGUARD_THREE;
                } else {
                    pickGuardType = PickGuardTypes.SOUTH_PICKGUARD_SIX;
                }
            }
            else {
                System.out.println("Wrong option! Please try again!");
            }
        }

        command = null;
        partPrices = Helper.generatePartPrices(3);

        // Run the interactions until the user a valid
        while (!validChoices.contains(command)) {

            Helper.optionsDisplay(partPrefix, "Pick Ups",  partPrices);
            command = myObj.nextLine();

            if (command.equals("1")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    pickUpsType = PickUpsTypes.NORTH_PICKUPS_ONE;
                } else {
                    pickUpsType = PickUpsTypes.SOUTH_PICKUPS_FOUR;
                }
            }
            else if (command.equals("2")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    pickUpsType = PickUpsTypes.NORTH_PICKUPS_TWO;
                } else {
                    pickUpsType = PickUpsTypes.SOUTH_PICKUPS_FIVE;
                }
            }
            else if (command.equals("3")) {

                overallPrice += partPrices.get(Integer.parseInt(command) - 1);
                if (store.getStoreName().equals("Northside FNMS")) {
                    pickUpsType = PickUpsTypes.NORTH_PICKUPS_THREE;
                } else {
                    pickUpsType = PickUpsTypes.SOUTH_PICKUPS_SIX;
                }
            }
            else {
                System.out.println("Wrong option! Please try again!");
            }
        }

        command = null;

        Item item = customStoreGuitar.makeGuitar(
                bridgeType,
                knobSetType,
                neckType,
                coversType,
                pickGuardType,
                pickUpsType);

        item.setDayArrived(store.getDay());
        item.setListPrice(overallPrice);

        System.out.println("The overall price of the guitar is: $" + Helper.round(overallPrice));

        store.addToRegistry(store.getInventory(), "Custom Guitar", item);
        clerk.BuyItemTransaction(store, "Custom Guitar", customerName);

        // Set aside the item from inventory if customer didn't buy it
        ArrayList<Item> customGuitarsList = store.getInventory().get("Custom Guitar");
        if (customGuitarsList.contains(item)) {
            customGuitarsList.remove(item);
        }
    }
}
