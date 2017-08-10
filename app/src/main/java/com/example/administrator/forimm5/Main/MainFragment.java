package com.example.administrator.forimm5.Main;


import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private ContainerFragment containerFragment;    // 자원 영역
    private LanguageAdapter adapter;
    private View view;                              // 뷰 영역
    private ImageView settings;
    private Button goMap, goLaw, goFaq, goEmail;
    private RecyclerView languageRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 재사용성 여부 따질 필요 없음.
        view = inflater.inflate(R.layout.fragment_main, container, false);
        setFragment();
        init(view);
        setListener();
        setLanguageRecycler();
        return view;
    }

    /**
     * 프래그먼트 생성
     */
    public void setFragment() {
        containerFragment = new ContainerFragment();
    }

    /**
     * 뷰 초기화
     */
    private void init(View view) {
        goMap = (Button) view.findViewById(R.id.goMap);
        goLaw = (Button) view.findViewById(R.id.goLaw);
        goFaq = (Button) view.findViewById(R.id.goFaq);
        goEmail = (Button) view.findViewById(R.id.goEmail);
        settings = (ImageView) view.findViewById(R.id.settings);
        languageRecycler = (RecyclerView) view.findViewById(R.id.languageRecycler);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NotoSans-Regular.ttf");
        goMap.setTypeface(typeface);
        goLaw.setTypeface(typeface);
        goFaq.setTypeface(typeface);
        goEmail.setTypeface(typeface);

    }

    /**
     * 리스너 설정
     */
    private void setListener() {
        goMap.setOnClickListener(this);
        goLaw.setOnClickListener(this);
        goFaq.setOnClickListener(this);
        goEmail.setOnClickListener(this);
        settings.setOnClickListener(this);
    }



    /**
     * 어댑터 생성, 리사이클러뷰에 설정, 라사이클러뷰 매니저 설정
     */
    public void setLanguageRecycler(){
        adapter = new LanguageAdapter(getContext());
        languageRecycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        languageRecycler.setLayoutManager(manager);
    }


    /**
     * 컨테이너 프래그먼트 띄우고 -> 컨테이너 프래그먼트에서 보여줄 프래그먼트를 다시 설정해 준다
     */
    public void onClick(View view) {
        // 컨테이너 프래그먼트 띄위기
        callFragment(containerFragment);
        // 내부 프래그먼트 설정
        switch (view.getId()) {
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

    /**
     * 프래그먼트 띄우기 -- 맵의 경우 시간이 오래 걸릴 경우 스레드로 뺴준다.
     */
    public void callFragment(final Fragment fragment) {
        new AsyncTask<Void, Void, Void>() {
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

    public void callContainer(LatLng latLng, int zoom) {
        containerFragment.callMap(latLng, zoom);
    }

    public void movePager(String title) {
        containerFragment.movePager(title);
    }
}
