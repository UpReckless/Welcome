package com.welcome.studio.welcome.ui.profile.watcher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.main.MainModule;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by @mistreckless on 31.03.2017. !
 */
@Layout(id = R.layout.fragment_post_watcher)
public class PostWatcher extends BaseMainFragment implements PostWatcherView {
    @Inject
    PostWatcherPresenter presenter;

    @Bind(R.id.img_content)
    ImageView imageView;

    public static PostWatcher newInstance(Post post){
        PostWatcher postWatcher=new PostWatcher();
        Bundle args=new Bundle();
        args.putSerializable("post",post);
        postWatcher.setArguments(args);
        return postWatcher;
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
        return null;
    }

    @Override
    protected void inject() {
        Injector.getInstance().plus(new MainModule(null)).inject(this);
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }
}
