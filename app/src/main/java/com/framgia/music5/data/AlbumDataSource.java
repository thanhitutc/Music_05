package com.framgia.music5.data;

import com.framgia.music5.data.model.Album;
import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public interface AlbumDataSource {

    List<Album> getListAlbum();

    boolean insertAlbum(String name);

    boolean deleteAlbum(int idAlbum);

    boolean updateName(int idAlbum, String newName);

    int getLastIdInsert();
}
