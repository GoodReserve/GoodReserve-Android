package kr.edcan.rerant.model;

/**
 * Created by Junseok on 2016-10-04.
 */

public class CommonListData {
    private String title, content;

    public CommonListData(String title, String content) {
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
