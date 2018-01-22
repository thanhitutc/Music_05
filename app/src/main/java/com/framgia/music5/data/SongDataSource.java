package com.framgia.music5.data;

import com.framgia.music5.data.model.Song;
import java.util.List;

/**
 * Created by MyPC on 22/01/2018.
 */

public interface SongDataSource {

    List<Song> getSong();

    List<Song> getSongByName(String name);

    boolean deleteSong(String id);
}
