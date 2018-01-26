package com.framgia.music5.screen.addsongtoalbum;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.framgia.music5.R;
import com.framgia.music5.data.model.Album;
import com.framgia.music5.data.repository.AlbumRepository;
import com.framgia.music5.data.repository.SongInAlbumRepository;
import com.framgia.music5.ultils.Constant;
import java.util.List;

/**
 * Created by MyPC on 26/01/2018.
 */

public class SongAddToAlbumActivity extends Activity
        implements ContractSongAddToAlbum.SongAddToAlbumView, View.OnClickListener,
        OnClickAlbumListener {
    public static final String DEFAULT_ID_SONG_ADD = "-1";
    public static final int SPAN_COUNT = 2;
    private RecyclerView mRecyclerAlbum;
    private ContractSongAddToAlbum.SongAddToAlbumPresenter mPresenter;
    private FloatingActionButton mButtonAddAlbum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_add_allbum);
        initRecyclerView();
        initPresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void setPresenter(ContractSongAddToAlbum.SongAddToAlbumPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showListAlbum(List<Album> albums) {
        ListAlbumAddSongAdapter mAdapter = new ListAlbumAddSongAdapter(albums);
        mRecyclerAlbum.setAdapter(mAdapter);
        mAdapter.setListener(this);
    }

    @Override
    public void showNoListAlbum() {
        showToast(getBaseContext().getResources().getString(R.string.announcement_no_album));
    }

    @Override
    public void showAddSongSuccess() {
        showToast(getBaseContext().getResources()
                .getString(R.string.announcement_add_song_album_success));
        finish();
    }

    @Override
    public void showAddSongFail() {
        showToast(getBaseContext().getResources()
                .getString(R.string.announcement_add_song_album_fail));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_album:
                createDialogAddAlbum();
                break;
        }
    }

    @Override
    public void onItemClickSong(Album album) {
        mPresenter.addSongToAlbum(album.getId());
    }

    private void initRecyclerView() {
        mButtonAddAlbum = findViewById(R.id.button_add_album);
        mButtonAddAlbum.setOnClickListener(this);
        mRecyclerAlbum = findViewById(R.id.recycler_list_album);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mRecyclerAlbum.setLayoutManager(layoutManager);
    }

    private void initPresenter() {
        String idSongAdd = DEFAULT_ID_SONG_ADD;
        if (getIntent().getAction().equals(Constant.ConstantIntent.ACTION_ID_SONG_ADD_TO_ALBUM)) {
            idSongAdd =
                    getIntent().getStringExtra(Constant.ConstantIntent.EXTRA_ID_SONG_ADD_TO_ALBUM);
        }
        AlbumRepository albumRepository = AlbumRepository.getInstance(this);
        SongInAlbumRepository songInAlbumRepository = SongInAlbumRepository.getInstance(this);
        mPresenter = new SongAddToAlbumPresenter(albumRepository, songInAlbumRepository, idSongAdd);
        mPresenter.setView(this);
        mPresenter.onStart();
        mPresenter.loadListSong();
    }

    private void createDialogAddAlbum() {
        LayoutInflater inflater = this.getLayoutInflater();
        final View al = inflater.inflate(R.layout.layout_dialog_add_album, null);
        final EditText editTextNewAlbum = al.findViewById(R.id.edit_text_new_album);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getBaseContext().getResources().getString(R.string.title_dialog_add_album));
        dialog.setView(al);
        dialog.setNegativeButton(getBaseContext().getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.addNewAlbum(editTextNewAlbum.getText().toString());
                    }
                });

        dialog.setPositiveButton(getBaseContext().getResources().getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.create();
        dialog.show();
    }

    private void showToast(String content) {
        Toast.makeText(getBaseContext(), content, Toast.LENGTH_SHORT).show();
    }
}
