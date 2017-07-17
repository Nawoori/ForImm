package com.example.administrator.forimm5.Util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.forimm5.R;

/**
 * Created by Administrator on 2017-07-12.
 */

public class CustomTab extends LinearLayout {

    ImageView map, law, faq, mail, setting;
    ViewPager pager;

    public CustomTab(Context context) {
        super(context);
        init(context);
    }

    public CustomTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, this, false);
        addView(view);
    }


}
