package com.welcome.studio.welcome.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.App;
import com.welcome.studio.welcome.util.Constance;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View {
    private MainComponent mainComponent;
    @Inject
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainComponent= App.getComponent().plus(new MainModule(this));
        mainComponent.inject(this);
        presenter.onCreate(getIntent().getBooleanExtra(Constance.IntentKeyHolder.KEY_IS_AUTH,false));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public MainComponent getComponent() {
        return mainComponent;
    }

    @Override
    public void start() {

    }

    @Override
    public FragmentManager getCurrentFragmentManager() {
        return getSupportFragmentManager();
    }

}
