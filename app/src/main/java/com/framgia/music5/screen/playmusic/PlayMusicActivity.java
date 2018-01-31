package com.framgia.music5.screen.playmusic;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.framgia.music5.R;
import com.framgia.music5.service.MediaService;
import com.framgia.music5.ultils.Constant;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.framgia.music5.ultils.Constant.ConstantBroadcast.ACTION_STATE_MEDIA;

/**
 * Created by MyPC on 31/01/2018.
 */

public class PlayMusicActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int DEFAULT_DELAY = 1000;
    private static final String TIME_FORMAT = "mm:ss";
    private TextView mTextCurrentDuration;
    private TextView mTextDuration;
    private TextView mTextTitleSong;
    private SeekBar mSeekBar;
    private ImageView mButtonShuffle;
    private ImageView mButtonPrevious;
    private ImageView mButtonState;
    private ImageView mButtonNext;
    private ImageView mButtonRepeat;
    private MediaService mService;
    private Handler mHandler;
    private IntentFilter mIntentFilter;
    private BroadcastReceiver mBroadcastReceiver;

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, PlayMusicActivity.class);
        return intent;
    }

    private ServiceConnection mMediaConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MediaService.MediaBinder mediaBinder = (MediaService.MediaBinder) iBinder;
            mService = mediaBinder.getMediaService();
            update();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        initToolbar();
        initComponents();
        initListeners();
        receiveStateMedia();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, mIntentFilter);
        update();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeCallbacks();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mMediaConnection);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_shuffle:

                break;
            case R.id.image_previous:

                break;
            case R.id.image_state:
                if (mService.isPlay()) {
                    mService.pause();
                } else {
                    mService.resume();
                }
                break;
            case R.id.image_next:
                mService.next();
                break;
            case R.id.image_repeat:

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponents() {
        mTextCurrentDuration = findViewById(R.id.text_current_duration);
        mTextDuration = findViewById(R.id.text_duration);
        mTextTitleSong = findViewById(R.id.text_title_playing);
        mSeekBar = findViewById(R.id.seek_bar);
        mButtonShuffle = findViewById(R.id.image_shuffle);
        mButtonPrevious = findViewById(R.id.image_previous);
        mButtonState = findViewById(R.id.image_state);
        mButtonNext = findViewById(R.id.image_next);
        mButtonRepeat = findViewById(R.id.image_repeat);
        mHandler = new Handler();
        Intent intent = new Intent(this, MediaService.class);
        bindService(intent, mMediaConnection, BIND_AUTO_CREATE);
    }

    private void initListeners() {
        mButtonShuffle.setOnClickListener(this);
        mButtonPrevious.setOnClickListener(this);
        mButtonState.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mButtonRepeat.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(mOnSeekChange);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_play_music);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.title_toolbar_playmusic));
    }

    private String convertToTime(long duration) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        String time = sdf.format(duration);
        return time;
    }

    private void update() {
        mHandler.postDelayed(mTimeCounter, DEFAULT_DELAY);
    }

    private Runnable mTimeCounter = new Runnable() {
        @Override
        public void run() {
            if (!mTextTitleSong.getText().toString().equals(mService.getTitleSongPlaying())) {
                mTextTitleSong.setText(mService.getTitleSongPlaying());
                mTextDuration.setText(convertToTime(mService.getDuration()));
            }
            if (mService.isPlay()) {
                long currentPercent = 100 * mService.getCurrentDuration() / mService.getDuration();
                mSeekBar.setProgress((int) currentPercent);
                mTextCurrentDuration.setText(convertToTime(mService.getCurrentDuration()));
            }
            update();
        }
    };

    private void receiveStateMedia() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ACTION_STATE_MEDIA);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() == null) {
                    return;
                }
                if (intent.getAction().equals(ACTION_STATE_MEDIA)) {
                    if (intent.getBooleanExtra(Constant.ConstantBroadcast.EXTRA_STATE_MEDIA,
                            false)) {
                        mButtonState.setImageResource(R.drawable.ic_pause);
                    } else {
                        mButtonState.setImageResource(R.drawable.ic_play);
                    }
                }
            }
        };
    }

    private SeekBar.OnSeekBarChangeListener mOnSeekChange = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int percent = seekBar.getProgress();
            int currentSeekBarTo = percent * (int) mService.getDuration() / 100;
            mService.seekTo(currentSeekBarTo);
        }
    };

    private void removeCallbacks() {
        mHandler.removeCallbacks(mTimeCounter);
    }
}
