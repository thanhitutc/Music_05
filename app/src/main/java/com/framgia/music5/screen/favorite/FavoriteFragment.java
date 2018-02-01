package com.framgia.music5.screen.favorite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.framgia.music5.R;
import com.framgia.music5.data.model.Song;
import com.framgia.music5.data.repository.FavoriteRepository;
import com.framgia.music5.screen.listsongaddfavorite.ListAddFavoriteActivity;
import java.util.List;

/**
 * Favorite fragment show list
 */
public class FavoriteFragment extends Fragment
        implements ContractFavorite.FavoriteView, OnClickSongFavoriteListener {
    private RecyclerView mRecyclerViewFavorite;
    private FavoriteAdapter mAdapter;
    private ContractFavorite.FavoritePresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadFavoriteSongs();
    }

    @Override
    public void setPresenter(ContractFavorite.FavoritePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showListSong(List<Song> songs) {
        mAdapter = new FavoriteAdapter(songs);
        mRecyclerViewFavorite.setAdapter(mAdapter);
        mAdapter.setListener(this);
    }

    @Override
    public void showNoSong() {
        showToast(getActivity().getResources().getString(R.string.announcement_no_song_favorite));
    }

    @Override
    public void showDeleteSongSuccess(Song song) {
        mAdapter.removeSongFavorite(song);
    }

    @Override
    public void showDeleteSongFail() {
        showToast(getActivity().getResources().getString(R.string.announcement_delete_song_fail));
    }

    @Override
    public void onItemClickFavoriteSong(int position) {

    }

    @Override
    public void onDeleteFavoriteSong(Song song) {
        mPresenter.deleteSongFavorite(song);
    }

    private void initPresenter() {
        FavoriteRepository favoriteRepository = FavoriteRepository.getInstance(getActivity());
        mPresenter = new FavoritePresenter(favoriteRepository);
        mPresenter.setView(this);
        mPresenter.onStart();
    }

    private void initRecyclerView() {
        getView().findViewById(R.id.button_add_favorite)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().startActivity(
                                ListAddFavoriteActivity.getInstance(getActivity()));
                    }
                });
        mRecyclerViewFavorite = getView().findViewById(R.id.recycler_favorite);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewFavorite.setLayoutManager(layoutManager);
    }

    private void showToast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }
}
