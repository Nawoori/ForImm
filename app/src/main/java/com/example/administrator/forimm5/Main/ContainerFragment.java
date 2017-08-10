package com.example.administrator.forimm5.Main;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.forimm5.Email.EmailFragment;
import com.example.administrator.forimm5.Faq.FaqFragment;
import com.example.administrator.forimm5.Law.LawFragment;
import com.example.administrator.forimm5.Map.MapFragment;
import com.example.administrator.forimm5.R;
import com.example.administrator.forimm5.Setting.SettingsFragment;
import com.example.administrator.forimm5.Util.CustomTab;
import com.google.android.gms.maps.model.LatLng;

/**
 * 굳이 컨테이너 프래그먼트로 해 준 이유는 이 프래그먼트에 또 다시 프래그먼트들이 올라오기 때문이다. 이렇게 해 준 이유는
 * 탭 바는 고정된 채로 프래그먼트만 바꿔끼우는 효과를 보이기 위해서이다.
 *
 * *********조심할 것은 여기서부터는 반드시 생명주기 관리를 해 줘야 하는 것이다.**********
 */
public class ContainerFragment extends Fragment implements View.OnClickListener{

    int position;                   // 자원 영역
    View view;
    MapFragment mapFragment;
    LawFragment lawFragment;
    FaqFragment faqFragment;
    EmailFragment emailFragment;
    SettingsFragment settingsFragment;
    CustomTab customTab;             // 뷰 영역
    ImageView goMap, goLaw, goFaq, goEmail, settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_container, container, false);
            setFragment();              // 프래그먼트 생성
            setViews();                  // 탭 초기화
            setListener();
            Log.e("생명주기 확인", "ContainerFragment");
        }
        switchFragment(position);       // 프래그먼트 교체
        return view;
    }

    /**
     * 프래그먼트 포지션값 설정 -- 메인에서 버튼을 누르고 ContainerFragment 가 호출될 때 어떤 세부 프래그먼트를 보여줄 것인지 결정
     */
    public void setFragmentPosition(int position){
        this.position = position;
    }

    public void setFragment(){
        mapFragment = new MapFragment();
        lawFragment = new LawFragment();
        faqFragment = new FaqFragment();
        emailFragment = new EmailFragment();
        settingsFragment = new SettingsFragment();
    }

    public void setViews(){
        customTab = (CustomTab) view.findViewById(R.id.customTab);
        goMap = (ImageView) view.findViewById(R.id.customMapImg);
        goLaw = (ImageView) view.findViewById(R.id.customLawImg);
        goFaq = (ImageView) view.findViewById(R.id.customFaqImg);
        goEmail = (ImageView) view.findViewById(R.id.customMailImg);
        settings = (ImageView) view.findViewById(R.id.customSettingsImg);
    }

    public void setListener(){
        goMap.setOnClickListener(this);
        goLaw.setOnClickListener(this);
        goFaq.setOnClickListener(this);
        goEmail.setOnClickListener(this);
        settings.setOnClickListener(this);
    }

    /**
     * 메인에서 버튼을 누를 떄 세부 프래그먼트 띄우기
     * callFragment 와 callFragmentByAnimation 을 분리해 준 이유는 처음 containerFragment 가 애니메이션으로 뜰 때 안에서는 애니메이션으로 뜨면 안 되기 때문
     */
    public void switchFragment(int position) {
        switch (position) {
            case 0:
                callFragment(mapFragment);
                goMap.setImageResource(R.drawable.map_selected);
                break;
            case 1:
                callFragment(lawFragment);
                goLaw.setImageResource(R.drawable.law_selected);
                break;
            case 2:
                callFragment(faqFragment);
                goFaq.setImageResource(R.drawable.faq_selected);
                break;
            case 3:
                callFragment(emailFragment);
                goEmail.setImageResource(R.drawable.mail_selected);
                break;
            case 4:
                callFragment(settingsFragment);
                settings.setImageResource(R.drawable.settings_selected);
                break;
        }
    }

    /**
     * 탭을 눌렀을 때 프래그먼트 변경
     */
    @Override
    public void onClick(View v) {
        // 탭을 unselected 상태로 교체
        initImg();
        switch (v.getId()){
            case R.id.customMapImg:
                // 안쪽 프래그먼트가 움직일 떄 따로 애니메이션으로 움직이도록 해 준다.
                callFragmentByAnimation(mapFragment);
                // 이미지를 선택된 이미지로 바꿔준다.
                goMap.setImageResource(R.drawable.map_selected);
                break;
            case R.id.customLawImg:
                callFragmentByAnimation(lawFragment);
                goLaw.setImageResource(R.drawable.law_selected);
                break;
            case R.id.customFaqImg:
                callFragmentByAnimation(faqFragment);
                goFaq.setImageResource(R.drawable.faq_selected);
                break;
            case R.id.customMailImg:
                callFragmentByAnimation(emailFragment);
                goEmail.setImageResource(R.drawable.mail_selected);
                break;
            case R.id.customSettingsImg:
                callFragmentByAnimation(settingsFragment);
                settings.setImageResource(R.drawable.settings_selected);
                break;
        }
    }

    /**
     * 처음 컨테이너가 메인에서 올라올 때 프래그먼트 장착
     */
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
                        .replace(R.id.subFragmentContainer, fragment)
                        .commit();
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                dialog.dismiss();
            }
        }.execute();
    }

    /**
     * 탭 버튼으로 프래그먼트 교체
     */
    public void callFragmentByAnimation(final Fragment fragment){
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
                        .replace(R.id.subFragmentContainer, fragment)
                        .commit();
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                dialog.dismiss();
            }
        }.execute();
    }

    /**
     * 탭 unselected 로 초기화
     */
    public void initImg(){
        goMap.setImageResource(R.drawable.map);
        goLaw.setImageResource(R.drawable.law);
        goFaq.setImageResource(R.drawable.faq);
        goEmail.setImageResource(R.drawable.email);
        settings.setImageResource(R.drawable.settings);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initImg();
    }

    public void callMap(LatLng latLng, int zoom){
        mapFragment.moveLocal(latLng, zoom);
    }

    public void movePager(String title){
        mapFragment.movePagerPage(title);
    }

}
