import java.util.ArrayList;

public class CustomStoreGuitar extends CustomGuitar {

    GuitarFactory guitarFactory;

    public CustomStoreGuitar(GuitarFactory guitarFactory){
        super();
        this.guitarFactory = guitarFactory;
    }

    CustomGuitar makeGuitar(BridgeTypes bridgeType, KnobSetTypes knobSetType, NeckTypes neckType,
                            CoversTypes coversType, PickGuardTypes pickGuardType, PickUpsTypes pickUpsType) {

        bridge = guitarFactory.createBridge(bridgeType);
        knobSet = guitarFactory.createKnobSet(knobSetType);
        neck = guitarFactory.createNeck(neckType);
        covers = guitarFactory.createCovers(coversType);
        pickGuard = guitarFactory.createPickGuard(pickGuardType);
        pickUps = guitarFactory.createPickUps(pickUpsType);

        // Get the parameters required to create a normal guitar and then customize it.
        ArrayList<Object> baseGuitarAttributes = Helper.getParams("Guitar");
        return new CustomGuitar(baseGuitarAttributes, bridge, knobSet, covers, neck, pickGuard, pickUps);
    }
}
