package com.welcome.studio.welcome.ui.profile.history;

import com.welcome.studio.welcome.model.data.ArchivePhoto;

import java.util.List;

/**
 * Created by Royal on 16.01.2017.
 */
public interface HistoryView {
    void setArchivePhotoListToAdapter(List<ArchivePhoto> archivePhotoList);
}
