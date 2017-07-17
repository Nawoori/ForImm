package com.example.administrator.forimm5.Map;


import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.forimm5.DB.Center;
import com.example.administrator.forimm5.DB.CenterLab;
import com.example.administrator.forimm5.R;
import com.example.administrator.forimm5.Util.Const;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class MapListFragment extends Fragment {

    RecyclerView recyclerView;
    List<Center> centerList;
    MapListAdapter adapter;

    TextView listRegion;
    ImageView backto;
    ConstraintLayout layout;

    GoogleMap googleMap;

    SearchView searchView;

    public static MapListFragment newInstance(String region){
        MapListFragment mapListFragment = new MapListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("region", region);
        mapListFragment.setArguments(bundle);
        return mapListFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_list, container, false);

        String region = getArguments().getString("region");
        Log.e("확인", region);

        recyclerView = (RecyclerView) view.findViewById(R.id.centerRecycler);

        centerList = CenterLab.getInstance(getActivity()).getRegions(region);
        Log.e("확인", centerList.size()+"");

        adapter = new MapListAdapter(centerList, callMapListBack, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        listRegion = (TextView) view.findViewById(R.id.listRegion);
        listRegion.setText(region);

        backto = (ImageView) view.findViewById(R.id.backtopager);
        backto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        searchView = (SearchView) view.findViewById(R.id.searchView2);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });

        return view;
    }

    public interface CallMapListBack {
        void callMap(LatLng latLng, int zoom);
        void movePager(String title);
    }

    CallMapListBack callMapListBack;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callMapListBack = (CallMapListBack) context;
    }
}
