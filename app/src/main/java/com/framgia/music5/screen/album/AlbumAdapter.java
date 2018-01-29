package com.framgia.music5.screen.album;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.framgia.music5.R;
import com.framgia.music5.data.model.Album;
import java.util.List;

/**
 * Created by MyPC on 28/01/2018.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {
    public static final int DEFAULT_POSITION_ADD = 0;
    private List<Album> mAlbums;
    private OnClickAlbumListener mAlbumListener;

    public AlbumAdapter(List<Album> albums) {
        mAlbums = albums;
    }

    @Override
    public AlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_album, parent, false);
        return new AlbumHolder(view);
    }

    public void removeAlbum(Album album) {
        int position = mAlbums.indexOf(album);
        if (position == -1) {
            return;
        }
        mAlbums.remove(position);
        notifyItemRemoved(position);
    }

    public void insertAlbum(Album album) {
        if (album == null) {
            return;
        }
        mAlbums.add(DEFAULT_POSITION_ADD, album);
        notifyItemInserted(DEFAULT_POSITION_ADD);
    }

    public void updateItem(Album album, String newName) {
        int position = mAlbums.indexOf(album);
        if (position == -1) {
            return;
        }
        mAlbums.remove(position);
        mAlbums.add(position, new Album(album.getId(), newName));
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(AlbumHolder holder, int position) {
        holder.bindData(mAlbums.get(position));
    }

    @Override
    public int getItemCount() {
        return mAlbums != null ? mAlbums.size() : 0;
    }

    public void setAlbumListener(OnClickAlbumListener albumListener) {
        mAlbumListener = albumListener;
    }

    /**
     * Holder Album recyler
     */
    public class AlbumHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewNameAlbum;
        private ImageView mButtonMore;

        public AlbumHolder(View itemView) {
            super(itemView);
            mTextViewNameAlbum = itemView.findViewById(R.id.text_name_album);
            mButtonMore = itemView.findViewById(R.id.image_more_album);

            mButtonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenu(view);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAlbumListener.onItemClickAlbum(mAlbums.get(getAdapterPosition()));
                }
            });
        }

        public void bindData(Album album) {
            if (album == null) {
                return;
            }
            mTextViewNameAlbum.setText(album.getNameAlbum());
        }

        private void showMenu(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), mButtonMore);
            popup.inflate(R.menu.menu_album);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_update_album:
                            mAlbumListener.onUpdateNameAlbum(mAlbums.get(getAdapterPosition()));
                            break;
                        case R.id.menu_delete_album:
                            mAlbumListener.onClickDeleteAlbum(mAlbums.get(getAdapterPosition()));
                            break;
                    }
                    return true;
                }
            });
            popup.show();
        }
    }
}
