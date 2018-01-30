package com.framgia.music5.screen.allsong;

import com.framgia.music5.data.model.Song;
import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public interface OnClickSongListener {

    void onItemClickSong(List<Song> songs, int position);

    void onAddToFavorite(Song song);

    void onAddToAlbum(Song song);

    void onDeleteSong(Song song);
}
