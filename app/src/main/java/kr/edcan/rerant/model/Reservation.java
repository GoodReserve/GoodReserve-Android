package kr.edcan.rerant.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Junseok on 2016-10-03.
 */

public class Reservation {
    /*
    * Payment
    * 0 선금결제
    * 1 현장결제
    * */
    private String _id, reservation_marker, restaurant_id, restaurant_name, reservation_menu, reservation_code;
    private Date reservation_time;
    private int reservation_people, reservation_payment, reservation_price, cancel_type, reservation_status;

    public Reservation(String _id, String reservation_marker, String restaurant_id, String restaurant_name, String reservation_menu, String reservation_code, Date reservation_time, int reservation_people, int reservation_payment, int reservation_price, int cancel_type, int reservation_status) {
        this._id = _id;
        this.reservation_marker = reservation_marker;
        this.restaurant_id = restaurant_id;
        this.restaurant_name = restaurant_name;
        this.reservation_menu = reservation_menu;
        this.reservation_code = reservation_code;
        this.reservation_time = reservation_time;
        this.reservation_people = reservation_people;
        this.reservation_payment = reservation_payment;
        this.reservation_price = reservation_price;
        this.cancel_type = cancel_type;
        this.reservation_status = reservation_status;
    }

    public String get_id() {
        return _id;
    }

    public String getReservation_marker() {
        return reservation_marker;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public String getReservation_menu() {
        return reservation_menu;
    }

    public String getReservation_code() {
        return reservation_code;
    }

    public Date getReservation_time() {
        return reservation_time;
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

    public int getCancel_type() {
        return cancel_type;
    }

    public int getReservation_status() {
        return reservation_status;
    }
}
