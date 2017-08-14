package com.example.administrator.forimm5.DB;

/**
 * Created by Administrator on 2017-07-14.
 */

public class LawChild {

    String title;
    String content;
    boolean isExpanded = false;

    public LawChild(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
