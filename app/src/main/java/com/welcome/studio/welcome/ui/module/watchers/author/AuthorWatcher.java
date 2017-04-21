package com.welcome.studio.welcome.ui.module.watchers.author;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.entity.Author;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.adapter.AuthorWatcherAdapter;
import com.welcome.studio.welcome.ui.adapter.AuthorWatcherAdapterListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by @mistreckless on 12.04.2017. !
 */
@Layout(id = R.layout.fragment_author_watcher)
public class AuthorWatcher extends BaseMainFragment implements AuthorWatcherView, AuthorWatcherAdapterListener {

    @Inject
    AuthorWatcherPresenter presenter;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private AuthorWatcherAdapter adapter;

    public static AuthorWatcher newInstance(List<Author> authors) {
        AuthorWatcher authorWatcher = new AuthorWatcher();
        Bundle args = new Bundle();
        args.putParcelableArrayList("authors", (ArrayList<? extends Parcelable>) authors);
        authorWatcher.setArguments(args);
        return authorWatcher;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new AuthorWatcherAdapter(getContext(), this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Author> authors = getArguments().getParcelableArrayList("authors");
        adapter.setAuthors(authors);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
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
        return null;
    }

    @Override
    protected void inject() {
        Injector.getInstance().plus(new AuthorWatcherModule()).inject(this);
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected boolean isAddedToBackStack() {
        return true;
    }

    @Override
    public void authorClicker(Author author) {
        presenter.authorClicked(author);
    }
}
