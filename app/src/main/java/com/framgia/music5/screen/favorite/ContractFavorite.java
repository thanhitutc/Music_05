package com.framgia.music5.screen.favorite;

import com.framgia.music5.BasePresenter;
import com.framgia.music5.BaseView;
import com.framgia.music5.data.model.Song;
import java.util.List;

/**
 * Created by MyPC on 26/01/2018.
 */

public interface ContractFavorite {
    /**
     * Interface View Favorte
     */
    interface FavoriteView extends BaseView<FavoritePresenter> {

        void showListSong(List<Song> songs);

        void showNoSong();

        void showDeleteSongSuccess(Song song);

        void showDeleteSongFail();
    }

    /**
     * Interface presenter Favorite
     */
    interface FavoritePresenter extends BasePresenter<FavoriteView> {

        void loadFavoriteSongs();

        void deleteSongFavorite(Song song);
    }
}
