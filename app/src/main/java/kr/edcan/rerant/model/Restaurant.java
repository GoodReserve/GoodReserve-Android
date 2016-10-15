package kr.edcan.rerant.model;

import java.util.ArrayList;

/**
 * Created by Junseok on 2016-10-03.
 */

public class Restaurant {
    private String _id;
    private String name, thumbnail, address, phone;
    private ArrayList<String> category;
    private int reservation_max, reservation_current, reservation_cancel, reservation_check;
    private ArrayList<String> menu;

    public Restaurant(String name, String thumbnail, String address) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.address = address;
    }

    public Restaurant(String _id, String name, String thumbnail, String address, String phone, ArrayList<String> category, int reservation_max, int reservation_current, int reservation_cancel, int reservation_check, ArrayList<String> menu) {
        this._id = _id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.address = address;
        this.phone = phone;
        this.category = category;
        this.reservation_max = reservation_max;
        this.reservation_current = reservation_current;
        this.reservation_cancel = reservation_cancel;
        this.reservation_check = reservation_check;
        this.menu = menu;
    }


    public Restaurant(String _id, String name, String thumbnail, String address, String phone) {
        this._id = _id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.address = address;
        this.phone = phone;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public int getReservation_max() {
        return reservation_max;
    }

    public int getReservation_current() {
        return reservation_current;
    }

    public int getReservation_cancel() {
        return reservation_cancel;
    }

    public int getReservation_check() {
        return reservation_check;
    }

    public ArrayList<String> getMenu() {
        return menu;
    }


}
