package com.welcome.studio.welcome.ui.profile.history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.profile.ProfileModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Royal on 16.01.2017. !
 */
@Layout(id=R.layout.fragment_history)
public class History extends BaseMainFragment implements HistoryView, HistoryAdapterListener {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Inject
    HistoryPresenter presenter;

    private HistoryAdapter historyAdapter;
    private static final int MAX_COLUMNS=3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyAdapter=new HistoryAdapter(getContext(),this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),MAX_COLUMNS));
        recyclerView.setAdapter(historyAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
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
    public void postClicked(Post post) {
        presenter.postClicked(post);
    }

    @Override
    public void setPostsToAdapter(List<Post> posts) {
        historyAdapter.setPostList(posts);
        historyAdapter.notifyDataSetChanged();
    }
}
