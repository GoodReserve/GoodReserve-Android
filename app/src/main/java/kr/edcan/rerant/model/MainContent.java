package kr.edcan.rerant.model;

/**
 * Created by JunseokOh on 2016. 10. 6..
 */
public class MainContent {
    String code, resTitle, leftTime;

    public MainContent(String code, String resTitle, String leftTime) {
        this.code = code;
        this.resTitle = resTitle;
        this.leftTime = leftTime;
    }

    public MainContent() {
    }

    public String getCode() {
        return code;
    }

    public String getResTitle() {
        return resTitle;
    }

    public String getLeftTime() {
        return leftTime;
    }
}
        