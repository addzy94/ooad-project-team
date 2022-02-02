public abstract class Music extends Item {

    String band;
    String album;

    Music (String name, double purchasePrice, int dayArrived, int condition, boolean isNew, String band, String album) {
        super(name, purchasePrice, dayArrived, condition, isNew);
        this.band = band;
        this.album = album;
    }

    String getBand() {
        return band;
    }
    void setBand(String new_band) {
        this.band = new_band;
    }
    String getAlbum() {
        return band;
    }
    void setAlbum(String new_album) {
        this.album = new_album;
    }
}