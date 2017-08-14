package com.example.administrator.forimm5.Main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.forimm5.Map.MapListFragment;
import com.example.administrator.forimm5.R;
import com.google.android.gms.maps.model.LatLng;

/**
 * 이 액티비티 하나에서 모든 프래그먼트가 동작한다.
 */
public class MainActivity extends AppCompatActivity implements MapListFragment.CallMapListBack {

    MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFragment = new MainFragment();
        callFragment(mainFragment);
    }

    /**
     * 메인 프래그먼트 띄우는 함수
     */
    public void callFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void callMap(LatLng latLng, int zoom) {
        mainFragment.callContainer(latLng, 14);
    }

    @Override
    public void movePager(String title) {
        mainFragment.movePager(title);
    }

    public void callEmail(){
        mainFragment.callEmail();
    }

    public void setLawContent(String law){
        mainFragment.setLawContent(law);
        Log.e("MainActivity", law);
    }

    public void setCenterByCurPos(){
        mainFragment.setCenterByCurPos();
    }

}
