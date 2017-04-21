package com.welcome.studio.welcome.ui.module.profile.now;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.adapter.WillcomeAdapter;
import com.welcome.studio.welcome.ui.adapter.WillcomeAdapterListener;
import com.welcome.studio.welcome.ui.module.profile.ProfileModule;
import com.welcome.studio.welcome.ui.adapter.PostAdapter;
import com.welcome.studio.welcome.ui.adapter.PostAdapterListener;
import com.welcome.studio.welcome.util.Helper;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by @mistreckless on 16.01.2017. !
 */
@Layout(id = R.layout.fragment_now)
public class Now extends BaseMainFragment implements NowView, PostAdapterListener, WillcomeAdapterListener {

    @Inject
    NowPresenter presenter;
    @Bind(R.id.rec_posts_view)
    RecyclerView recPostView;
    @Bind(R.id.rec_willcome_view)
    RecyclerView recWillcomeView;

    private PostAdapter postAdapter;
    private WillcomeAdapter willcomeAdapter;

    public static Now newInstance(User user){
        Now now=new Now();
        Bundle args=new Bundle();
        args.putSerializable("user",user);
        now.setArguments(args);
        return now;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.create();
        presenter.setUser((User)getArguments().getSerializable("user"));
    }

    @Override
    public void initAdapters(User user) {
        postAdapter = new PostAdapter(getContext(), this, user);
        willcomeAdapter = new WillcomeAdapter(getContext(), this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recPostView.setLayoutManager(new LinearLayoutManager(getContext()));
        recPostView.setAdapter(postAdapter);
        recWillcomeView.setLayoutManager(new LinearLayoutManager(getContext()));
        recWillcomeView.setAdapter(willcomeAdapter);
        if (Helper.isAndroidLOrHigher()) {
            recWillcomeView.setNestedScrollingEnabled(false);
            recPostView.setNestedScrollingEnabled(false);
        }
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
    public void userProfileClicked(Post post) {
        presenter.userProfileClicked(post);
    }

    @Override
    public void tryAgainClicked(Post post) {
        //do nothing :)
    }

    @Override
    public void likeCountClicked(Post post) {
        presenter.likeCountClicked(post);
    }

    @Override
    public void willcomeCountClicked(Post post) {
        presenter.willcomeCountClicked(post);
    }

    @Override
    public void reportCountClicked(Post post) {
        presenter.reportCountClicked(post);
    }

    @Override
    public void addressLineClicked(Post post) {
        presenter.addressLineClicked(post);
    }

    @Override
    public void setPostList(List<Post> posts) {
        postAdapter.removeAllPosts();
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

    @Override
    public void setWillcomeList(List<Post> posts) {
        willcomeAdapter.setPostList(posts);
        willcomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeWillcome(Post post) {
        willcomeAdapter.removePost(post);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addressClicked(Post post) {
        presenter.addressLineClicked(post);
    }

    @Override
    public void cancelClicked(Post post) {

    }

    @Override
    public void postClicked(Post post) {
        presenter.willcomedPostClicked(post);
    }
}
