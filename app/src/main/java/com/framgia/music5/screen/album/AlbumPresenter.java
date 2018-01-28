package com.framgia.music5.screen.album;

import com.framgia.music5.data.model.Album;
import com.framgia.music5.data.repository.AlbumRepository;
import com.framgia.music5.data.repository.SongInAlbumRepository;
import java.util.List;

/**
 * Created by MyPC on 28/01/2018.
 */

public class AlbumPresenter implements ContractAlbum.AlbumPresenter {
    private ContractAlbum.AlbumView mAlbumView;
    private AlbumRepository mAlbumRepository;
    private SongInAlbumRepository mSongInAlbumRepository;

    public AlbumPresenter(AlbumRepository albumRepository,
            SongInAlbumRepository songInAlbumRepository) {
        mAlbumRepository = albumRepository;
        mSongInAlbumRepository = songInAlbumRepository;
    }

    @Override
    public void setView(ContractAlbum.AlbumView view) {
        mAlbumView = view;
    }

    @Override
    public void onStart() {
        mAlbumView.setPresenter(this);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadListAlbum() {
        List<Album> albums = mAlbumRepository.getListAlbum();
        if (albums != null) {
            mAlbumView.showListAlbum(albums);
        } else {
            mAlbumView.showNoListAlbum();
        }
    }

    @Override
    public void addNewAlbum(String nameAlbum) {
        boolean isSuccess = mAlbumRepository.insertAlbum(nameAlbum);
        if (isSuccess) {
            Album album = new Album(mAlbumRepository.getLastIdInsert(), nameAlbum);
            mAlbumView.showNewAlbumSuccess(album);
        } else {
            mAlbumView.showNewAlbumFail();
        }
    }

    @Override
    public void deleteAlbum(Album album) {
        boolean isSuccessfully = mSongInAlbumRepository.deleteAllSongOfAlbum(album.getId());
        if (isSuccessfully) {
            boolean isSuccess = mAlbumRepository.deleteAlbum(album.getId());
            if (isSuccess) {
                mAlbumView.showDeleteAlbumSuccess(album);
            } else {
                mAlbumView.showDeleteAlbumFail();
            }
        }
    }

    @Override
    public void updateNameAlbum(Album album, String nameAlbum) {
        boolean isSuccess = mAlbumRepository.updateName(album.getId(), nameAlbum);
        if (isSuccess) {
            mAlbumView.showUpdateNameSuccess(album, nameAlbum);
        } else {
            mAlbumView.showUpdateNameFail();
        }
    }
}
