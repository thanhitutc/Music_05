package com.framgia.music5.screen.album;

import com.framgia.music5.data.model.Album;

/**
 * Created by MyPC on 28/01/2018.
 */

public interface OnClickAlbumListener {
    void onItemClickAlbum(Album album);

    void onClickDeleteAlbum(Album album);

    void onUpdateNameAlbum(Album album);
}
