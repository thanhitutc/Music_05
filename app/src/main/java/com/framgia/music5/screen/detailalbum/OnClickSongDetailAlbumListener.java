package com.framgia.music5.screen.detailalbum;

import com.framgia.music5.data.model.Song;

/**
 * Created by MyPC on 29/01/2018.
 */

public interface OnClickSongDetailAlbumListener {

    void onItemClickSongDetailAlbum(Song song);

    void onAddSongToFavorite(Song song);

    void onDeleteSong(Song song);
}
