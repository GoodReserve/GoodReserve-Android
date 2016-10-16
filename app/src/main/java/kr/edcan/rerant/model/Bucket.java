package kr.edcan.rerant.model;

/**
 * Created by Junseok on 2016-10-15.
 */
public class Bucket {
    private String _id, menus;
    private String restaurantId;
    public Bucket(String _id, String menus) {
        this._id = _id;
        this.menus = menus;
    }

    public Bucket(String _id, String menus, String restaurantId) {
        this._id = _id;
        this.menus = menus;
        this.restaurantId = restaurantId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String get_id() {
        return _id;
    }

    public String getMenus() {
        return menus;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
