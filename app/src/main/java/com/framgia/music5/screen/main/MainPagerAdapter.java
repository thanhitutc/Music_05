package com.framgia.music5.screen.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.framgia.music5.R;
import com.framgia.music5.screen.album.AlbumFragment;
import com.framgia.music5.screen.allsong.AllSongFragment;
import com.framgia.music5.screen.favorite.FavoriteFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public static final int COUNT_TAB = 3;

    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case MainType.ALL_SONG:
                return new AllSongFragment();

            case MainType.ALBUM:
                return new AlbumFragment();

            case MainType.FAVORITE:
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
            case MainType.ALL_SONG:
                return mContext.getResources().getString(R.string.title_all_song);

            case MainType.ALBUM:
                return mContext.getResources().getString(R.string.title_album);

            case MainType.FAVORITE:
                return mContext.getResources().getString(R.string.title_favorite);

            default:
                return null;
        }
    }
}
