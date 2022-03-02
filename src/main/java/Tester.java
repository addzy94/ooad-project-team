// Abstract Factory Tester Method - Will be removed once everything is completed for submission.

public class Tester {
    public static void main(String[] args) {

        Store s = new Store(2);

        GuitarFactory nf = new NorthSideFactory();
        CustomStoreGuitar n = new CustomStoreGuitar(nf);

        Item i = n.makeGuitar(
                BridgeTypes.NORTH_BRIDGE_ONE,
                KnobSetTypes.NORTH_KNOBSET_TWO,
                NeckTypes.NORTH_NECK_ONE,
                CoversTypes.NORTH_COVERS_THREE,
                PickGuardTypes.NORTH_PICKGUARD_THREE,
                PickUpsTypes.NORTH_PICKUPS_TWO);

        System.out.println(((CustomGuitar) i).getBridge());

        s.addToRegistry(s.getInventory(), "Custom Guitar", i);

        s.printInventory();

    }
}
