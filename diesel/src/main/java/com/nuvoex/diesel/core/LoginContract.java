package com.nuvoex.diesel.core;


import android.content.Context;

import com.nuvoex.diesel.core.Repository.LoginCallback;

public class LoginContract {

    interface View extends BaseView<Presenter> {

        void showViewContent();

        void setLoginButtonState(boolean enable);

        void showUsernameError();

        void showPasswordError();

        void hideKeyboard();

        void showProgressIndicator();

        void showNetworkError();

        void showWrongUsernamePassword();

        void showLoginFailed();

        void loginSuccess();

        Context getApplicationContext();

        void showAppUpdateDialog();
    }

    interface Presenter extends BasePresenter {

        void login(String username, String password);

        void callLoginApi(String username, String password, LoginCallback loginCallback);

        void retryLogin();

        void onTextChanged(String username, String password);

        void onDestroy();

        int getUserNameLength();

        void redirectToPlayStore();
    }
}
