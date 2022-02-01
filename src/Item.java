public abstract class Item {
    //attributes
    String name;
    double purchasePrice;
    double listPrice;
    int newOrUsed;
    int dayArrived;
    int condition;
    double salePrice;
    int daySold;

    //constructor
    Item(){

    }
    Item(String itemName) {
        //set all instance variables/attributes
        setName(itemName);
    }

    //methods
    String getName(){
        return name;
    }
    void setName(String new_name){
        name=new_name;
    }
    double getPurchasePrice(){
        return purchasePrice;
    }
    void setPurchasePrice(Double new_price){
        purchasePrice=new_price;
    }
    double getListPrice(){
        return listPrice;
    }
    void setListPrice(Double new_price){
        listPrice=new_price;
    }
    int getNewOrUsed(){
        return newOrUsed;
    }
    void setNewOrUsed(int result){
        newOrUsed=result;
    }
    int getDayArrived(){
        return dayArrived;
    }
    void setDayArrived(int new_day){
        dayArrived=new_day;
    }
    int getCondition(){
        return condition;
    }
    void setCondition(int new_condition){
        condition=new_condition;
    }
    double getSalePrice(){
        return salePrice;
    }
    void setSalePrice(double new_price){
        salePrice=new_price;
    }
    int getDaySold(){
        return daySold;
    }
    void setDaySold(int new_day){
        daySold=new_day;
    }
}

class Music extends Item{
    private String band;
    private String album;
    Music(String item_name){
        super(item_name);
        //setBand(band);
    }
    Music(){

    }

}