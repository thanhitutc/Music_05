package com.framgia.music5.screen.favorite;

import com.framgia.music5.data.model.Song;

/**
 * Created by MyPC on 28/01/2018.
 */

public interface OnClickSongFavoriteListener {

    void onItemClickFavoriteSong(int position);

    void onDeleteFavoriteSong(Song song);
}
