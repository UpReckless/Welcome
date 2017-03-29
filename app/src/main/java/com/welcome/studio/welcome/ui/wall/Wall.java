package com.welcome.studio.welcome.ui.wall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.app.RxBus;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.util.Constance;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by @mistreckless on 12.01.2017. !
 */
@Layout(id = R.layout.fragment_wall)
public class Wall extends BaseMainFragment implements WallView, PostAdapterListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @Inject
    WallPresenter presenter;
    @Inject
    RxBus rxBus;

    private PostAdapter postAdapter;

    public static Wall newInstance() {
        return new Wall();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postAdapter = new PostAdapter(getContext(), this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postAdapter);
        swipeRefresh.setOnRefreshListener(() -> {
            swipeRefresh.setRefreshing(true);
            swipeRefresh.postDelayed(()->{
                swipeRefresh.setRefreshing(false);
                Toast.makeText(getContext(),"refreshing",Toast.LENGTH_SHORT).show();
                presenter.refreshAll();
            },3000);

        });
        swipeRefresh.setColorSchemeColors(R.color.md_blue_500,
                R.color.md_green_500,
                R.color.md_deep_orange_500,
                R.color.md_red_800);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.controlPaging(recyclerView);
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
    public void addPosts(List<Post> posts) {
        postAdapter.addPosts(posts);
    }

    @Override
    public void refreshPosts(int position) {
        postAdapter.notifyItemInserted(position);
    }

    @Override
    public void removePost(Post post) {
        postAdapter.removePost(post);
    }

    @Override
    public void updatePost(Post post) {
        postAdapter.updatePost(post);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUserPost(Post post) {
        if (postAdapter!=null && recyclerView!=null) {
            postAdapter.addUserPost(post);
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void updateUserPost(Post post) {
        postAdapter.updateUserPost(post);
    }

    @Override
    public void showProgressBar(boolean b) {
        progressBar.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void likeClicked(Post post) {
        presenter.likeClicked(post);
    }

    @Override
    public void willcomeClicked(Post post) {
        presenter.willcomeClicked(post);
    }

    @Override
    public void reportClicked(Post post) {
        presenter.reportClicked(post);
    }

    @Override
    public void commentClicked(Post post) {
        presenter.commentClicked(post);
    }

    @Override
    public void userThumbClicked(Post post) {

    }

    @Override
    public void tryAgainClicked(Post post) {
        post.setTryToUpload(false);
        rxBus.sendUserPost(post);
    }

}
