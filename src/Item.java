public abstract class Item {

    String name;
    double purchasePrice;
    double listPrice;
    boolean isNew;
    int dayArrived;
    int condition;
    double salePrice;
    int daySold;

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


