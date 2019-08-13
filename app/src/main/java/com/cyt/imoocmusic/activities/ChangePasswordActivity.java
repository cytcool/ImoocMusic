package com.cyt.imoocmusic.activities;


import android.os.Bundle;
import android.view.View;

import com.cyt.imoocmusic.R;
import com.cyt.imoocmusic.utils.UserUtils;
import com.cyt.imoocmusic.views.InputView;

public class ChangePasswordActivity extends BaseActivity {

    private InputView mOldPassword,mPassword,mPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();
    }

    private void initView() {
        initNavBar(true,"修改密码",false);
        mOldPassword = fd(R.id.input_old_password);
        mPassword = fd(R.id.input_new_password);
        mPasswordConfirm = fd(R.id.input_new_password_confirm);
    }

    public void onChangePasswordClick(View view) {
        String oldPassword = mOldPassword.getInputStr();
        String password = mPassword.getInputStr();
        String passwordConfirm = mPasswordConfirm.getInputStr();

        boolean result = UserUtils.changePassword(this,oldPassword,password,passwordConfirm);
        if (!result){
            return;
        }

        UserUtils.logout(this);
    }
}
