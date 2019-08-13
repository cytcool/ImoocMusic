package com.cyt.imoocmusic.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cyt.imoocmusic.R;
import com.cyt.imoocmusic.views.InputView;

public class LoginActivity extends BaseActivity {

    private InputView mInputPhone;
    private InputView mInputPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView(){
        initNavBar(false,"登录",false);

        mInputPhone = fd(R.id.input_phone);
        mInputPassword = fd(R.id.input_password);


    }

    /**
     * 跳转注册页面点击事件
     * @param view
     */
    public void onRegisterClick(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 登录点击事件
     * @param view
     */
    public void onCommitClick(View view) {

        String phone = mInputPhone.getInputStr();
        String password = mInputPassword.getInputStr();

//        // 验证用户输入是否合法
//        if (!UserUtils.validateLogin(this,phone,password)){
//            return;
//        }

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
