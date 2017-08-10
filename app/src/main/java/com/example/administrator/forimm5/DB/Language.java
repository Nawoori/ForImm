package com.example.administrator.forimm5.DB;

/**
 * Created by Administrator on 2017-08-10.
 */

public class Language {

    private String name;
    private int resId;
    private int selectedImgId;
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Language(String name, int resId, int selectedImgId) {
        this.name = name;
        this.resId = resId;
        this.selectedImgId = selectedImgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getSelectedImgId() {
        return selectedImgId;
    }

    public void setSelectedImgId(int selectedImgId) {
        this.selectedImgId = selectedImgId;
    }
}
