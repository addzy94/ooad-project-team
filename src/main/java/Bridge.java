public abstract class Bridge {

    Bridge() {}
}

class NorthBridgeOne extends Bridge {

    NorthBridgeOne() {
        super();
    }

    public String toString() { return "North Bridge One"; }
}

class NorthBridgeTwo extends Bridge {

    NorthBridgeTwo() {
        super();
    }

    public String toString() { return "North Bridge Two"; }
}

class NorthBridgeThree extends Bridge {

    NorthBridgeThree() {
        super();
    }

    public String toString() { return "North Bridge Three"; }
}

class SouthBridgeFour extends Bridge {

    SouthBridgeFour() {
        super();
    }

    public String toString() { return "South Bridge Four"; }
}

class SouthBridgeFive extends Bridge {

    SouthBridgeFive() { super(); }

    public String toString() { return "South Bridge Five"; }
}

class SouthBridgeSix extends Bridge {

    SouthBridgeSix() {
        super();
    }

    public String toString() { return "South Bridge Six"; }
}
