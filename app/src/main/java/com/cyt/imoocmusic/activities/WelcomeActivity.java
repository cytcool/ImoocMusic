package com.cyt.imoocmusic.activities;

import android.content.Intent;
import android.os.Bundle;

import com.cyt.imoocmusic.R;
import com.cyt.imoocmusic.utils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    /**
     * 初始化
     */
    private void init(){
        final boolean isLogin = UserUtils.validateUserLogin(this);
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                toMain();
                if (isLogin){
                    toMain();
                }else {
                    toLogin();
                }
            }
        },3000);
    }

    /**
     * 跳转到MainActivity
     */
    private void toMain() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到LoginActivity
     */
    private void toLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
