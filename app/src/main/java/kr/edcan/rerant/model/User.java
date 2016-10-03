package kr.edcan.rerant.model;

import java.util.ArrayList;

/**
 * Created by JunseokOh on 2016. 9. 24..
 */
public class User {
    private int userType;
    private String email, name, phone, auth_token, reservation, _id;
    private ArrayList<String> reservation_waiting;

    public User(int userType, String email, String name, String phone, String auth_token) {
        this.email = email;
        this.userType = userType;
        this.name = name;
        this.phone = phone;
        this.auth_token = auth_token;
    }

    public User(int userType, String email, String name, String phone, String auth_token, String reservation) {
        this.email = email;
        this.userType = userType;
        this.name = name;
        this.phone = phone;
        this.auth_token = auth_token;
        this.reservation = reservation;
    }

    public User(int userType, String email, String name, String phone, String auth_token, String reservation, ArrayList<String> reservation_waiting) {
        this.email = email;
        this.name = name;
        this.userType = userType;
        this.phone = phone;
        this.auth_token = auth_token;
        this.reservation = reservation;
        this.reservation_waiting = reservation_waiting;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public String getReservation() {
        return reservation;
    }

    public ArrayList<String> getReservation_waiting() {
        return reservation_waiting;
    }
}
