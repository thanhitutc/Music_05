package com.framgia.music5.screen.addsongtoalbum;

import com.framgia.music5.BasePresenter;
import com.framgia.music5.BaseView;
import com.framgia.music5.data.model.Album;
import java.util.List;

/**
 * Created by MyPC on 26/01/2018.
 */

public interface ContractSongAddToAlbum {
    /**
     * Interface song add to album View
     */
    interface SongAddToAlbumView extends BaseView<SongAddToAlbumPresenter> {

        void showListAlbum(List<Album> albums);

        void showNoListAlbum();

        void showAddSongSuccess();

        void showAddSongFail();
    }

    /**
     * Interface presenter song add to album presenter
     */
    interface SongAddToAlbumPresenter extends BasePresenter<SongAddToAlbumView> {

        void loadListSong();

        void addNewAlbum(String nameAlbum);
    }
}
