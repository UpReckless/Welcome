package com.welcome.studio.welcome.ui.profile.today;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.profile.ProfileModule;
import com.welcome.studio.welcome.ui.wall.PostAdapter;
import com.welcome.studio.welcome.ui.wall.PostAdapterListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by @mistreckless on 16.01.2017. !
 */
@Layout(id = R.layout.fragment_now)
public class Now extends BaseMainFragment implements NowView, PostAdapterListener {

    @Inject
    NowPresenter presenter;
    @Bind(R.id.rec_posts_view)
    RecyclerView recPostView;

    private PostAdapter postAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postAdapter=new PostAdapter(getContext(),this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recPostView.setLayoutManager(new LinearLayoutManager(getContext()));
        recPostView.setAdapter(postAdapter);
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
    public void likeClicked(Post post) {

    }

    @Override
    public void willcomeClicked(Post post) {

    }

    @Override
    public void reportClicked(Post post) {

    }

    @Override
    public void commentClicked(Post post) {

    }

    @Override
    public void userThumbClicked(Post post) {

    }

    @Override
    public void tryAgainClicked(Post post) {

    }

    @Override
    public void setPostList(List<Post> posts) {
        postAdapter.addPosts(posts);
        postAdapter.notifyDataSetChanged();
    }

    @Override
    public void changePost(Post post) {
        postAdapter.updatePost(post);
    }

    @Override
    public void removePost(Post post) {
        postAdapter.removePost(post);
    }
}
