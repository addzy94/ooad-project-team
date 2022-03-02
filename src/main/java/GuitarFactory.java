public interface GuitarFactory {

    public Bridge createBridge(BridgeTypes bridgeType);
    public KnobSet createKnobSet(KnobSetTypes knobSetType);
    public Covers createCovers(CoversTypes coverType);
    public Neck createNeck(NeckTypes neckType);
    public PickGuard createPickGuard(PickGuardTypes pickGuardType);
    public PickUps createPickUps(PickUpsTypes pickUpType);

}
