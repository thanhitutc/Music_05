package com.framgia.music5.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.framgia.music5.R;
import com.framgia.music5.screen.album.AlbumFragment;
import com.framgia.music5.screen.allsong.AllSongFragment;
import com.framgia.music5.screen.favorite.FavoriteFragment;

import static com.framgia.music5.adapter.TabType.ALBUM_FRAGMENT;
import static com.framgia.music5.adapter.TabType.ALL_SONG_FRAGMENT;
import static com.framgia.music5.adapter.TabType.FAVORITE_FRAGMENT;

public class TabFragmentAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public static final int COUNT_TAB = 3;

    public TabFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case ALL_SONG_FRAGMENT:
                return new AllSongFragment();

            case ALBUM_FRAGMENT:
                return new AlbumFragment();

            case FAVORITE_FRAGMENT:
                return new FavoriteFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return COUNT_TAB;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case ALL_SONG_FRAGMENT:
                return mContext.getResources().getString(R.string.title_all_song);

            case ALBUM_FRAGMENT:
                return mContext.getResources().getString(R.string.title_album);

            case FAVORITE_FRAGMENT:
                return mContext.getResources().getString(R.string.title_favorite);

            default:
                return null;
        }
    }
}
