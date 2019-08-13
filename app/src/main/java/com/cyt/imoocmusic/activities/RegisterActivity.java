package com.cyt.imoocmusic.activities;


import android.os.Bundle;
import android.view.View;

import com.cyt.imoocmusic.R;

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        initNavBar(true,"注册",false);
    }

    public void onToRegisterClick(View view) {

    }
}
