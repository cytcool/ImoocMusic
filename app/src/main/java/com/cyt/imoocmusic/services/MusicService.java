package com.cyt.imoocmusic.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.cyt.imoocmusic.R;
import com.cyt.imoocmusic.activities.WelcomeActivity;
import com.cyt.imoocmusic.helps.MediaPlayerHelp;
import com.cyt.imoocmusic.models.MusicModel;

public class MusicService extends Service {

    public static final int NOTIFICATION_ID = 1;
    public static final String CHANNEL_ID = "MusicService";

    private MediaPlayerHelp mMediaPlayerHelp;
    private MusicModel mMusicModel;

    public MusicService() {
    }

    public class MusicBinder extends Binder{

        /**
         * 设置音乐
         */
        public void setMusic(MusicModel musicModel){
            mMusicModel = musicModel;
            startForeground();
        }

        /**
         * 播放音乐
         */
        public void playMusic(){

            if (mMediaPlayerHelp.getPath() != null
                    && mMediaPlayerHelp.getPath().equals(mMusicModel.getPath())){
                mMediaPlayerHelp.start();
            }else {
                mMediaPlayerHelp.setPath(mMusicModel.getPath());
                mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayerHelp.start();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopSelf();
                    }
                });
            }
        }

        /**
         * 暂停播放
         */
        public void stopMusic(){
            mMediaPlayerHelp.pause();
        }


    }

    @Override
    public IBinder onBind(Intent intent) {
       return new MusicBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mMediaPlayerHelp = MediaPlayerHelp.getInstance(this);

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"主服务",NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);//设置提示灯
            channel.setLightColor(Color.RED);
            channel.setShowBadge(true);//显示logo
            channel.setDescription("Music");//设置描述
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC); //设置锁屏可见 VISIBILITY_PUBLIC=可见
            manager.createNotificationChannel(channel);
        }
    }

    /**
     * 设置服务在前台可见
     */
    private void startForeground(){

        // 通知栏点击跳转的Intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, new Intent(this,WelcomeActivity.class),PendingIntent.FLAG_CANCEL_CURRENT);

        // 创建Notification
        Notification notification = new Notification.Builder(this)
                .setChannelId(CHANNEL_ID)
                .setContentTitle(mMusicModel.getName())
                .setContentText(mMusicModel.getAuthor())
                .setSmallIcon(R.mipmap.logo)
                .setContentIntent(pendingIntent)
                .build();

        // 设置notification在前台展示
        startForeground(NOTIFICATION_ID,notification);
    }
}
