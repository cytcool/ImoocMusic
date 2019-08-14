package com.cyt.imoocmusic.helps;

import android.content.Context;

import com.cyt.imoocmusic.migration.Migration;
import com.cyt.imoocmusic.models.AlbumModel;
import com.cyt.imoocmusic.models.MusicModel;
import com.cyt.imoocmusic.models.MusicSourceModel;
import com.cyt.imoocmusic.models.UserModel;
import com.cyt.imoocmusic.utils.DataUtils;

import java.io.FileNotFoundException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelp {

    private Realm mRealm;

    public RealmHelp(){
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * Realm数据库发生结果变化时，需要对数据库进行数据迁移
     */

    /**
     * 告诉Realm数据需要迁移，并且为Realm设置最新的配置
     */
    public static void migration(){
        RealmConfiguration conf = getRealmConf();

        // 为Realm设置最新的配置
        Realm.setDefaultConfiguration(conf);
        // 告诉Realm数据需要迁移
        try {
            Realm.migrateRealm(conf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回 RealmConfiguration
     */
    private static RealmConfiguration getRealmConf(){
        return new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new Migration())
                .build();
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

    /**
     * 保存音乐源数据
     */
    public void saveMusicSource(Context context){

        String musicSourceJson = DataUtils.getJsonFromAssets(context,"DataSource.json");
        mRealm.beginTransaction();
        mRealm.createObjectFromJson(MusicSourceModel.class,musicSourceJson);
        mRealm.commitTransaction();
    }

    /**
     * 删除音乐源数据
     */
    public void removeMusicSource(){
        mRealm.beginTransaction();
        mRealm.delete(MusicSourceModel.class);
        mRealm.delete(MusicModel.class);
        mRealm.delete(AlbumModel.class);
        mRealm.commitTransaction();
    }
}
