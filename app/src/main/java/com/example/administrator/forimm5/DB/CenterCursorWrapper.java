package com.example.administrator.forimm5.DB;

import android.database.Cursor;
import android.database.CursorWrapper;

import static com.example.administrator.forimm5.Util.Const.ADDRESS;
import static com.example.administrator.forimm5.Util.Const.EMAIL;
import static com.example.administrator.forimm5.Util.Const.LAT;
import static com.example.administrator.forimm5.Util.Const.LNG;
import static com.example.administrator.forimm5.Util.Const.NAME;
import static com.example.administrator.forimm5.Util.Const.REGION;
import static com.example.administrator.forimm5.Util.Const.TEL;



public class CenterCursorWrapper extends CursorWrapper {

    public CenterCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Center getCenterFromCursor(){
        String region = getString(getColumnIndex(REGION));
        String name = getString(getColumnIndex(NAME));
        String address = getString(getColumnIndex(ADDRESS));
        String lat = getString(getColumnIndex(LAT));
        String lng = getString(getColumnIndex(LNG));
        String tel = getString(getColumnIndex(TEL));
        String email = getString(getColumnIndex(EMAIL));

        Center center = new Center();
        center.setRegion(region);
        center.setName(name);
        center.setAddress(address);
        center.setLat(lat);
        center.setLng(lng);
        center.setTel(tel);
        center.setEmail(email);

        return center;
    }
}
