package com.example.administrator.forimm5.DB;

/**
 * Created by Administrator on 2017-07-13.
 */

public class Center {

    private String region;
    private String name;
    private String address;
    private String lat;
    private String lng;
    private String tel;
    private String email;
    private int resId;
    private int selectedResId;
    private boolean isSelected = false;

    public int getSelectedResId() {
        return selectedResId;
    }

    public void setSelectedResId(int selectedResId) {
        this.selectedResId = selectedResId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
