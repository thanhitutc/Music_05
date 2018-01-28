package com.framgia.music5.screen.detailalbum;

import com.framgia.music5.BasePresenter;
import com.framgia.music5.BaseView;
import com.framgia.music5.data.model.Song;
import java.util.List;

/**
 * Created by MyPC on 29/01/2018.
 */

public interface ContractDetaiAlbum {
    /**
     * Interface View DetailAlbum
     */
    interface DetailAlbumView extends BaseView<DetailAlbumPresenter> {

        void showLoadListDetailSong(List<Song> songs);

        void showLoadNoSong();

        void showDeleteSongSuccess(Song song);

        void showDeleteSongFail();

        void showAddSongFavoriteSuccess();

        void showAddSongFavoriteFail();
    }

    /**
     * Interface Presenter DetailAlbum
     */
    interface DetailAlbumPresenter extends BasePresenter<DetailAlbumView> {

        void loadListSongDetail(int idAlbum);

        void deleteSongOfAlbum(Song song);

        void addSongToFavorite(Song song);
    }
}
