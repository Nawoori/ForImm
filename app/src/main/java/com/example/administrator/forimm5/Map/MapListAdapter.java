package com.example.administrator.forimm5.Map;

import android.content.Context;
import android.graphics.Camera;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.forimm5.DB.Center;
import com.example.administrator.forimm5.DB.CenterLab;
import com.example.administrator.forimm5.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-07-14.
 */

public class MapListAdapter extends RecyclerView.Adapter<MapListAdapter.Holder> {

    List<Center> data = new ArrayList<>();
    List<Center> original = new ArrayList<>();
    int position;
    Context context;
    MapListFragment.CallMapListBack fragment;
    MapListFragment listFragment;

    public MapListAdapter(List<Center> data, MapListFragment.CallMapListBack fragment, MapListFragment mapListFragment) {
        this.data = data;
        this.original = data;
        this.fragment = fragment;
        this.listFragment =  mapListFragment;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_center_recycler, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        this.position = position;
        Center center = data.get(position);
        holder.name.setText(center.getName());
        holder.address.setText(center.getAddress());
        holder.phone.setText(center.getTel());
        holder.email.setText(center.getEmail());
        holder.title = center.getName();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name, address, phone, email;
        ConstraintLayout layout;
        String title;
        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.mapListCenterName);
            address = (TextView) itemView.findViewById(R.id.mapListCenterAddres);
            phone = (TextView) itemView.findViewById(R.id.mapListCenterPhone);
            email = (TextView) itemView.findViewById(R.id.mapListCenterEmail);
            layout = (ConstraintLayout) itemView.findViewById(R.id.mapListCenterItem);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int where =  fragment.getActivity().getSupportFragmentManager().getBackStackEntryCount();
//                    String name = fragment.getActivity().getSupportFragmentManager().getBackStackEntryAt(where).getName();
//                    Fragment getFragment = fragment.getActivity().getSupportFragmentManager().findFragmentByTag(name);
//                    ((MapFragment)getFragment).moveLocal(position);
                    double lat = Double.parseDouble(data.get(position).getLat());
                    double lng = Double.parseDouble(data.get(position).getLng());
                    fragment.callMap(new LatLng(lat, lng), 14);
                    fragment.movePager(title);
                    listFragment.getActivity().onBackPressed();

                }
            });
        }
    }

    public void setMap(GoogleMap map){
        LatLng latLng = new LatLng(Double.parseDouble(CenterLab.getInstance(context).getDatas().get(position).getLat()), Double.parseDouble(CenterLab.getInstance(context).getDatas().get(position).getLng()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
    }


    public void filter(String newText){

        data = original;

        if(newText.length() == 0){
            return;
        } else {
            List<Center> newList = new ArrayList<>();

            for(Center center : data){
                if(center.getName().contains(newText) || center.getAddress().contains(newText)
                        || center.getEmail().contains(newText) || center.getTel().contains(newText)){
                    newList.add(center);
                }
            }

            data = newList;

        }

        notifyDataSetChanged();
    }
}
