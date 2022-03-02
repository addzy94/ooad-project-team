import java.util.ArrayList;

public class CustomGuitar extends Guitar {

    protected Bridge bridge;
    protected KnobSet knobSet;
    protected Covers covers;
    protected Neck neck;
    protected PickGuard pickGuard;
    protected PickUps pickUps;

    CustomGuitar() {}

    CustomGuitar(ArrayList<Object> baseGuitarAttributes, Bridge bridge, KnobSet knobSet, Covers covers, Neck neck, PickGuard pickGuard, PickUps pickUps) {

        super(
                baseGuitarAttributes.get(0).toString(),
                Double.parseDouble(baseGuitarAttributes.get(1).toString()),
                Integer.parseInt(baseGuitarAttributes.get(2).toString()),
                Integer.parseInt(baseGuitarAttributes.get(3).toString()),
                Boolean.parseBoolean(baseGuitarAttributes.get(4).toString()),
                Boolean.parseBoolean(baseGuitarAttributes.get(5).toString()),
                Boolean.parseBoolean(baseGuitarAttributes.get(6).toString()));

        this.bridge = bridge;
        this.knobSet = knobSet;
        this.covers = covers;
        this.neck = neck;
        this.pickGuard = pickGuard;
        this.pickUps = pickUps;
    }

    public Bridge getBridge() { return bridge; }

    public KnobSet getKnobSet() {
        return knobSet;
    }

    public Covers getCovers() {
        return covers;
    }

    public Neck getNeck() {
        return neck;
    }

    public PickGuard getPickGuard() {
        return pickGuard;
    }

    public PickUps getPickUps() {
        return pickUps;
    }
}
