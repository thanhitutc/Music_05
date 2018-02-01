package com.framgia.music5.screen.listsongaddfavorite;

import com.framgia.music5.data.FavoriteDataSource;
import com.framgia.music5.data.model.Song;
import com.framgia.music5.data.repository.FavoriteRepository;
import java.util.List;

/**
 * Created by MyPC on 01/02/2018.
 */

public class ListAddFavoritePresenter implements ContractListAddFavorite.ListSongAddPresenter {
    private ContractListAddFavorite.ListSongAddView mView;
    private FavoriteRepository mFavoriteRepository;

    public ListAddFavoritePresenter(FavoriteRepository favoriteRepository) {
        mFavoriteRepository = favoriteRepository;
    }

    @Override
    public void setView(ContractListAddFavorite.ListSongAddView view) {
        mView = view;
    }

    @Override
    public void onStart() {
        mView.setPresenter(this);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadSongs() {
        List<Song> songs = mFavoriteRepository.getSongNotInFavorite();
        if (songs != null) {
            mView.showGetSongSuccess(songs);
        } else {
            mView.showGetSongFail();
        }
    }

    @Override
    public void addSongToFavorite(List<Song> songs) {
        mFavoriteRepository.insertListSongToFavorite(songs,
                new FavoriteDataSource.CallBackInsertFavorite() {
                    @Override
                    public void onComplete() {
                        mView.showAddSongSuccessfully();
                    }

                    @Override
                    public void onFail(List<String> songFail) {

                    }

                    @Override
                    public void onNoSongInserted() {
                        mView.showNoSongSelected();
                    }
                });
    }
}
