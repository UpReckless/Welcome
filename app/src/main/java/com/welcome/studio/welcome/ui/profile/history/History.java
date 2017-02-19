package com.welcome.studio.welcome.ui.profile.history;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.data.ArchivePhoto;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.profile.ProfileModule;
import com.welcome.studio.welcome.app.Injector;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Royal on 16.01.2017. !
 */
@Layout(id=R.layout.fragment_history)
public class History extends BaseMainFragment implements HistoryView {
    @Bind(R.id.grid_view)
    GridView gridView;
    @Inject
    HistoryPresenter presenter;

    @Inject
    HistoryAdapter adapter;

    @Override
    protected Object getRouter() {
        return getActivity();
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public String getFragmentTag() {
        return getTag();
    }

    @Override
    protected void inject() {
        Injector.getInstance().plus(new ProfileModule()).inject(this);
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    public void setArchivePhotoListToAdapter(List<ArchivePhoto> archivePhotoList) {
        adapter.setArchivePhotoList(archivePhotoList);
        adapter.notifyDataSetChanged();
    }
}
