package com.nuvoex.diesel.core;

import android.support.annotation.NonNull;

import com.nuvoex.diesel.model.LoginResponse;


public class LoginPresenter implements LoginContract.Presenter {

    private int USERNAME_LENGTH = 10;
    private static final int PASSWORD_MINIMUM_LENGTH = 6;

    private String mUsername;
    private String mPassword;

    @NonNull
    private final LoginContract.View mLoginView;

    @NonNull
    private final Repository mRepository;

    private boolean mMockLogin;

    public LoginPresenter(@NonNull LoginContract.View loginView, @NonNull Repository repository, boolean mockLogin) {
        mLoginView = loginView;
        mRepository = repository;
        mMockLogin = mockLogin;
        mLoginView.setPresenter(this);
        updateConfig();
    }

    private void updateConfig() {
        USERNAME_LENGTH = Config.Companion.getSInstance().getUserName();
    }

    @Override
    public void login(String username, String password) {
        mUsername = username;
        mPassword = password;

        mLoginView.hideKeyboard();
        if (username.length() < USERNAME_LENGTH) {
            mLoginView.showUsernameError();
        } else if (password.length() < PASSWORD_MINIMUM_LENGTH) {
            mLoginView.showPasswordError();
        } else {
            callLoginApi(mUsername, mPassword, mLoginCallback);
        }
    }

    @Override
    public void callLoginApi(String username, String password, Repository.LoginCallback loginCallback) {
        mLoginView.showProgressIndicator();
        if (!mMockLogin) {
            mRepository.login(username, password, loginCallback);
        } else {
            loginCallback.successful(new LoginResponse());
        }
    }

    @Override
    public void retryLogin() {
        callLoginApi(mUsername, mPassword, mLoginCallback);
    }

    @Override
    public void onTextChanged(String username, String password) {
        if (username.length() == USERNAME_LENGTH && password.length() >= PASSWORD_MINIMUM_LENGTH) {
            mLoginView.setLoginButtonState(true);
        } else {
            mLoginView.setLoginButtonState(false);
        }
    }

    @Override
    public void onDestroy() {
        mRepository.cancelLoginRequest();
    }

    @Override
    public void start() {

    }

    @Override
    public int getUserNameLength() {
        return USERNAME_LENGTH;
    }

    private Repository.LoginCallback mLoginCallback = new Repository.LoginCallback() {
        @Override
        public void successful(LoginResponse loginResponse) {
            mLoginView.showViewContent();
            mRepository.saveLoginDetails(loginResponse);
            mLoginView.loginSuccess();
        }

        @Override
        public void wrongUsernamePassword() {
            mLoginView.showViewContent();
            mLoginView.showWrongUsernamePassword();
        }

        @Override
        public void failed() {
            mLoginView.showViewContent();
            mLoginView.showLoginFailed();
        }

        @Override
        public void networkFailure() {
            mLoginView.showNetworkError();
        }

        @Override
        public boolean isLatestApk(int latestVersionCode) {
            return mLoginView.isLatestApk(latestVersionCode);

        }

        @Override
        public void appUpdateRequired() {
            mLoginView.showAppUpdateDialog();
        }
    };
}
