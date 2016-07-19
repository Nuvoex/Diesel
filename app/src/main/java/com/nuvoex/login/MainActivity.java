package com.nuvoex.login;

import android.os.Bundle;
import android.view.MenuItem;

import com.nuvoex.diesel.core.LoginFragment;
import com.nuvoex.library.LumiereBaseActivity;

public class MainActivity extends LumiereBaseActivity implements LoginFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            addFragment(loginFragment, false, LoginFragment.getFragmentTag());
        }
    }

    @Override
    protected void configureToolbar() {

    }

    @Override
    protected void setUpNavigationView() {

    }

    @Override
    public int getFrameLayoutId() {
        return R.id.fragment_container;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void navigateToHomeActivity() {

    }

    @Override
    protected boolean useAppBar() {
        return false;
    }

    @Override
    protected boolean useNavDrawer() {
        return false;
    }
}
