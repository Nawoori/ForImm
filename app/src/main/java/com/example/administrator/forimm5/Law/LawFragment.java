package com.example.administrator.forimm5.Law;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.administrator.forimm5.DB.LawChild;
import com.example.administrator.forimm5.DB.LawLab;
import com.example.administrator.forimm5.DB.LawParent;
import com.example.administrator.forimm5.Main.MainActivity;
import com.example.administrator.forimm5.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 노동법 법률을 보여주는 프래그먼트
 */
public class LawFragment extends Fragment {

    View view;                                      // 뷰 영역
    SearchView searchView;
    LawAdapter lawAdapter;
    ExpandableListView listView;
    List<LawParent> data = new ArrayList<>();       // 자원 영역
    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 재사용성을 위해 null 일 경우에만 세팅해준다.
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_law, container, false);
            setViews();
            setListener();
            initData();
            lawAdapter = new LawAdapter(data, activity);
            listView.setAdapter(lawAdapter);
            listView.setGroupIndicator(null);
            Log.e("생명주기 확인", "LawFragment");
        }

        return view;
    }

    public void collapseGroup(){
        for (int i = 0; i < 4; i++) {
            listView.collapseGroup(i);
        }
    }

    public void setViews(){
        listView = (ExpandableListView) view.findViewById(R.id.lawExpandable);
        searchView = (SearchView) view.findViewById(R.id.lawSearch);

    }

    public void setListener(){
        searchView.setOnQueryTextListener(queryListener);
        searchView.setOnCloseListener(closeListener);
    }

    /**
     * 서치뷰에 입력 되었을 때 리스너
     */
    SearchView.OnQueryTextListener queryListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            lawAdapter.filter(newText);
            expandAll();
            return false;
        }
    };

    /**
     * 서치뷰가 닫혔을 때 리스너너
     */
    SearchView.OnCloseListener closeListener =  new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {
            lawAdapter.filter("");
            closeAll();
            return false;
        }
    };

    /**
     * 검색된 내용을 펼쳐서 보여줌
     */
    public void expandAll(){
        int count = lawAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            listView.expandGroup(i);
        }
    }

    /**
     * setOnCloseListener 호출시 원상태로 복구
     */
    public void closeAll(){
        int count = lawAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            listView.collapseGroup(i);
        }
    }

    /**
     * 데이터 세팅
     */
    public void initData(){
        List<LawChild> childList1 = LawLab.getInstance(getActivity()).getLaws("1부");
        LawParent parent1 = new LawParent("1부", childList1);
        data.add(parent1);

        List<LawChild> childList2 = LawLab.getInstance(getActivity()).getLaws("2부");
        LawParent parent2 = new LawParent("2부", childList2);
        data.add(parent2);

        List<LawChild> childList3 = LawLab.getInstance(getActivity()).getLaws("3부");
        LawParent parent3 = new LawParent("3부", childList3);
        data.add(parent3);

        List<LawChild> childList4 = LawLab.getInstance(getActivity()).getLaws("4부");
        LawParent parent4 = new LawParent("4부", childList4);
        data.add(parent4);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }
}