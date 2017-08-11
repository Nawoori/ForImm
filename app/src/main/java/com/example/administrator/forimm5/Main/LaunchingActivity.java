package com.example.administrator.forimm5.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.administrator.forimm5.R;

public class LaunchingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindow();
        setContentView(R.layout.activity_launcher);
        splash();
    }

    /**
     * 런쳐 액티비티 Full Screen 설정
     */
    private void setWindow() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * 3초 지난 후 MainActivity 로 넘어감
     */
    private void splash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LaunchingActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }
}
