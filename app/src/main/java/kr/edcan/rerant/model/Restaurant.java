package kr.edcan.rerant.model;

import java.util.ArrayList;

/**
 * Created by Junseok on 2016-10-03.
 */

public class Restaurant {
    private String _id;
    private String name, address, phone;
    private ArrayList<String> category;
    private int reservation_max, reservation_current, reservation_cancel, reservation_check;
    private ArrayList<Menu> menu;

    public Restaurant(String _id, String name, String address) {
        this._id = _id;
        this.name = name;
        this.address = address;
    }

    public Restaurant(String _id, String name, String address, String phone, ArrayList<String> category, int reservation_max, int reservation_current, int reservation_cancel, int reservation_check, ArrayList<Menu> menu) {
        this._id = _id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.category = category;
        this.reservation_max = reservation_max;
        this.reservation_current = reservation_current;
        this.reservation_cancel = reservation_cancel;
        this.reservation_check = reservation_check;
        this.menu = menu;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
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

    public ArrayList<Menu> getMenu() {
        return menu;
    }

    public class Menu {
        private String _id;
        private String name;
        private int price;

        public Menu(String _id, String name, int price) {
            this._id = _id;
            this.name = name;
            this.price = price;
        }

        public String get_id() {
            return _id;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }
    }

    public class Benefit {
        private String main_benefit, sub_benefit;
        private String _id;

        public Benefit(String main_benefit, String sub_benefit, String _id) {
            this.main_benefit = main_benefit;
            this.sub_benefit = sub_benefit;
            this._id = _id;
        }

        public String getMain_benefit() {
            return main_benefit;
        }

        public String getSub_benefit() {
            return sub_benefit;
        }

        public String get_id() {
            return _id;
        }
    }
}
