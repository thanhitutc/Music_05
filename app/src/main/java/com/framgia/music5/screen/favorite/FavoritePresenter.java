package com.framgia.music5.screen.favorite;

import com.framgia.music5.data.model.Song;
import com.framgia.music5.data.repository.FavoriteRepository;
import java.util.List;

/**
 * Created by MyPC on 26/01/2018.
 */

public class FavoritePresenter implements ContractFavorite.FavoritePresenter {
    private ContractFavorite.FavoriteView mFavoriteView;
    private FavoriteRepository mFavoriteRepository;

    public FavoritePresenter(FavoriteRepository favoriteRepository) {
        mFavoriteRepository = favoriteRepository;
    }

    @Override
    public void setView(ContractFavorite.FavoriteView view) {
        mFavoriteView = view;
    }

    @Override
    public void onStart() {
        mFavoriteView.setPresenter(this);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadFavoriteSongs() {
        List<Song> songs = mFavoriteRepository.getSongInFavorite();
        if (songs != null) {
            mFavoriteView.showListSong(songs);
        } else {
            mFavoriteView.showNoSong();
        }
    }

    @Override
    public void deleteSongFavorite(Song song) {
        boolean isSuccessfully = mFavoriteRepository.deleteFavorite(song.getId());
        if (isSuccessfully) {
            mFavoriteView.showDeleteSongSuccess(song);
        } else {
            mFavoriteView.showDeleteSongFail();
        }
    }
}
