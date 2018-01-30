package com.framgia.music5.screen.detailalbum;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.framgia.music5.R;
import com.framgia.music5.data.model.Song;
import java.util.List;

/**
 * Created by MyPC on 29/01/2018.
 */

public class DetailAlbumAdapter extends RecyclerView.Adapter<DetailAlbumAdapter.DetailAlbumHolder> {
    private List<Song> mSongs;
    private OnClickSongDetailAlbumListener mListener;

    public DetailAlbumAdapter(List<Song> songs) {
        mSongs = songs;
    }

    @Override
    public DetailAlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new DetailAlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailAlbumHolder holder, int position) {
        holder.bindData(mSongs.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    public void removeSong(Song song) {
        int position = mSongs.indexOf(song);
        if (position == -1) {
            return;
        }
        mSongs.remove(position);
        notifyItemRemoved(position);
    }

    public void setListener(OnClickSongDetailAlbumListener listener) {
        mListener = listener;
    }

    /**
     * Holder recycler detail album
     */
    public class DetailAlbumHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle;
        private TextView mTextViewSinger;
        private ImageView mButtonMore;

        public DetailAlbumHolder(View itemView) {
            super(itemView);
            initComponents();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClickSongDetailAlbum(mSongs.get(getAdapterPosition()));
                }
            });
            mButtonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenu(view);
                }
            });
        }

        private void initComponents() {
            mTextViewTitle = itemView.findViewById(R.id.text_title_song);
            mTextViewSinger = itemView.findViewById(R.id.text_singer_song);
            mButtonMore = itemView.findViewById(R.id.image_more_song);
        }

        public void bindData(Song song) {
            if (song == null) {
                return;
            }
            mTextViewTitle.setText(song.getTitle());
            mTextViewSinger.setText(song.getSinger());
        }

        private void showMenu(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), mButtonMore);
            popup.inflate(R.menu.menu_song_detail_album);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_add_favorite:
                            mListener.onAddSongToFavorite(mSongs.get(getAdapterPosition()));
                            break;
                        case R.id.menu_delete_song:
                            mListener.onDeleteSong(mSongs.get(getAdapterPosition()));
                            break;
                    }
                    return true;
                }
            });
            popup.show();
        }
    }
}
