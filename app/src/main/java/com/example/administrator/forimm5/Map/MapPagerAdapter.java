package com.example.administrator.forimm5.Map;

import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.forimm5.DB.Center;
import com.example.administrator.forimm5.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MapPagerAdapter extends PagerAdapter implements View.OnClickListener {

    List<Center> data;
    GoogleMap googleMap;
    LatLng latLng;
    Center center;

    MapFragment fragment;

    public MapPagerAdapter(List<Center> data, MapFragment fragment) {
        this.data = data;
        this.googleMap = googleMap;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view ==  object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_map_pager, container, false);
        TextView name = (TextView) view.findViewById(R.id.centerName);
        TextView address = (TextView) view.findViewById(R.id.centerAddress);
        TextView phone = (TextView) view.findViewById(R.id.centerTelephone);
        TextView email = (TextView) view.findViewById(R.id.centerMail);
        ConstraintLayout pagerItem = (ConstraintLayout) view.findViewById(R.id.pagerItem);

        center = data.get(position);
        name.setText(center.getName());
        address.setText(center.getAddress());
        phone.setText(center.getTel());
        email.setText(center.getEmail());
        pagerItem.setOnClickListener(this);
        Log.e("페이저 확인", center.getName());

        Typeface typeface = Typeface.createFromAsset(fragment.getActivity().getAssets(), "fonts/NotoSans-Bold.ttf");
        name.setSelected(true);
        name.setTypeface(typeface);
        address.setSelected(true);
        address.setTypeface(typeface);

        phone.setTypeface(typeface);
        email.setTypeface(typeface);
        email.setSelected(true);

        latLng = new LatLng(Double.parseDouble(center.getLat()), Double.parseDouble(center.getLng()));

        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public void onClick(View v) {
        Log.e("확인", center.getName()+"");
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
//        fragment.moveLocal(latLng, 14);
        fragment.setPagerPage();        // todo 사실 왜 위에서 instantiate 로 하면 안 되는지 모르겠다. 반드시 이유를 찾을 것!!!
    }
}
