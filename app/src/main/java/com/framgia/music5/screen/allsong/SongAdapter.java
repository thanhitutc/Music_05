package com.framgia.music5.screen.allsong;

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
 * Created by MyPC on 25/01/2018.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongRecyclerViewHolder> {
    private List<Song> mSongs;
    private OnClickSongListener mListener;

    public SongAdapter(List<Song> songs) {
        mSongs = songs;
    }

    @Override
    public SongRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongRecyclerViewHolder holder, int position) {
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

    public void setListener(OnClickSongListener listener) {
        mListener = listener;
    }

    /**
     * Song recycler holder
     */

    public class SongRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextTitle;
        private TextView mTextSinger;
        private ImageView mButtonMore;

        public SongRecyclerViewHolder(View itemView) {
            super(itemView);
            initComponents();
            mButtonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenu(view);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClickSong(getAdapterPosition());
                }
            });
        }

        private void initComponents() {
            mButtonMore = itemView.findViewById(R.id.image_more_song);
            mTextTitle = itemView.findViewById(R.id.text_title_song);
            mTextSinger = itemView.findViewById(R.id.text_singer_song);
        }

        public void bindData(Song song) {
            if (song == null) {
                return;
            }
            mTextTitle.setText(song.getTitle());
            mTextSinger.setText(song.getSinger());
        }

        private void showMenu(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), mButtonMore);
            popup.inflate(R.menu.menu_item_song);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_add_favorite:
                            mListener.onAddToFavorite(mSongs.get(getAdapterPosition()));
                            break;
                        case R.id.menu_add_to_album:
                            mListener.onAddToAlbum(mSongs.get(getAdapterPosition()));
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
