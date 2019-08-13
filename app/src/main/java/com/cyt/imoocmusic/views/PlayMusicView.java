package com.cyt.imoocmusic.views;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.cyt.imoocmusic.R;
import com.cyt.imoocmusic.helps.MediaPlayerHelp;


public class PlayMusicView extends FrameLayout {

    private Context mContext;
    private View mView;
    private ImageView mIvIcon,mIvNeedle,mIvPlay;
    private FrameLayout mFlPlayMusic;
    private MediaPlayerHelp mMediaPlayerHelp;

    private String mPath;

    private Boolean isPlaying;

    private Animation mPlayMusicAnim,mPlayNeedleAnim,mStopNeddleAnim;



    public PlayMusicView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        mContext = context;

        mView = LayoutInflater.from(context).inflate(R.layout.play_music,this,false);
        mIvIcon = mView.findViewById(R.id.iv_icon);
        mFlPlayMusic = mView.findViewById(R.id.fl_play_music);
        mFlPlayMusic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                trigger();
            }
        });
        mIvNeedle = mView.findViewById(R.id.iv_needle);
        mIvPlay = mView.findViewById(R.id.iv_play);

        mPlayMusicAnim = AnimationUtils.loadAnimation(mContext,R.anim.play_music_anim);
        mPlayNeedleAnim = AnimationUtils.loadAnimation(mContext,R.anim.play_needle_anim);
        mStopNeddleAnim = AnimationUtils.loadAnimation(mContext,R.anim.stop_needle_anim);

        addView(mView);

        mMediaPlayerHelp = MediaPlayerHelp.getInstance(mContext);
    }

    /**
     * 切换播放状态
     */
    private void trigger(){
        if (isPlaying){
            stopMusic();
        }else {
            playMusic(mPath);
        }
    }

    /**
     * 播放音乐
     */
    public void playMusic(String path){
        mPath = path;
        isPlaying = true;
        mIvPlay.setVisibility(GONE);
        mFlPlayMusic.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);

        /**
         * 1、判断当前音乐是否是已经在播放的音乐
         * 2、如果当前音乐是已经在播放的音乐，那么直接执行start()方法
         * 3、如果当前音乐不是需要播放的音乐，那么调用setPath()方法
         */

        if (mMediaPlayerHelp.getPath() != null
                && mMediaPlayerHelp.getPath().equals(path)){
            mMediaPlayerHelp.start();
        }else {
            mMediaPlayerHelp.setPath(path);
            mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayerHelp.start();
                }
            });
        }
    }

    /**
     * 停止播放
     */
    public void stopMusic(){
        isPlaying = false;
        mIvPlay.setVisibility(VISIBLE);
        mFlPlayMusic.clearAnimation();
        mIvNeedle.startAnimation(mStopNeddleAnim);

        mMediaPlayerHelp.pause();
    }

    /**
     * 设置光盘中显示的音乐封面图片
     * @param icon
     */
    public void  setMusicIcon(String icon){
        Glide.with(mContext)
                .load(icon)
                .into(mIvIcon);
    }
}