package com.welcome.studio.welcome.ui.module.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.adapter.SearchUserAdapter;
import com.welcome.studio.welcome.ui.adapter.SearchUserAdapterListener;
import com.welcome.studio.welcome.util.search_view.RxCompactSearchView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by @mistreckless on 12.01.2017. !
 */
@Layout(id= R.layout.fragment_search)
public class Search extends BaseMainFragment implements SearchView, SearchUserAdapterListener {

    @Inject
    SearchPresenter presenter;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private SearchUserAdapter searchUserAdapter;

    public static Search newInstance(){
        return new Search();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        searchUserAdapter=new SearchUserAdapter(getContext(),this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(searchUserAdapter);
        presenter.controlUsers(recyclerView);
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
    protected void inject(){
        Injector.getInstance().plus(new SearchModule()).inject(this);
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.search);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem searchItem=menu.findItem(R.id.icSearch);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        RxCompactSearchView.queryTextChanged(searchView)
                .debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(presenter::querySearchLine);


        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void userProfileClicked(User user) {

    }

    @Override
    public void addUsers(List<User> users) {
        searchUserAdapter.addUsers(users);
    }

    @Override
    public void setUsers(List<User> users) {
        searchUserAdapter.clearUsers();
        searchUserAdapter.addUsers(users);
    }
}
