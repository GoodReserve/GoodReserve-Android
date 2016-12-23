package kr.edcan.rerant.model;

import java.util.ArrayList;

/**
 * Created by Junseok on 2016-10-15.
 */
public class Bucket {
    private String _id;
    private ArrayList<Menu> menus;
    private String restaurantId;

    public Bucket(String _id, ArrayList<Menu> menus, String restaurantId) {
        this._id = _id;
        this.menus = menus;
        this.restaurantId = restaurantId;
    }

    public String get_id() {
        return _id;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
