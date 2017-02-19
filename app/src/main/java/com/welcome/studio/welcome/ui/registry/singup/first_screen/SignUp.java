package com.welcome.studio.welcome.ui.registry.singup.first_screen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.registry.RegistryModule;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.app.Injector;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


@Layout(id = R.layout.fragment_sign_up)
public class SignUp extends BaseMainFragment implements SignUpView {

    @Inject
    SignUpPresenter presenter;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.txt_hello)
    TextView txtHello;
    @Bind(R.id.edt_nickname)
    EditText edtNickname;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.img_check)
    ImageView imgCheck;


    public static SignUp newInstance() {
        return new SignUp();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btnNext.setEnabled(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.listenField(RxTextView.textChanges(edtNickname));
    }

    @OnClick(R.id.txt_already_registered)
    public void onAlreadyRegisteredClick() {
        presenter.onAlreadyRegisteredClick();
    }

    @OnClick(R.id.btn_next)
    public void onNextButtonClick() {
        if (btnNext.isEnabled())
            presenter.clickNext();
    }

    @Override
    protected Object getRouter() {
        return getParentFragment();
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public String getFragmentTag() {
        return Constance.FragmentTagHolder.SIGN_UP;
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
    public void setEnableNextButton(Boolean enabled) {
        btnNext.setEnabled(enabled);
        progressBar.setVisibility(View.INVISIBLE);
        imgCheck.setVisibility(View.VISIBLE);
        imgCheck.setImageDrawable(getResources().getDrawable(enabled?R.mipmap.green_check_mark:R.mipmap.red_check_mark));
    }

    @Override
    public void setHeaderName(CharSequence name) {
        txtHello.setText(name);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    @SuppressLint("HardwareIds")
    public void prepareParams() {
        String imei = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        presenter.registryNewUser(imei);
    }

    @Override
    public void showToast(@StringRes int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBarVisible() {
        imgCheck.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }
}
