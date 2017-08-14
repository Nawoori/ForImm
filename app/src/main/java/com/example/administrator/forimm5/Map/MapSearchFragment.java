package com.example.administrator.forimm5.Map;


import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.forimm5.DB.Center;
import com.example.administrator.forimm5.DB.CenterLab;
import com.example.administrator.forimm5.Main.MainActivity;
import com.example.administrator.forimm5.R;
import com.example.administrator.forimm5.Util.CheckPermission;

import java.util.List;

/**
 *
 */
public class MapSearchFragment extends Fragment implements CheckPermission.CallBack {

    RecyclerView recyclerView;
    EditText queryInput;
    ImageView closeQuery;
    MapSearchAdapter adapter;
    ConstraintLayout findCenterByCurPos, findCenterByRegion;
    MainActivity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_search, container, false);
        init(view);
        setListener();
        return view;
    }

    private void init(View view){
        queryInput = (EditText) view.findViewById(R.id.queryInput);
        closeQuery = (ImageView) view.findViewById(R.id.closeQueryInput);
        recyclerView = (RecyclerView) view.findViewById(R.id.queryAllRecycler);
        findCenterByCurPos = (ConstraintLayout) view.findViewById(R.id.centerByNearbyLayout);
        findCenterByRegion = (ConstraintLayout) view.findViewById(R.id.centerByRegionLayout);
        adapter = new MapSearchAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setListener(){
        queryInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence queryString, int i, int i1, int i2) {
                List<Center> centerList = CenterLab.getInstance(getActivity()).querySome(queryString.toString());
                adapter.setData(centerList);
                if(queryString.toString().equals("")){
                    centerList.clear();
                    adapter.setData(centerList);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        closeQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryInput.setText("");
                queryInput.clearFocus();
            }
        });

        findCenterByCurPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] pemrs = {android.Manifest.permission.ACCESS_FINE_LOCATION};
                CheckPermission.checkVersion(MapSearchFragment.this, pemrs);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void callInit() {
        activity.setCenterByCurPos();
    }
}
