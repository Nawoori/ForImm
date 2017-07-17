package com.example.administrator.forimm5.Main;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.forimm5.R;
import com.google.android.gms.maps.model.LatLng;

/**
 * 처음 보이는 화면이 바로 이 프래그먼트이다. 이 화면에서 1. 언어선택 2. 페이지 선택이 이루어진다.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    ContainerFragment containerFragment;    // 자원 영역
    View view;                              // 뷰 영역
    ImageView settings;
    Button goMap, goLaw, goFaq, goEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_main, container, false);
            setFragment();
            init(view);
            setListener();
            Log.e("생명주기 확인", "MainFragment");
            // 이게 계속 확인되는 것으로 보아 액티비티가 죽으면 프래그먼트도 죽는 듯 하다.
            // 메인 프래그먼트는 고정되어 있어서 재사용 할 경우가 없다. 따라서 굳이 생명주기 관리, 재사용성 관리를 해 주지 않아도 된다.
        }
        return view;
    }


    // 프래그먼트 생성
    public void setFragment(){
        containerFragment = new ContainerFragment();
    }

    // 초기화
    private void init(View view){
        goMap = (Button) view.findViewById(R.id.goMap);
        goLaw = (Button) view.findViewById(R.id.goLaw);
        goFaq = (Button) view.findViewById(R.id.goFaq);
        goEmail = (Button) view.findViewById(R.id.goEmail);
        settings = (ImageView) view.findViewById(R.id.settings);
    }

    private void setListener(){
        goMap.setOnClickListener(this);
        goLaw.setOnClickListener(this);
        goFaq.setOnClickListener(this);
        goEmail.setOnClickListener(this);
        settings.setOnClickListener(this);
    }

    // 페이지 넘어가기
    public void onClick(View view){
        callFragment(containerFragment);
        switch(view.getId()){
            case R.id.goMap:
                containerFragment.setFragmentPosition(0);
                break;
            case R.id.goLaw:
                containerFragment.setFragmentPosition(1);
                break;
            case R.id.goFaq:
                containerFragment.setFragmentPosition(2);
                break;
            case R.id.goEmail:
                containerFragment.setFragmentPosition(3);
                break;
            case R.id.settings:
                containerFragment.setFragmentPosition(4);
        }
    }

    // 프래그먼트 띄우기 -- 맵의 경우 시간이 오래 걸릴 경우 대비해서 스레드로 뺴준다.
    public void callFragment(final Fragment fragment){
        new AsyncTask<Void, Void, Void>(){
            ProgressDialog dialog = new ProgressDialog(getActivity());

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("로딩 중");
                dialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left)
                        .add(R.id.fragmentContainer, fragment)
                        .commit();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                dialog.dismiss();
            }
        }.execute();
    }

    public void callContainer(LatLng latLng, int zoom){
        containerFragment.callMap(latLng, zoom);
    }

    public void movePager(String title){
        containerFragment.movePager(title);
    }
}
