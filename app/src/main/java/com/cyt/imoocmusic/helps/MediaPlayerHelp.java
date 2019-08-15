package com.cyt.imoocmusic.helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * 播放音乐的方式
 * 1、直接在Activity中创建播放音乐，音乐与Activity绑定
 * 2、通过全局单例类与Application绑定
 * 3、通过Service进行音乐播放
 */
public class MediaPlayerHelp {

    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private String mPath;

    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;

    private static MediaPlayerHelp instance;

    public void setOnMediaPlayerHelperListener(OnMediaPlayerHelperListener onMediaPlayerHelperListener) {
        this.onMediaPlayerHelperListener = onMediaPlayerHelperListener;
    }

    public static MediaPlayerHelp getInstance(Context context) {
        if (instance == null) {
            synchronized (MediaPlayerHelp.class) {
                if (instance == null) {
                    instance = new MediaPlayerHelp(context);
                }
            }
        }
        return instance;
    }

    private MediaPlayerHelp(Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();
    }

    /**
     * 当前需要播放的音乐
     * @param path
     */
    public void setPath(String path){
        /**
         * 1、音乐正在播放，充值音乐播放状态
         * 2、设置播放音乐的路径
         * 3、准备播放
         */


        // 1、音乐正在播放，充值音乐播放状态
        if (mMediaPlayer.isPlaying() || !path.equals(mPath)){
            mMediaPlayer.reset();
        }

        mPath = path;
        // 2、设置播放音乐的路径
        try {
            mMediaPlayer.setDataSource(mContext, Uri.parse(mPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3、准备播放
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (onMediaPlayerHelperListener != null){
                    onMediaPlayerHelperListener.onPrepared(mediaPlayer);
                }
            }
        });


    }

    /**
     * 返回正在播放的音乐路径
     * @return
     */
    public String getPath(){
        return mPath;
    }

    /**
     * 播放音乐
     */
    public void start(){
        if (mMediaPlayer.isPlaying()) return;
        mMediaPlayer.start();
    }

    /**
     * 暂停播放
     */
    public void pause(){
        mMediaPlayer.pause();
    }

    public interface OnMediaPlayerHelperListener{
        void onPrepared(MediaPlayer mp);
    }
}
