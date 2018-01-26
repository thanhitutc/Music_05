package com.framgia.music5.screen.addsongtoalbum;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.framgia.music5.R;
import com.framgia.music5.data.model.Album;
import java.util.List;

/**
 * Created by MyPC on 26/01/2018.
 */

public class ListAlbumAddSongAdapter
        extends RecyclerView.Adapter<ListAlbumAddSongAdapter.ListAlbumHolder> {
    private List<Album> mAlbums;

    public ListAlbumAddSongAdapter(List<Album> albums) {
        mAlbums = albums;
    }

    @Override
    public ListAlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_album_add, parent, false);
        return new ListAlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAlbumHolder holder, int position) {
        holder.binData(mAlbums.get(position));
    }

    @Override
    public int getItemCount() {
        return mAlbums != null ? mAlbums.size() : 0;
    }

    /**
     * Holder List album add
     */

    public class ListAlbumHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewNameAlbum;

        public ListAlbumHolder(View itemView) {
            super(itemView);
            mTextViewNameAlbum = itemView.findViewById(R.id.text_name_album);
        }

        public void binData(Album album) {
            if (album == null) {
                return;
            }
            mTextViewNameAlbum.setText(album.getNameAlbum());
        }
    }
}
