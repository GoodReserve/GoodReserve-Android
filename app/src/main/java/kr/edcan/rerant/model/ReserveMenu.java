package kr.edcan.rerant.model;

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
        return "\\" + money;
    }
}
