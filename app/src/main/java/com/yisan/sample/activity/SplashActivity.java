package com.yisan.sample.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;
import android.widget.VideoView;

import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.BaseActivity;
import com.yisan.sample.R;
import com.yisan.sample.mvp.presenter.SplashActivityTimerPresenter;
import com.yisan.sample.mvp.view.ISplashActivityContract;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author：wzh
 * @description: 闪屏页
 * @packageName: com.yisan.sample.activity
 * @date：2019/11/24 上午 11:15
 */
@ViewLayoutInject(value = R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements ISplashActivityContract.IView {

    private static final String TAG = "wzh_SplashActivity";

    @BindView(R.id.video_view)
    VideoView mVideoView;
    @BindView(R.id.tv_jump_to_main)
    TextView mTvJumpToMain;

    /**
     * 视频播放
     */
    private MediaPlayer mMediaPlayer;

    @Override
    protected void afterBindView() {

        initVideoView();
        initListener();
        initTimerPresenter();

    }

    private void initTimerPresenter() {
        ISplashActivityContract.IPresenter timerPresenter = new SplashActivityTimerPresenter(this);
        timerPresenter.initTimer();
    }


    private void initVideoView() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash);
        mVideoView.setVideoURI(uri);
    }

    private void initListener() {

        mVideoView.setOnPreparedListener(mediaPlayer -> {
            if (mMediaPlayer == null) {
                mMediaPlayer = mediaPlayer;
            }
            mediaPlayer.start();
        });

        mVideoView.setOnCompletionListener(mediaPlayer -> {
            if (mMediaPlayer == null) {
                mMediaPlayer = mediaPlayer;
            }
            mediaPlayer.start();
        });


        mVideoView.setOnErrorListener((mediaPlayer, what, extra) -> {

            if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                Log.v(TAG, "Media Error,Server Died" + extra);
            } else if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
                Log.v(TAG, "Media Error,Error Unknown " + extra);
            }
            return true;
        });
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (mVideoView != null) {
            //释放资源
            mVideoView.suspend();
        }
    }


    @OnClick(R.id.tv_jump_to_main)
    public void onViewClicked() {
        MainActivity.show(this);
        finish();
    }


    @Override
    public void setTvTimer(String str) {
        mTvJumpToMain.setText(str);
    }
}
