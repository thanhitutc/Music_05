package com.framgia.music5.screen.allsong;

import com.framgia.music5.BasePresenter;
import com.framgia.music5.BaseView;
import com.framgia.music5.data.model.Song;
import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public interface AllSongContract {

    /**
     * interface song view
     **/
    interface SongView extends BaseView<Presenter> {

        void showListSong(List<Song> songs);

        void showNoSong();

        void showDeleteSuccess(Song song);

        void showDeleteError();

        void showAddFavoriteSuccess();

        void showAddFavoriteError();
    }

    /**
     * Presenter all song
     **/
    interface Presenter extends BasePresenter<SongView> {

        void loadListSong();

        void addToFavorite(Song song);

        void deleteSong(Song song);
    }
}
