package com.cyt.imoocmusic.activities;


import android.os.Bundle;
import android.view.View;

import com.cyt.imoocmusic.R;
import com.cyt.imoocmusic.utils.UserUtils;
import com.cyt.imoocmusic.views.InputView;

public class RegisterActivity extends BaseActivity {

    private InputView mInputPhone,mInputPassword,mInputPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        initNavBar(true,"注册",false);
        mInputPhone = fd(R.id.input_phone);
        mInputPassword = fd(R.id.input_password);
        mInputPasswordConfirm = fd(R.id.input_password_confirm);
    }

    /**
     * 注册按钮点击事件
     * @param view
     */
    public void onRegisterClick(View view) {
        String phone = mInputPhone.getInputStr();
        String password = mInputPassword.getInputStr();
        String passwordConfirm = mInputPasswordConfirm.getInputStr();

        boolean result = UserUtils.registerUser(this,phone,password,passwordConfirm);
        if (!result){
            return;
        }
        onBackPressed();
    }
}
