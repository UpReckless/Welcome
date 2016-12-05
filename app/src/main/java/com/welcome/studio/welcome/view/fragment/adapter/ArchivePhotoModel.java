package com.welcome.studio.welcome.view.fragment.adapter;

import com.welcome.studio.welcome.model.entity.ArchivePhoto;

/**
 * Created by Royal on 28.11.2016.
 */

public interface ArchivePhotoModel {
    void add(ArchivePhoto archivePhoto);
    ArchivePhoto remove(int position);
    ArchivePhoto getPhoto(int position);
    int getSize();
}
