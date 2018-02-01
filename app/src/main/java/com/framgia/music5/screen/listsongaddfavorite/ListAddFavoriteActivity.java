package com.framgia.music5.screen.listsongaddfavorite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.framgia.music5.R;
import com.framgia.music5.data.model.Song;
import com.framgia.music5.data.repository.FavoriteRepository;
import java.util.List;

/**
 * Created by MyPC on 01/02/2018.
 */

public class ListAddFavoriteActivity extends AppCompatActivity
        implements ContractListAddFavorite.ListSongAddView {
    private RecyclerView mRecyclerView;
    private SongAddToFavoriteAdapter mAdapter;
    private ContractListAddFavorite.ListSongAddPresenter mPresenter;

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, ListAddFavoriteActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song_add_favorite);
        initToolbar();
        initRecyclerView();
        initPresenter();
    }

    @Override
    public void setPresenter(ContractListAddFavorite.ListSongAddPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showGetSongSuccess(List<Song> songs) {
        mAdapter = new SongAddToFavoriteAdapter(songs);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showGetSongFail() {
        Toast.makeText(this, R.string.announcement_list_favorite_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddSongSuccessfully() {
        finish();
        Toast.makeText(this, R.string.announcement_add_song_favorite_success, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showNoSongSelected() {
        Toast.makeText(this, R.string.announcement_add_favorite_nothing_song, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_song_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_ok:
                List<Song> songs = mAdapter.getSongSelected();
                if (songs != null) {
                    mPresenter.addSongToFavorite(songs);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_favorite);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.title_add_song_to_favorite));
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_add_song_favorite);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void initPresenter() {
        FavoriteRepository favoriteRepository = FavoriteRepository.getInstance(this);
        mPresenter = new ListAddFavoritePresenter(favoriteRepository);
        mPresenter.setView(this);
        mPresenter.onStart();
        mPresenter.loadSongs();
    }
}
