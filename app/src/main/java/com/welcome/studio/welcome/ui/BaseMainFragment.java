package com.welcome.studio.welcome.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.welcome.studio.welcome.ui.main.MainActivity;

import java.lang.annotation.Annotation;

import butterknife.ButterKnife;

/**
 * Created by Royal on 04.02.2017.
 */
@SuppressWarnings("unchecked")
public abstract class BaseMainFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Class cls = getClass();
        if (!cls.isAnnotationPresent(Layout.class)) return null;
        Annotation annotation = cls.getAnnotation(Layout.class);
        Layout layout = (Layout) annotation;
        View view = inflater.inflate(layout.id(), null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        ((MainActivity) getActivity()).setToolbarToDrawer(getToolbar(), getToolbarTitle(), isAddedToBackStack());
        getPresenter().setView(this);
        getPresenter().setRouter(getRouter());
    }

    protected boolean isAddedToBackStack() {
        return false;
    }

    protected abstract Object getRouter();

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        getPresenter().setRouter(null);
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //do nothing
    }

    @NonNull
    protected abstract BasePresenter getPresenter();

    public abstract String getFragmentTag();

    protected abstract void inject();

    protected abstract Toolbar getToolbar();

    protected abstract String getToolbarTitle();
}
