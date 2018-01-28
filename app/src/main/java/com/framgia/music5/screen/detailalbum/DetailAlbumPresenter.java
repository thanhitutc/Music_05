package com.framgia.music5.screen.detailalbum;

import com.framgia.music5.data.model.Song;
import com.framgia.music5.data.repository.FavoriteRepository;
import com.framgia.music5.data.repository.SongInAlbumRepository;
import java.util.List;

/**
 * Created by MyPC on 29/01/2018.
 */

public class DetailAlbumPresenter implements ContractDetaiAlbum.DetailAlbumPresenter {
    private SongInAlbumRepository mSongInAlbumRepository;
    private FavoriteRepository mFavoriteRepository;
    private ContractDetaiAlbum.DetailAlbumView mDetailAlbumView;
    private int mIdAlbum;

    public DetailAlbumPresenter(SongInAlbumRepository songInAlbumRepository,
            FavoriteRepository favoriteRepository) {
        mSongInAlbumRepository = songInAlbumRepository;
        mFavoriteRepository = favoriteRepository;
    }

    @Override
    public void setView(ContractDetaiAlbum.DetailAlbumView view) {
        mDetailAlbumView = view;
    }

    @Override
    public void onStart() {
        mDetailAlbumView.setPresenter(this);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadListSongDetail(int idAlbum) {
        mIdAlbum = idAlbum;
        List<Song> songs = mSongInAlbumRepository.getSongOfAlbum(idAlbum);
        if (songs != null) {
            mDetailAlbumView.showLoadListDetailSong(songs);
        } else {
            mDetailAlbumView.showLoadNoSong();
        }
    }

    @Override
    public void deleteSongOfAlbum(Song song) {
        boolean isSuccess = mSongInAlbumRepository.deleteSongInAlbum(song.getId(), mIdAlbum);
        if (isSuccess) {
            mDetailAlbumView.showDeleteSongSuccess(song);
        } else {
            mDetailAlbumView.showDeleteSongFail();
        }
    }

    @Override
    public void addSongToFavorite(Song song) {
        boolean isSuccess = mFavoriteRepository.insertSongToFavorite(song.getId());
        if (isSuccess) {
            mDetailAlbumView.showAddSongFavoriteSuccess();
        } else {
            mDetailAlbumView.showAddSongFavoriteFail();
        }
    }
}
