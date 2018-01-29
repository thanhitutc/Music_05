package com.framgia.music5.screen.album;

import com.framgia.music5.BasePresenter;
import com.framgia.music5.BaseView;
import com.framgia.music5.data.model.Album;
import java.util.List;

/**
 * Created by MyPC on 28/01/2018.
 */

public interface ContractAlbum {
    /**
     * Interface view album
     */
    interface AlbumView extends BaseView<AlbumPresenter> {
        void showListAlbum(List<Album> albums);

        void showNoListAlbum();

        void showNewAlbumSuccess(Album album);

        void showNewAlbumFail();

        void showDeleteAlbumSuccess(Album album);

        void showDeleteAlbumFail();

        void showUpdateNameSuccess(Album album, String newName);

        void showUpdateNameFail();
    }

    /**
     * interface presenter album
     */
    interface AlbumPresenter extends BasePresenter<AlbumView> {
        void loadListAlbum();

        void addNewAlbum(String nameAlbum);

        void deleteAlbum(Album album);

        void updateNameAlbum(Album album, String newNameAlbum);
    }
}
