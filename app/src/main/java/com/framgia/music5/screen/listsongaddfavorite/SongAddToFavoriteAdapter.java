package com.framgia.music5.screen.listsongaddfavorite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.framgia.music5.R;
import com.framgia.music5.data.model.Song;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPC on 01/02/2018.
 */

public class SongAddToFavoriteAdapter
        extends RecyclerView.Adapter<SongAddToFavoriteAdapter.SongAddToFavoriteHolder> {
    private List<Song> mSongs;
    private List<Song> mSongSelected = new ArrayList<>();

    public SongAddToFavoriteAdapter(List<Song> songs) {
        mSongs = songs;
    }

    @Override
    public SongAddToFavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song_add, parent, false);
        return new SongAddToFavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(SongAddToFavoriteHolder holder, int position) {
        holder.bindData(mSongs.get(position));
    }

    public List<Song> getSongSelected() {
        return mSongSelected;
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    /**
     * Holder recycler list song add to favorite
     */
    public class SongAddToFavoriteHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle;
        private TextView mTextViewSinger;
        private CheckBox mCheckBox;

        public SongAddToFavoriteHolder(final View itemView) {
            super(itemView);
            initComponents();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCheckBox.isChecked()) {
                        mSongSelected.remove(mSongs.get(getAdapterPosition()));
                        mCheckBox.setChecked(false);
                    } else {
                        mSongSelected.add(mSongs.get(getAdapterPosition()));
                        mCheckBox.setChecked(true);
                    }
                }
            });
        }

        private void initComponents() {
            mTextViewTitle = itemView.findViewById(R.id.text_title_song);
            mTextViewSinger = itemView.findViewById(R.id.text_singer_song);
            mCheckBox = itemView.findViewById(R.id.checkbox_song);
        }

        public void bindData(Song song) {
            if (song == null) {
                return;
            }
            mTextViewTitle.setText(song.getTitle());
            mTextViewSinger.setText(song.getSinger());
        }
    }
}
