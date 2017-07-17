package com.example.administrator.forimm5.DB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-07-14.
 */

public class LawParent {

    String chapter;             // 제목
    List<LawChild> data = new ArrayList<>();        // 자식 데이터

    public LawParent(String chapter, List<LawChild> data) {
        this.chapter = chapter;
        this.data = data;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public List<LawChild> getData() {
        return data;
    }

    public void setData(List<LawChild> data) {
        this.data = data;
    }
}
