package kr.edcan.rerant.model;

/**
 * Created by JunseokOh on 2016. 10. 8..
 */
public class Menu {
    private String _id;
    private String restaurant, name, thumbnail;
    private int price;

    public Menu(String _id, String restaurant, String name, String thumbnail, int price) {
        this._id = _id;
        this.restaurant = restaurant;
        this.name = name;
        this.thumbnail = thumbnail;
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getPrice() {
        return price;
    }
}
