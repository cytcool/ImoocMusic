package com.cyt.imoocmusic.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.cyt.imoocmusic.R;
import com.cyt.imoocmusic.activities.LoginActivity;


/**
 * Created by cyt on 2019/8/5.
 *
 */

public class UserUtils {

    /**
     * 验证登录用户输入的合法性
     * @param context 上下文
     * @param phone 用户名/手机号
     * @param password 密码
     * @return
     */
    public static boolean validateLogin(Context context,String phone,String password){
        if (!RegexUtils.isMobileExact(phone)){
            Toast.makeText(context,"无效手机号",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(context,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 退出登录
     * @param context
     */
    public static void logout(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        // 添加Intent标志符，清理task栈，新生成一个task栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        // 定义Activity跳转动画
        ((Activity) context).overridePendingTransition(R.anim.open_enter,R.anim.open_exit);
    }
}
