package com.welcome.studio.welcome.ui.registry;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.ui.BaseFragment;
import com.welcome.studio.welcome.ui.main.MainActivity;

import javax.inject.Inject;

/**
 * Created by Royal on 06.01.2017.
 */

public class SignUp extends BaseFragment implements SignUpView {
    private RegistryComponent registryComponent;
    @Inject
    SignUpPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registryComponent = ((MainActivity) getActivity()).getComponent().plus(new RegistryModule(this));
        registryComponent.inject(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public RegistryComponent getComponent() {
        return registryComponent;
    }
}
