public abstract class Item {
    //attributes
    String name;
    Double purchasePrice;
    Double listPrice;
    Integer newOrUsed;
    Integer dayArrived;
    Integer condition;
    Double salePrice;
    Integer daySold;

    //abstract methods
    abstract String getName();
    abstract void setName(String new_name);
}

class Music extends Item{
    String getName(){
        return name;
    }
    void setName(String new_name){
        name=new_name;
    }
}