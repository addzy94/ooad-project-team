public abstract class Item {

    /*
    --- ENCAPSULATION ---
        In this file, we represent encapsulation, where we bind the items
        with its private attributes and their setter and getter methods into
        the Item class.
    --- ENCAPSULATION ---
     */

    private String name;
    private double purchasePrice;
    private double listPrice;
    private boolean isNew;
    private int dayArrived;
    private int condition;
    protected double salePrice;
    private int daySold;

    /*
    --- ABSTRACTION ---
        For all the items, we abstract out its features and reduce it to just the bare components
        that are necessary. For example, we have name, price, condition, etc.
        However, we have nothing about the weight or dimensions of objects.
    --- ABSTRACTION ---
     */

    Item() {}

    Item(String name, double purchasePrice, int dayArrived, int condition, boolean isNew) {

        this.name = name;

        this.purchasePrice = purchasePrice;
        this.listPrice = 2 * purchasePrice;

        this.dayArrived = dayArrived;
        this.condition = condition;
        this.isNew = isNew;

        this.daySold = -1;
        this.salePrice = -1;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    double getPurchasePrice() {
        return purchasePrice;
    }

    void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    double getListPrice() {
        return listPrice;
    }

    void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    boolean getIsNew() {
        return isNew;
    }

    void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    int getDayArrived() {
        return dayArrived;
    }

    void setDayArrived(int dayArrived) {
        this.dayArrived = dayArrived;
    }

    int getCondition() {
        return condition;
    }

    void setCondition(int condition) {
        this.condition = condition;
    }

    double getSalePrice() {
        return salePrice;
    }

    void setSalePrice(double salePrice){
        this.salePrice = salePrice;
    }

    int getDaySold() {
        return daySold;
    }

    void setDaySold(int daySold) {
        this.daySold = daySold;
    }
}


