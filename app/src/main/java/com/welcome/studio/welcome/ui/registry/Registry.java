package com.welcome.studio.welcome.ui.registry;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.registry.singup.last_screen.NextStep;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.util.DepthPagerTransformer;
import com.welcome.studio.welcome.app.Injector;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Royal on 04.02.2017. !
 */
@Layout(id = R.layout.fragment_registry)
public class Registry extends BaseMainFragment implements RegistryView, RegistryRouter {

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Inject
    RegistryPresenter presenter;

    private RegistryFragmentAdapter adapter;

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
        return Constance.FragmentTagHolder.REGISTRY;
    }

    @Override
    protected void inject() {
        Injector.getInstance().plus(new RegistryModule()).inject(this);
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Override
    public void setFragmentPagerAdapter() {
        adapter = new RegistryFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.setPageTransformer(false, new DepthPagerTransformer());
    }

    @Override
    public void resetFragmentAdapter() {
        int currentItem=viewPager.getCurrentItem();
        presenter.setLanguage(currentItem);
        viewPager.setAdapter(null);
        adapter=null;
        RegistryFragmentAdapter choosenAdapter=new RegistryFragmentAdapter(getChildFragmentManager());
        choosenAdapter.clearChoose(currentItem==0?1:0);
        viewPager.setAdapter(choosenAdapter);
    }

    @Override
    public void nextStep() {
        viewPager.setVisibility(View.GONE);
        NextStep nextStep=new NextStep();
        getChildFragmentManager().beginTransaction()
                .add(R.id.container,nextStep,nextStep.getFragmentTag())
                .commit();
    }

    @Override
    public void navigateRestoreAccount() {

    }

    @Override
    public void navigateToMainScreen() {
        Log.e("Router","navigateTo Main screen");
        presenter.leaveRegistryModule();
    }
}
