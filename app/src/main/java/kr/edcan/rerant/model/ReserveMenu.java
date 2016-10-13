package kr.edcan.rerant.model;

import java.text.DecimalFormat;

/**
 * Created by JunseokOh on 2016. 10. 11..
 */
public class ReserveMenu {
    private String imageUrl, title, money;

    public ReserveMenu(String imageUrl, String title, String money) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.money = money;
    }

    public ReserveMenu(String imageUrl, String title, int money) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.money = money + "";
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getMoney() {
        return money;
    }

    public String getMoneyString() {
        return new DecimalFormat("#,###").format(Integer.parseInt(money)) + "원";
    }
}
