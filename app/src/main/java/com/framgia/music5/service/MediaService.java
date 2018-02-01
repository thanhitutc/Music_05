package com.framgia.music5.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import com.framgia.music5.R;
import com.framgia.music5.data.local.SettingLocalDataSource;
import com.framgia.music5.data.model.Setting;
import com.framgia.music5.data.model.Song;
import com.framgia.music5.data.repository.SettingRepository;
import com.framgia.music5.screen.playmusic.PlayMusicActivity;
import com.framgia.music5.ultils.Constant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.framgia.music5.service.NotificationType.REQUEST_CODE_CLEAR;
import static com.framgia.music5.service.NotificationType.REQUEST_CODE_NEXT;
import static com.framgia.music5.service.NotificationType.REQUEST_CODE_PAUSE;
import static com.framgia.music5.service.NotificationType.REQUEST_CODE_PREVIOUS;

/**
 * Created by MyPC on 30/01/2018.
 */

public class MediaService extends Service implements BaseMediaPlayer {
    private static final String ACTION_CHANGE_MEDIA_NEXT = "ACTION_CHANGE_MEDIA_NEXT";
    private static final String ACTION_CHANGE_MEDIA_PREVIOUS = "ACTION_CHANGE_MEDIA_PREVIOUS";
    private static final String ACTION_CHANGE_MEDIA_STATE = "ACTION_CHANGE_MEDIA_STATE";
    private static final String ACTION_MEDIA_CLEAR = "ACTION_MEDIA_CLEAR";
    private static final int DEFAULT_POSITION_START = 0;
    private static final int ID_NOTIFICATION = 183;
    private List<Song> mSongs;
    private MediaPlayer mMediaPlayer;
    private int mPosition;
    private RemoteViews mRemoteViews;
    private Notification mNotification;
    private Intent mIntentBroadcast;
    private Setting mSetting;
    private SettingRepository mSettingRepository;

    public static Intent getInstance(Context context, List<Song> songs, int position) {
        Intent intent = new Intent(context, MediaService.class);
        intent.setAction(Constant.ConstantIntent.ACTION_INIT_SONG_SERVICE);
        intent.putParcelableArrayListExtra(Constant.ConstantIntent.EXTRA_INIT_SONG_SERVICE,
                (ArrayList<? extends Parcelable>) songs);
        intent.putExtra(Constant.ConstantIntent.EXTRA_INIT_POSITION_SONG_SERVICE, position);
        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaPlayer();
        initSettingService();
        mIntentBroadcast = new Intent();
        mIntentBroadcast.setAction(Constant.ConstantBroadcast.ACTION_STATE_MEDIA);
    }

    private void initSettingService() {
        mSettingRepository =
                SettingRepository.getInstance(SettingLocalDataSource.getInstance(this));
        mSetting = mSettingRepository.getSetting();
    }

    @Override
    public void onDestroy() {
        release();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action == null) {
            return START_STICKY;
        }
        switch (action) {
            case Constant.ConstantIntent.ACTION_INIT_SONG_SERVICE:
                List<Song> songs = intent.getParcelableArrayListExtra(
                        Constant.ConstantIntent.EXTRA_INIT_SONG_SERVICE);
                int position =
                        intent.getIntExtra(Constant.ConstantIntent.EXTRA_INIT_POSITION_SONG_SERVICE,
                                -1);
                if (position != -1 && songs != null) {
                    mSongs = songs;
                    mPosition = position;
                    play(position);
                }
                break;
            case ACTION_CHANGE_MEDIA_PREVIOUS:

                break;
            case ACTION_CHANGE_MEDIA_STATE:
                if (isPlay()) {
                    pause();
                } else {
                    resume();
                }
                break;
            case ACTION_CHANGE_MEDIA_NEXT:
                next();
                break;
            case ACTION_MEDIA_CLEAR:
                stopSelf();
                stopForeground(true);
                stop();
                break;
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MediaBinder();
    }

    @Override
    public void start() {
        mMediaPlayer.start();
        mIntentBroadcast.putExtra(Constant.ConstantBroadcast.EXTRA_STATE_MEDIA, true);
        sendBroadcast(mIntentBroadcast);
    }

