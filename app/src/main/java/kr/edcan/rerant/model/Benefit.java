package kr.edcan.rerant.model;

/**
 * Created by JunseokOh on 2016. 10. 8..
 */

public class Benefit {
    private String main_benefit, sub_benefit;
    private String _id;

    public String getMain_benefit() {
        return main_benefit;
    }

    public String getSub_benefit() {
        return sub_benefit;
    }

    public String get_id() {
        return _id;
    }

    public Benefit(String main_benefit, String sub_benefit, String _id) {
        this.main_benefit = main_benefit;
        this.sub_benefit = sub_benefit;
        this._id = _id;

    }
}