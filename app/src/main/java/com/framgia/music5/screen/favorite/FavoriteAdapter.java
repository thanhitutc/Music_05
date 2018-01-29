package com.framgia.music5.screen.favorite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.framgia.music5.R;
import com.framgia.music5.data.model.Song;
import java.util.List;

/**
 * Created by MyPC on 28/01/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {
    private List<Song> mSongs;
    private OnClickSongFavoriteListener mListener;

    public FavoriteAdapter(List<Song> songs) {
        mSongs = songs;
    }

    @Override
    public FavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_song, parent, false);
        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteHolder holder, int position) {
        holder.bindData(mSongs.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    public void removeSongFavorite(Song song) {
        int position = mSongs.indexOf(song);
        if (position == -1) {
            return;
        }
        mSongs.remove(position);
        notifyItemRemoved(position);
    }

    public void setListener(OnClickSongFavoriteListener listener) {
        mListener = listener;
    }

    /**
     * Favorite adapter holder
     */
    public class FavoriteHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle;
        private TextView mTextViewSinger;
        private ImageView mButtonDelete;

        public FavoriteHolder(View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.text_title_song_favorite);
            mTextViewSinger = itemView.findViewById(R.id.text_singer_song_favorite);
            mButtonDelete = itemView.findViewById(R.id.image_delete_favorite);

            mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDeleteFavoriteSong(mSongs.get(getAdapterPosition()));
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClickFavoriteSong(getAdapterPosition());
                }
            });
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
