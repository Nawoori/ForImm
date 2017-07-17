package com.example.administrator.forimm5.Setting;


import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.administrator.forimm5.R;

public class SettingsFragment extends PreferenceFragmentCompat {

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        addPreferencesFromResource(R.xml.settings);
//    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = super.onCreateView(inflater, container, savedInstanceState);
//        view.setBackgroundColor(Color.WHITE);
//        addPreferencesFromResource(R.xml.settings);
//
//        return view;
//    }
}
