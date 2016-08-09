package com.nuvoex.diesel.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nuvoex.diesel.model.LoginResponse;

import java.util.List;


public class LoginPresenter implements LoginContract.Presenter {

    private int USERNAME_LENGTH = 10;
    private static final int PASSWORD_MINIMUM_LENGTH = 6;

    private String mUsername;
    private String mPassword;

    @NonNull
    private final LoginContract.View mLoginView;

    @NonNull
    private final Repository mRepository;

    public LoginPresenter(@NonNull LoginContract.View loginView, @NonNull Repository repository) {
        mLoginView = loginView;
        mRepository = repository;
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
        mRepository.login(username, password, loginCallback);
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

    @Override
    public void redirectToPlayStore() {
        String playStoreID = mLoginView.getApplicationContext().getPackageName();

        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + playStoreID));
        boolean isPlayStoreAppPresent = false;
        // Need to directly open play store app only as more apps may have handled market:// Urls
        final List<ResolveInfo> otherApps = mLoginView.getApplicationContext().getPackageManager().queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp : otherApps)
            if (otherApp.activityInfo.applicationInfo.packageName.equals("com.android.vending")) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(
                        otherAppActivity.applicationInfo.packageName,
                        otherAppActivity.name
                );
                rateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                rateIntent.setComponent(componentName);
                isPlayStoreAppPresent = true;
                break;
            }
        if (!isPlayStoreAppPresent) {
            rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + playStoreID));
        }
        mLoginView.getApplicationContext().startActivity(rateIntent);


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

            Context context = mLoginView.getApplicationContext();
            int version;
            try {
                PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                version = pInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return true;
            }
            if (latestVersionCode > version) {
                return false;
            }
            return true;
        }

        @Override
        public void appUpdateRequired() {
            mLoginView.showAppUpdateDialog();
        }
    };
}
