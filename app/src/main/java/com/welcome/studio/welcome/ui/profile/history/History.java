package com.welcome.studio.welcome.ui.profile.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.ui.BaseFragment;
import com.welcome.studio.welcome.ui.profile.Profile;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Royal on 16.01.2017.
 */

public class History extends BaseFragment implements HistoryView {
    private HistoryComponent component;
    @Bind(R.id.grid_view)
    GridView gridView;
    @Inject
    AdapterView adapter;
    @Inject
    HistoryPresenter presenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        component=((Profile)getParentFragment()).getComponent().plus(new HistoryModule(this));
        component.inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onActivityCreated();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_history;
    }

    @Override
    public HistoryComponent getComponent() {
        return component;
    }

    @Override
    public void setAdapter() {
        gridView.setAdapter((HistoryAdapter)adapter);
    }
}
