package com.cyt.imoocmusic.helps;

import com.cyt.imoocmusic.models.UserModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelp {

    private Realm mRealm;

    public RealmHelp(){
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * 关闭数据库
     */
    public void close(){
        if (mRealm != null || !mRealm.isClosed()){
            mRealm.close();
        }
    }

    /**
     * 保存用户信息
     * @param userModel
     */
    public void saveUser(UserModel userModel){
        mRealm.beginTransaction();
        mRealm.insert(userModel);
        mRealm.commitTransaction();
    }

    /**
     * 返回所有用户
     * @return
     */
    public List<UserModel> getAllUser(){
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        RealmResults<UserModel> results = query.findAll();
        return results;
    }

    /**
     * 验证用户信息
     * @param phone 手机号
     * @param password 密码
     * @return
     */
    public boolean validateUser(String phone,String password){
        boolean result = false;
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        query = query.equalTo("phone",phone)
                .equalTo("password",password);

        UserModel userModel = query.findFirst();

        if (userModel != null){
            result = true;
        }

        return result;
    }

    /**
     * 获取当前用户
     */
    public UserModel getUser(){
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        UserModel userModel = query.equalTo("phone",UserHelper.getInstance().getPhone()).findFirst();
        return userModel;
    }

    /**
     * 修改密码
     */
    public void changePassword(String password){
        UserModel userModel = getUser();
        mRealm.beginTransaction();
        userModel.setPassword(password);
        mRealm.commitTransaction();
    }
}
