public class NorthSideFactory implements GuitarFactory {

    public Bridge createBridge(BridgeTypes bridgeType) {

        if (bridgeType == BridgeTypes.NORTH_BRIDGE_ONE) {
            return new NorthBridgeOne();
        }

        else if (bridgeType == BridgeTypes.NORTH_BRIDGE_TWO) {
            return new NorthBridgeTwo();
        }

        else if (bridgeType == BridgeTypes.NORTH_BRIDGE_THREE) {
            return new NorthBridgeThree();
        }

        else { return null; }
    }

    public KnobSet createKnobSet(KnobSetTypes knobSetType) {

        if (knobSetType == KnobSetTypes.NORTH_KNOBSET_ONE) {
            return new NorthKnobSetOne();
        }

        else if (knobSetType == KnobSetTypes.NORTH_KNOBSET_TWO) {
            return new NorthKnobSetTwo();
        }

        else if (knobSetType == KnobSetTypes.NORTH_KNOBSET_THREE) {
            return new NorthKnobSetThree();
        }

        else { return null; }
    }

    public Covers createCovers(CoversTypes coverType) {

        if (coverType == CoversTypes.NORTH_COVERS_ONE) {
            return new NorthCoversOne();
        }

        else if (coverType == CoversTypes.NORTH_COVERS_TWO) {
            return new NorthCoversTwo();
        }

        else if (coverType == CoversTypes.NORTH_COVERS_THREE) {
            return new NorthCoversThree();
        }

        else { return null; }
    }

    public Neck createNeck(NeckTypes neckType) {

        if (neckType == NeckTypes.NORTH_NECK_ONE) {
            return new NorthNeckOne();
        }

        else if (neckType == NeckTypes.NORTH_NECK_TWO) {
            return new NorthNeckTwo();
        }

        else if (neckType == NeckTypes.NORTH_NECK_THREE) {
            return new NorthNeckThree();
        }

        else { return null; }
    }

    public PickGuard createPickGuard(PickGuardTypes pickGuardType) {

        if (pickGuardType == PickGuardTypes.NORTH_PICKGUARD_ONE) {
            return new NorthPickGuardOne();
        }

        else if (pickGuardType == PickGuardTypes.NORTH_PICKGUARD_TWO) {
            return new NorthPickGuardTwo();
        }

        else if (pickGuardType == PickGuardTypes.NORTH_PICKGUARD_THREE) {
            return new NorthPickGuardThree();
        }

        else { return null; }
    }

    public PickUps createPickUps(PickUpsTypes pickUpType) {
        if (pickUpType == PickUpsTypes.NORTH_PICKUPS_ONE) {
            return new NorthPickUpsOne();
        }

        else if (pickUpType == PickUpsTypes.NORTH_PICKUPS_TWO) {
            return new NorthPickUpsTwo();
        }

        else if (pickUpType == PickUpsTypes.NORTH_PICKUPS_THREE) {
            return new NorthPickUpsThree();
        }

        else { return null; }
    }

}
