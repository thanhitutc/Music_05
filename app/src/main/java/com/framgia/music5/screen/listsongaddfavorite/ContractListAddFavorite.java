package com.framgia.music5.screen.listsongaddfavorite;

import com.framgia.music5.BasePresenter;
import com.framgia.music5.BaseView;
import com.framgia.music5.data.model.Song;
import java.util.List;

/**
 * Created by MyPC on 01/02/2018.
 */

public interface ContractListAddFavorite {

    /**
     * Interface view list song add to favorite
     */
    interface ListSongAddView extends BaseView<ListSongAddPresenter> {
        void showGetSongSuccess(List<Song> songs);

        void showGetSongFail();

        void showAddSongSuccessfully();

        void showNoSongSelected();
    }

    /**
     * Interface presenter list song add to favorite
     */

    interface ListSongAddPresenter extends BasePresenter<ListSongAddView> {
        void loadSongs();

        void addSongToFavorite(List<Song> songs);
    }
}
