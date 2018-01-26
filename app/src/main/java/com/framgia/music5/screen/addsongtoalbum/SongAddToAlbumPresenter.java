package com.framgia.music5.screen.addsongtoalbum;

import com.framgia.music5.data.model.Album;
import com.framgia.music5.data.repository.AlbumRepository;
import com.framgia.music5.data.repository.SongInAlbumRepository;
import java.util.List;

/**
 * Created by MyPC on 26/01/2018.
 */

public class SongAddToAlbumPresenter implements ContractSongAddToAlbum.SongAddToAlbumPresenter {
    private AlbumRepository mAlbumRepository;
    private SongInAlbumRepository mSongInAlbumRepository;
    private ContractSongAddToAlbum.SongAddToAlbumView mSongAddToAlbumView;
    private String mIdSong;

    public SongAddToAlbumPresenter(AlbumRepository repository,
            SongInAlbumRepository songInAlbumRepository, String idSong) {
        mAlbumRepository = repository;
        mSongInAlbumRepository = songInAlbumRepository;
        mIdSong = idSong;
    }

    @Override
    public void loadListSong() {
        List<Album> albums = mAlbumRepository.getListAlbum();
        if (albums != null) {
            mSongAddToAlbumView.showListAlbum(albums);
        } else {
            mSongAddToAlbumView.showNoListAlbum();
        }
    }

    @Override
    public void addNewAlbum(String nameAlbum) {
        
    }

    @Override
    public void setView(ContractSongAddToAlbum.SongAddToAlbumView view) {
        mSongAddToAlbumView = view;
    }

    @Override
    public void onStart() {
        mSongAddToAlbumView.setPresenter(this);
    }

    @Override
    public void onStop() {

    }
}
