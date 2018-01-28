package com.framgia.music5.screen.album;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.framgia.music5.R;
import com.framgia.music5.data.model.Album;
import com.framgia.music5.data.repository.AlbumRepository;
import com.framgia.music5.data.repository.SongInAlbumRepository;
import java.util.List;

/**
 * Fragment album show list album
 */

public class AlbumFragment extends Fragment
        implements ContractAlbum.AlbumView, OnClickAlbumListener, View.OnClickListener {
    public static final int SPAN_COUNT = 2;
    private ContractAlbum.AlbumPresenter mPresenter;
    private RecyclerView mRecyclerViewAlbum;
    private AlbumAdapter mAdapter;
    private FloatingActionButton mButtonAddAlbum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initPresenter();
    }

    @Override
    public void setPresenter(ContractAlbum.AlbumPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showListAlbum(List<Album> albums) {
        mAdapter = new AlbumAdapter(albums);
        mRecyclerViewAlbum.setAdapter(mAdapter);
        mAdapter.setAlbumListener(this);
    }

    @Override
    public void showNoListAlbum() {

    }

    @Override
    public void showNewAlbumSuccess(Album album) {
        mAdapter.insertAlbum(album);
    }

    @Override
    public void showNewAlbumFail() {

    }

    @Override
    public void showDeleteAlbumSuccess(Album album) {
        mAdapter.removeAlbum(album);
    }

    @Override
    public void showDeleteAlbumFail() {

    }

    @Override
    public void showUpdateNameSuccess(Album album, String newName) {
        mAdapter.updateItem(album, newName);
    }

    @Override
    public void showUpdateNameFail() {

    }

    @Override
    public void onItemClickAlbum(Album album) {

    }

    @Override
    public void onClickDeleteAlbum(Album album) {
        mPresenter.deleteAlbum(album);
    }

    @Override
    public void onUpdateNameAlbum(Album album) {
        createDialogUpdateNameAlbum(album);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_album:
                createDialogAddAlbum();
                break;
        }
    }

    private void initPresenter() {
        AlbumRepository albumRepository = AlbumRepository.getInstance(getActivity());
        SongInAlbumRepository songInAlbumRepository =
                SongInAlbumRepository.getInstance(getActivity());
        mPresenter = new AlbumPresenter(albumRepository, songInAlbumRepository);
        mPresenter.setView(this);
        mPresenter.onStart();
        mPresenter.loadListAlbum();
    }

    private void initRecyclerView() {
        mButtonAddAlbum = getView().findViewById(R.id.button_add_album);
        mButtonAddAlbum.setOnClickListener(this);
        mRecyclerViewAlbum = getView().findViewById(R.id.recycler_album);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        mRecyclerViewAlbum.setLayoutManager(layoutManager);
    }

    private void createDialogAddAlbum() {
        LayoutInflater inflater = this.getLayoutInflater();
        final View al = inflater.inflate(R.layout.layout_dialog_add_album, null);
        final EditText editTextNewAlbum = al.findViewById(R.id.edit_text_new_album);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getActivity().getResources().getString(R.string.title_dialog_add_album));
        dialog.setView(al);
        dialog.setNegativeButton(getActivity().getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.addNewAlbum(editTextNewAlbum.getText().toString());
                    }
                });

        dialog.setPositiveButton(getActivity().getResources().getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.create();
        dialog.show();
    }

    private void createDialogUpdateNameAlbum(final Album album) {
        LayoutInflater inflater = this.getLayoutInflater();
        final View al = inflater.inflate(R.layout.layout_dialog_add_album, null);
        final EditText editTextNewName = al.findViewById(R.id.edit_text_new_album);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getActivity().getResources().getString(R.string.title_dialog_add_album));
        dialog.setView(al);
        dialog.setNegativeButton(getActivity().getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.updateNameAlbum(album, editTextNewName.getText().toString());
                    }
                });

        dialog.setPositiveButton(getActivity().getResources().getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.create();
        dialog.show();
    }
}
