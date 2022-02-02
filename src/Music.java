class Music extends Item{
    private String band;
    private String album;

    //constructor
    Music(){

    }
    Music(String item_name, double purchase_price, int day_arrived, int new_condition, String new_band, String new_album){
        //set all instance variables/attributes inherited from Item class
        super(item_name,purchase_price,day_arrived,new_condition);
        //set exclusive variables/attributes defined in Music class
        setBand(new_band);
        setAlbum(new_album);
    }

    //method
    String getBand(){
        return band;
    }
    void setBand(String new_band){
        band = new_band;
    }
    String getAlbum(){
        return band;
    }
    void setAlbum(String new_album){
        album = new_album;
    }
}