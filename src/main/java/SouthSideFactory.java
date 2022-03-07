public class SouthSideFactory implements GuitarFactory {

    public Bridge createBridge(BridgeTypes bridgeType) {

        if (bridgeType == BridgeTypes.SOUTH_BRIDGE_FOUR) {
            return new SouthBridgeFour();
        }

        else if (bridgeType == BridgeTypes.SOUTH_BRIDGE_FIVE) {
            return new SouthBridgeFive();
        }

        else { return new SouthBridgeSix(); }
    }

    public KnobSet createKnobSet(KnobSetTypes knobSetType) {

        if (knobSetType == KnobSetTypes.SOUTH_KNOBSET_FOUR) {
            return new SouthKnobSetFour();
        }

        else if (knobSetType == KnobSetTypes.SOUTH_KNOBSET_FIVE) {
            return new SouthKnobSetFive();
        }

        else { return new SouthKnobSetSix(); }
    }

    public Covers createCovers(CoversTypes coverType) {

        if (coverType == CoversTypes.SOUTH_COVERS_FOUR) {
            return new SouthCoversFour();
        }

        else if (coverType == CoversTypes.SOUTH_COVERS_FIVE) {
            return new SouthCoversFive();
        }

        else { return new SouthCoversSix(); }
    }

    public Neck createNeck(NeckTypes neckType) {

        if (neckType == NeckTypes.SOUTH_NECK_FOUR) {
            return new SouthNeckFour();
        }

        else if (neckType == NeckTypes.SOUTH_NECK_FIVE) {
            return new SouthNeckFive();
        }

        else { return new SouthNeckSix(); }
    }

    public PickGuard createPickGuard(PickGuardTypes pickGuardType) {

        if (pickGuardType == PickGuardTypes.SOUTH_PICKGUARD_FOUR) {
            return new SouthPickGuardFour();
        }

        else if (pickGuardType == PickGuardTypes.SOUTH_PICKGUARD_FIVE) {
            return new SouthPickGuardFive();
        }

        else { return new SouthPickGuardSix(); }
    }

    public PickUps createPickUps(PickUpsTypes pickUpType) {
        if (pickUpType == PickUpsTypes.SOUTH_PICKUPS_FOUR) {
            return new SouthPickUpsFour();
        }

        else if (pickUpType == PickUpsTypes.SOUTH_PICKUPS_FIVE) {
            return new SouthPickUpsFive();
        }

        else { return new SouthPickUpsSix(); }
    }

}
