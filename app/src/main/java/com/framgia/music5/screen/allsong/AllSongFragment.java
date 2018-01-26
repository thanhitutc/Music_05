package com.framgia.music5.screen.allsong;

import android.content.Intent;
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
import com.framgia.music5.data.repository.SongRepository;
import com.framgia.music5.screen.addsongtoalbum.SongAddToAlbumActivity;
import com.framgia.music5.ultils.Constant;
import java.util.List;

/**
 * AllSongFragment
 **/
public class AllSongFragment extends Fragment
        implements AllSongContract.SongView, OnClickSongListener {
    private AllSongContract.Presenter mPresenter;
    private SongAdapter mSongAdapter;
    private RecyclerView mRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_allsong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRecyclerView();
        initPresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(AllSongContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showListSong(List<Song> songs) {
        mSongAdapter = new SongAdapter(songs);
        mRecycler.setAdapter(mSongAdapter);
        mSongAdapter.setListener(this);
    }

    @Override
    public void showNoSong() {
        showToast(getActivity().getResources().getString(R.string.announcement_no_song));
    }

    @Override
    public void showDeleteSuccess(Song song) {
        mSongAdapter.removeSong(song);
    }

    @Override
    public void showDeleteError() {
        showToast(getActivity().getResources().getString(R.string.announcement_delete_song_fail));
    }

    @Override
    public void showAddFavoriteSuccess() {
        showToast(getActivity().getResources()
                .getString(R.string.announcement_add_song_favorite_success));
    }

    @Override
    public void showAddFavoriteError() {
        showToast(getActivity().getResources()
                .getString(R.string.announcement_add_song_favorite_fail));
    }

    @Override
    public void onItemClickSong(int position) {
    }

    @Override
    public void onAddToFavorite(Song song) {
        mPresenter.addToFavorite(song);
    }

    @Override
    public void onAddToAlbum(Song song) {
        Intent intent = new Intent(getActivity(), SongAddToAlbumActivity.class);
        intent.setAction(Constant.ConstantIntent.ACTION_ID_SONG_ADD_TO_ALBUM);
        intent.putExtra(Constant.ConstantIntent.EXTRA_ID_SONG_ADD_TO_ALBUM, song.getId());
        getActivity().startActivity(intent);
    }

    @Override
    public void onDeleteSong(Song song) {
        mPresenter.deleteSong(song);
    }

    private void showToast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }

    private void initRecyclerView() {
        mRecycler = getView().findViewById(R.id.recyler_allsong);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(layoutManager);
    }

    private void initPresenter() {
        SongRepository songRepository = SongRepository.getInstance(getActivity());
        FavoriteRepository favoriteRepository = FavoriteRepository.getInstance(getActivity());
        mPresenter = new AllSongPresenter(songRepository, favoriteRepository);
        mPresenter.setView(this);
        mPresenter.onStart();
        mPresenter.loadListSong();
    }
}
