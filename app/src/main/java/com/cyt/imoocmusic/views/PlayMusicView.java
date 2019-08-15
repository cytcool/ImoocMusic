package com.cyt.imoocmusic.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
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
import com.cyt.imoocmusic.models.MusicModel;
import com.cyt.imoocmusic.services.MusicService;


public class PlayMusicView extends FrameLayout {

    private Intent mServiceIntent;
    private MusicService.MusicBinder mMusicBind;
    private MusicModel mMusicModel;

    private Context mContext;
    private View mView;
    private ImageView mIvIcon,mIvNeedle,mIvPlay;
    private FrameLayout mFlPlayMusic;


    private Boolean isPlaying,isBindService=false;

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

    }

    /**
     * 切换播放状态
     */
    private void trigger(){
        if (isPlaying){
            stopMusic();
        }else {
            playMusic();
        }
    }

    /**
     * 播放音乐
     */
    public void playMusic(){
        isPlaying = true;
        mIvPlay.setVisibility(GONE);
        mFlPlayMusic.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);

        startMusicService();

    }

    /**
     * 停止播放
     */
    public void stopMusic(){
        isPlaying = false;
        mIvPlay.setVisibility(VISIBLE);
        mFlPlayMusic.clearAnimation();
        mIvNeedle.startAnimation(mStopNeddleAnim);

        if (mMusicBind!=null)
            mMusicBind.stopMusic();
    }

    /**
     * 设置光盘中显示的音乐封面图片
     * @param
     */
    public void  setMusicIcon(){
        Glide.with(mContext)
                .load(mMusicModel.getPoster())
                .into(mIvIcon);
    }

    public void setMusic(MusicModel musicModel){
        mMusicModel = musicModel;
        setMusicIcon();
    }

    /**
     * 启动音乐服务
     */
    private void startMusicService(){
        // 启动Service
        if (mServiceIntent == null){
            mServiceIntent = new Intent(mContext, MusicService.class);
            mContext.startService(mServiceIntent);
        }else {
            mMusicBind.playMusic();
        }

        // 绑定Service
        if (!isBindService){
            isBindService = true;
            mContext.bindService(mServiceIntent,conn,Context.BIND_AUTO_CREATE);
        }


    }

    /**
     * 解除绑定
     */
    public void destroy(){
        if (isBindService){
            isBindService = false;
            mContext.unbindService(conn);
        }
    }

    ServiceConnection conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mMusicBind = (MusicService.MusicBinder) iBinder;
                mMusicBind.setMusic(mMusicModel);
                mMusicBind.playMusic();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
    };
}
