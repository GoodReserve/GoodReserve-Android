package kr.edcan.rerant.model;

/**
 * Created by JunseokOh on 2016. 10. 6..
 */
public class MainHeader {
    String title, content;

    public MainHeader(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
