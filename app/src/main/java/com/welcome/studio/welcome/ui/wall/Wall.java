package com.welcome.studio.welcome.ui.wall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.util.Constance;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by @mistreckless on 12.01.2017. !
 */
@Layout(id = R.layout.fragment_wall)
public class Wall extends BaseMainFragment implements WallView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    WallPresenter presenter;

    private PostAdapter postAdapter;

    public static Wall newInstance() {
        return new Wall();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        postAdapter=new PostAdapter(getContext(),new ArrayList<>());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postAdapter);
    }

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
        return Constance.FragmentTagHolder.WALL;
    }

    @Override
    protected void inject() {
        Injector.getInstance().plus(new WallModule()).inject(this);
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public String getToolbarTitle() {
        return getResources().getString(R.string.app_name);
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        if (fab.isEnabled())
            presenter.onFabClick();
    }

    @Override
    public void setFabEnabled(Boolean enabled) {
        fab.setEnabled(enabled);
    }

    @Override
    public void updateUi(List<Post> posts) {
        postAdapter.setPostList(posts);
        postAdapter.notifyDataSetChanged();
    }
}
