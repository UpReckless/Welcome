package com.welcome.studio.welcome.ui.wall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
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

    @Inject
    WallPresenter presenter;

    private PostAdapter postAdapter;
    private Post post;

    public static Wall newInstance() {
        return new Wall();
    }
    public static Wall newInstance(Post post){
        Wall wall=new Wall();
        Bundle args=new Bundle();
        args.putSerializable("post",post);
        wall.setArguments(args);
        return wall;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        post=getArguments()==null?null:(Post)getArguments().getSerializable("post");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        postAdapter=new PostAdapter(getContext(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postAdapter);
        presenter.controlPaging(recyclerView,post);
        post=null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
    public void refreshPost(int position) {
        postAdapter.notifyItemChanged(position);
    }

    @Override
    public void refresh() {
        postAdapter.notifyDataSetChanged();
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
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUserPost(Post post) {
        postAdapter.addUserPost(post);
    }

    @Override
    public void updateUserPost(Post post) {
        postAdapter.updateUserPost(post);
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
}
