package com.framgia.music5.data;

import com.framgia.music5.data.model.Song;
import java.util.List;

/**
 * Created by MyPC on 24/01/2018.
 */

public interface FavoriteDataSource {

    List<Song> getSongInFavorite();

    List<Song> getSongNotInFavorite();

    boolean deleteFavorite(String idSong);

    boolean insertSongToFavorite(String idSong);

    void insertListSongToFavorite(List<Song> songs, CallBackInsertFavorite callBack);

    /**
     * callback insert favorite
     **/
    interface CallBackInsertFavorite {
        void onComplete();

        void onFail(List<String> songFail);

        void onNoSongInserted();
    }
}
