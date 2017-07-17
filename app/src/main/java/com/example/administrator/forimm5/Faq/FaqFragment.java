package com.example.administrator.forimm5.Faq;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.administrator.forimm5.R;

import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FaqFragment extends Fragment {

    ExpandableListView listView;
    FaqAdapter faqAdapter;
    List<String> listdata;
    Map<String, List<String>> listMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);

        // 1.
//        listView = (ExpandableListView) view.findViewById(R.id.faqExpandable);

        // 2.
//        initData();

        // 3.
//        faqAdapter = new FaqAdapter(listdata, listMap);

        // 4.
//        listView.setAdapter(faqAdapter);
//        listView.setGroupIndicator(null);

        return view;
    }


}
