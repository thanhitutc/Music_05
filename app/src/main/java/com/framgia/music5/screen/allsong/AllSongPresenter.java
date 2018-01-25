package com.framgia.music5.screen.allsong;

import com.framgia.music5.data.model.Song;
import com.framgia.music5.data.repository.FavoriteRepository;
import com.framgia.music5.data.repository.SongRepository;
import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public class AllSongPresenter implements AllSongContract.Presenter {
    private AllSongContract.SongView mSongView;
    private SongRepository mSongRepository;
    private FavoriteRepository mFavoriteRepository;

    public AllSongPresenter(SongRepository songRepository, FavoriteRepository favoriteRepository) {
        mSongRepository = songRepository;
        mFavoriteRepository = favoriteRepository;
    }

    @Override
    public void setView(AllSongContract.SongView view) {
        mSongView = view;
    }

    @Override
    public void onStart() {
        mSongView.setPresenter(this);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadListSong() {
        List<Song> songs = mSongRepository.getSong();
        if (songs == null) {
            mSongView.showNoSong();
        } else {
            mSongView.showListSong(songs);
        }
    }

    @Override
    public void addToFavorite(Song song) {
        boolean isSuccessful = mFavoriteRepository.insertSongToFavorite(song.getId());
        if (isSuccessful) {
            mSongView.showAddFavoriteSuccess();
        } else {
            mSongView.showAddFavoriteError();
        }
    }

    @Override
    public void deleteSong(Song song) {
        boolean isSuccessful = mSongRepository.deleteSong(song);
        if (isSuccessful) {
            mSongView.showDeleteSuccess(song);
        } else {
            mSongView.showDeleteError();
        }
    }
}
