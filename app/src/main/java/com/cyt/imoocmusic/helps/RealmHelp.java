package com.cyt.imoocmusic.helps;

import com.cyt.imoocmusic.models.UserModel;

import io.realm.Realm;

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
}
