package com.cyt.imoocmusic.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cyt.imoocmusic.R;
import com.cyt.imoocmusic.activities.LoginActivity;
import com.cyt.imoocmusic.helps.RealmHelp;
import com.cyt.imoocmusic.models.UserModel;

import java.util.List;


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

        if (!UserUtils.userExistFromPhone(phone)){
            Toast.makeText(context, "当前手机号未注册", Toast.LENGTH_SHORT).show();
            return false;
        }

        RealmHelp realmHelp = new RealmHelp();
        boolean result = realmHelp.validateUser(phone,EncryptUtils.encryptMD5ToString(password));
        realmHelp.close();
        if (!result){
            Toast.makeText(context, "手机号或密码不正确", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 保存用户登录标记
        boolean isSave = SPUtils.saveUser(context,phone);
        if (!isSave){
            Toast.makeText(context, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 保存用户标记，在全局单例类之中


        return true;
    }

    /**
     * 退出登录
     * @param context
     */
    public static void logout(Context context){
        boolean isRemove = SPUtils.removeUser(context);
        if (!isRemove){
            Toast.makeText(context, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(context, LoginActivity.class);
        // 添加Intent标志符，清理task栈，新生成一个task栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        // 定义Activity跳转动画
        ((Activity) context).overridePendingTransition(R.anim.open_enter,R.anim.open_exit);
    }

    /**
     * 注册用户
     * @param context
     * @param phone
     * @param password
     * @param passwordConfirm
     */
    public static boolean registerUser(Context context,String phone,String password,String passwordConfirm){
        if (!RegexUtils.isMobileExact(phone)){
            Toast.makeText(context,"无效手机号",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (StringUtils.isEmpty(password) || !password.equals(passwordConfirm)){
            Toast.makeText(context, "请确认密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 注册验证
        if (UserUtils.userExistFromPhone(phone)){
            Toast.makeText(context, "该手机号已存在", Toast.LENGTH_SHORT).show();
            return false;
        }

        UserModel userModel = new UserModel();
        userModel.setPhone(phone);
        userModel.setPassword(EncryptUtils.encryptMD5ToString(password));

        saveUser(userModel);

        return true;
    }

    /**
     * 保存用户到数据库
     * @param userModel
     */
    public static void saveUser(UserModel userModel){
        RealmHelp realmHelp = new RealmHelp();
        realmHelp.saveUser(userModel);
        realmHelp.close();
    }

    /**
     * 验证手机号是否已存在
     * @param phone
     * @return
     */
    public static boolean userExistFromPhone(String phone){
        boolean result = false;
        
        RealmHelp realmHelp = new RealmHelp();
        List<UserModel> allUser = realmHelp.getAllUser();
        for (UserModel userModel:allUser) {
            if (userModel.getPhone().equals(phone)){
                result = true;
                break;
            }
        }

        realmHelp.close();

        return result;
    }

    /**
     * 验证是否存在已登录用户
     * @param context
     * @return
     */
    public static boolean validateUserLogin(Context context){
        return SPUtils.isLoginUser(context);
    }

    /**
     * 修改密码
     */
    public static boolean changePassword(Context context,String oldPassword,String password,String passwordConfirm){
        if (TextUtils.isEmpty(oldPassword)){
            Toast.makeText(context, "请输入原密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password) || !password.equals(passwordConfirm)){
            Toast.makeText(context, "请确认密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 验证原密码是否正确
        RealmHelp realmHelp = new RealmHelp();
        UserModel userModel = realmHelp.getUser();


        if (!EncryptUtils.encryptMD5ToString(oldPassword).equals(userModel.getPassword())){
            Toast.makeText(context, "原密码输入不正确", Toast.LENGTH_SHORT).show();
            return false;
        }

        realmHelp.changePassword(EncryptUtils.encryptMD5ToString(password));

        realmHelp.close();

        return true;


    }
}
