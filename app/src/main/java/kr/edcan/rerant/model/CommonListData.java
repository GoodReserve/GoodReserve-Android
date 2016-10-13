package kr.edcan.rerant.model;

/**
 * Created by Junseok on 2016-10-04.
 */

public class CommonListData {
    private String title, content;
    private int icon;

    public CommonListData(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public CommonListData(String title, String content, int icon) {
        this.title = title;
        this.content = content;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getIcon() {
        return icon;
    }
}
