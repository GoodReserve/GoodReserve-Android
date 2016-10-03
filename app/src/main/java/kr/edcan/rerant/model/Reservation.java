package kr.edcan.rerant.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Junseok on 2016-10-03.
 */

public class Reservation {
    private String _id;
    private String restaurant_name, reservation_code;
    private int reservation_people, reservation_payment, reservation_price;
    private ArrayList<Restaurant.Menu> reservation_menu;
    private Date reservation_time;

    public Reservation(String _id, String restaurant_name, String reservation_code, int reservation_people, int reservation_payment, int reservation_price, ArrayList<Restaurant.Menu> reservation_menu, Date reservation_time) {
        this._id = _id;
        this.restaurant_name = restaurant_name;
        this.reservation_code = reservation_code;
        this.reservation_people = reservation_people;
        this.reservation_payment = reservation_payment;
        this.reservation_price = reservation_price;
        this.reservation_menu = reservation_menu;
        this.reservation_time = reservation_time;
    }

    public String get_id() {
        return _id;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public String getReservation_code() {
        return reservation_code;
    }

    public int getReservation_people() {
        return reservation_people;
    }

    public int getReservation_payment() {
        return reservation_payment;
    }

    public int getReservation_price() {
        return reservation_price;
    }

    public ArrayList<Restaurant.Menu> getReservation_menu() {
        return reservation_menu;
    }

    public Date getReservation_time() {
        return reservation_time;
    }
}