    @Override
    public void play(int position) {
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(mSongs.get(position).getData());
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Logger.getLogger(e.toString());
        }
    }

    @Override
    public void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mIntentBroadcast.putExtra(Constant.ConstantBroadcast.EXTRA_STATE_MEDIA, false);
            sendBroadcast(mIntentBroadcast);
        }
        updateNotification();
    }

    @Override
    public void resume() {
        if (isPlay()) {
            return;
        }
        start();
        updateNotification();
    }

    @Override
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mIntentBroadcast.putExtra(Constant.ConstantBroadcast.EXTRA_STATE_MEDIA, false);
            sendBroadcast(mIntentBroadcast);
        }
        updateNotification();
    }

    @Override
    public void release() {
        stop();
        mMediaPlayer.release();
    }

    @Override
    public void seekTo(int index) {
        mMediaPlayer.seekTo(index);
    }

    @Override
    public void next() {
        if (mPosition < mSongs.size() - 1) {
            mPosition++;
        } else {
            mPosition = DEFAULT_POSITION_START;
        }
        play(mPosition);
    }

    @Override
    public void previous() {
        if (mPosition > DEFAULT_POSITION_START) {
            mPosition--;
        } else {
            mPosition = DEFAULT_POSITION_START;
        }
        play(mPosition);
    }

    @Override
    public long getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public long getCurrentDuration() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public boolean isPlay() {
        return mMediaPlayer.isPlaying();
    }

    public boolean isShuff() {
        return mSetting.isShuffleMode();
    }

    public void setShuff(boolean shuff) {
        mSetting.setShuffleMode(shuff);
        mSettingRepository.saveSetting(mSetting);
    }

    public int getRepeat() {
        return mSetting.getRepeatMode();
    }

    public void setRepeat(int repeat) {
        mSetting.setRepeatMode(repeat);
        mSettingRepository.saveSetting(mSetting);
    }

    private void initMediaPlayer() {
        mPosition = -1;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(mOnPrepare);
        mMediaPlayer.setOnCompletionListener(mOnComple);
    }

    private void checkRepeatMode() {
        switch (mSetting.getRepeatMode()) {
            case RepeatType.NO_REPEAT:
                if (mPosition < mSongs.size() - 1) {
                    mPosition++;
                    play(mPosition);
                } else {
                    mPosition = DEFAULT_POSITION_START;
                    seekTo(DEFAULT_POSITION_START);
                    stop();
                }
                break;
            case RepeatType.REPEAT_ONE:
                play(mPosition);
                break;
            case RepeatType.REPEAT_ALL:
                if (mPosition == mSongs.size() - 1) {
                    mPosition = DEFAULT_POSITION_START;
                } else {
                    mPosition++;
                }
                play(mPosition);
                break;
        }
    }

    private MediaPlayer.OnPreparedListener mOnPrepare = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            start();
            createNotification(mSongs.get(mPosition).getTitle());
        }
    };

    private MediaPlayer.OnCompletionListener mOnComple = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            checkRepeatMode();
        }
    };

    public String getTitleSongPlaying() {
        return mSongs.get(mPosition).getTitle();
    }

    private void createNotification(String title) {
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        mRemoteViews.setTextViewText(R.id.text_title_song_notification, title);

        Intent intentActionNext = new Intent();
        intentActionNext.setAction(ACTION_CHANGE_MEDIA_NEXT);
        intentActionNext.setClass(getApplicationContext(), MediaService.class);
        PendingIntent peServiceNext =
                PendingIntent.getService(getApplicationContext(), REQUEST_CODE_NEXT,
                        intentActionNext, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.image_next, peServiceNext);

        Intent intentActionPrev = new Intent();
        intentActionPrev.setAction(ACTION_CHANGE_MEDIA_PREVIOUS);
        intentActionPrev.setClass(getApplicationContext(), MediaService.class);
        PendingIntent peServicePre =
                PendingIntent.getService(getApplicationContext(), REQUEST_CODE_PREVIOUS,
                        intentActionPrev, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.image_pre, peServicePre);

        Intent intentActionPause = new Intent();
        intentActionPause.setAction(ACTION_CHANGE_MEDIA_STATE);
        intentActionPause.setClass(getApplicationContext(), MediaService.class);
        PendingIntent peServicePause =
                PendingIntent.getService(getApplicationContext(), REQUEST_CODE_PAUSE,
                        intentActionPause, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.image_pause, peServicePause);

        Intent intentActionClear = new Intent();
        intentActionClear.setAction(ACTION_MEDIA_CLEAR);
        intentActionClear.setClass(this, MediaService.class);
        PendingIntent peServiceClear =
                PendingIntent.getService(this, REQUEST_CODE_CLEAR, intentActionClear,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.image_cancel, peServiceClear);

        Intent intent = new Intent(this, PlayMusicActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivities(this, (int) System.currentTimeMillis(),
                        new Intent[] { intent }, 0);
        Notification.Builder notificationBuilder =
                new Notification.Builder(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotification = notificationBuilder.setSmallIcon(R.drawable.ic_song)
                    .setContentIntent(pendingIntent)
                    .setContent(mRemoteViews)
                    .setDefaults(Notification.FLAG_NO_CLEAR)
                    .build();
        }
        startForeground(ID_NOTIFICATION, mNotification);
    }

    private void updateNotification() {
        if (isPlay()) {
            mRemoteViews.setImageViewResource(R.id.image_pause, R.drawable.ic_pause);
        } else {
            mRemoteViews.setImageViewResource(R.id.image_pause, R.drawable.ic_play);
        }
        startForeground(ID_NOTIFICATION, mNotification);
    }

    /**
     * Media Binder
     */
    public class MediaBinder extends Binder {
        public MediaService getMediaService() {
            return MediaService.this;
        }
    }
}
