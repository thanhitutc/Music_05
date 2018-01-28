package com.framgia.music5.screen.detailalbum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.framgia.music5.R;
import com.framgia.music5.data.model.Album;
import com.framgia.music5.data.model.Song;
import com.framgia.music5.data.repository.FavoriteRepository;
import com.framgia.music5.data.repository.SongInAlbumRepository;
import com.framgia.music5.ultils.Constant;
import java.util.List;

/**
 * Created by MyPC on 29/01/2018.
 */

public class DetailAlbumActivity extends AppCompatActivity
        implements ContractDetaiAlbum.DetailAlbumView, OnClickSongDetailAlbumListener {
    private static final int DEFAULT_ID_ALBUM = -1;
    private static final String DEFAULT_NAME_ALBUM = "Detail album";
    private ContractDetaiAlbum.DetailAlbumPresenter mPresenter;
    private RecyclerView mRecyclerViewDetail;
    private DetailAlbumAdapter mAdapter;

    public static Intent getInstance(Context context, Album album) {
        Intent intent = new Intent(context, DetailAlbumActivity.class);
        intent.putExtra(Constant.ConstantIntent.EXTRA_ALBUM, (Parcelable) album);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_album);
        initRecyclerView();
        initPresenter();
    }

    @Override
    public void setPresenter(ContractDetaiAlbum.DetailAlbumPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoadListDetailSong(List<Song> songs) {
        mAdapter = new DetailAlbumAdapter(songs);
        mRecyclerViewDetail.setAdapter(mAdapter);
        mAdapter.setListener(this);
    }

    @Override
    public void showLoadNoSong() {
        Toast.makeText(getBaseContext(), R.string.announcement_no_song_in_album, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showDeleteSongSuccess(Song song) {
        mAdapter.removeSong(song);
    }

    @Override
    public void showDeleteSongFail() {
        Toast.makeText(getBaseContext(), R.string.announcement_delete_song_fail, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showAddSongFavoriteSuccess() {
        Toast.makeText(getBaseContext(), R.string.announcement_add_song_favorite_success,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddSongFavoriteFail() {
        Toast.makeText(getBaseContext(), R.string.announcement_add_song_favorite_fail,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickSongDetailAlbum(Song song) {

    }

    @Override
    public void onAddSongToFavorite(Song song) {
        mPresenter.addSongToFavorite(song);
    }

    @Override
    public void onDeleteSong(Song song) {
        mPresenter.deleteSongOfAlbum(song);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        mRecyclerViewDetail = findViewById(R.id.recycler_detail_album);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        mRecyclerViewDetail.setLayoutManager(layoutManager);
    }

    private void initPresenter() {
        int idAlbum = DEFAULT_ID_ALBUM;
        String titleToolbar = DEFAULT_NAME_ALBUM;
        Album album = getIntent().getParcelableExtra(Constant.ConstantIntent.EXTRA_ALBUM);
        idAlbum = album.getId();
        titleToolbar = album.getNameAlbum();
        initToolbar(titleToolbar);
        SongInAlbumRepository songInAlbumRepository =
                SongInAlbumRepository.getInstance(getBaseContext());
        FavoriteRepository favoriteRepository = FavoriteRepository.getInstance(getBaseContext());
        mPresenter = new DetailAlbumPresenter(songInAlbumRepository, favoriteRepository);
        mPresenter.setView(this);
        mPresenter.onStart();
        mPresenter.loadListSongDetail(idAlbum);
    }

    private void initToolbar(String titleToolbar) {
        Toolbar toolbar = findViewById(R.id.toolbar_detail_album);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(titleToolbar);
    }
}
