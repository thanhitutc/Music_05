package com.framgia.music5.data;

import com.framgia.music5.data.model.Song;
import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public interface SongInAlbumDataSource {

    List<Song> getSongOfAlbum(int idAlbum);

    boolean insertSongToAlbum(int idAlbum, String idSong);

    boolean deleteAllSongOfAlbum(int idAlbum);

    boolean deleteSongInAlbum(String idSong, int idAlbum);
}
