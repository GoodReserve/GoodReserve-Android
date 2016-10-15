package kr.edcan.rerant.model;

/**
 * Created by Junseok on 2016-10-15.
 */
public class Bucket {
    private String _id, menus;

    public Bucket(String _id, String menus) {
        this._id = _id;
        this.menus = menus;
    }

    public String get_id() {
        return _id;
    }

    public String getMenus() {
        return menus;
    }
}
