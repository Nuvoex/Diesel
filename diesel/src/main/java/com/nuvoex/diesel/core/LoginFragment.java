package com.nuvoex.diesel.core;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nuvoex.diesel.R;
import com.nuvoex.library.LumiereBaseActivity;
import com.nuvoex.library.LumiereBaseFragment;

public class LoginFragment extends LumiereBaseFragment implements LoginContract.View {

    //@BindView(R.id.edit_username)
    EditText mEditUsername;

    // @BindView(R.id.edit_password)
    EditText mEditPassword;

    // @BindView(R.id.button_login)
    Button mButtonLogin;

    //private Unbinder mUnBinder;
    private OnFragmentInteractionListener mListener;
    private LoginContract.Presenter mPresenter;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement " + OnFragmentInteractionListener.class.getSimpleName());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        //    mUnBinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise();
    }

    @Override
    public void onDestroyView() {
        //mUnBinder.unbind();
        mPresenter.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initialise() {
        initView();
        mEditUsername.addTextChangedListener(mTextWatcher);
        mEditPassword.addTextChangedListener(mTextWatcher);
        mEditPassword.setImeActionLabel(getString(R.string.login_text), KeyEvent.KEYCODE_ENTER);
        mEditPassword.setOnKeyListener(mOnKeyListener);
    }

    private void initView() {
         mEditUsername = (EditText) getView().findViewById(R.id.edit_username);

         mEditPassword = (EditText) getView().findViewById(R.id.edit_password);

         mButtonLogin = (Button) getView().findViewById(R.id.button_login);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick();
            }
        });

    }


    private void onLoginClick() {
        String username = mEditUsername.getText().toString();
        String password = mEditPassword.getText().toString();
        mPresenter.login(username, password);
    }

    private void showSnackBar(View viewToFindParent, String message, @Snackbar.Duration int duration) {
        Snackbar snack = Snackbar.make(viewToFindParent, message, duration);
        View snackBarView = snack.getView();
        snackBarView.setBackgroundColor(Color.WHITE);
        TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.color_accent));
        snack.show();
    }

    private View.OnKeyListener mOnKeyListener = new View.OnKeyListener() {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                onLoginClick();
                return true;
            }
            return false;
        }
    };

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String username = mEditUsername.getText().toString();
            String password = mEditPassword.getText().toString();
            mPresenter.onTextChanged(username, password);
        }
    };

    public static String getFragmentTag() {
        return LoginFragment.class.getSimpleName();
    }

    @Override
    protected String screenTitle() {
        //No Actionbar in login
        return "";
    }

    @Override
    public void showProgressIndicator() {
        getFragmentListener().showProgressBar();
    }

    @Override
    public void showViewContent() {
        getFragmentListener().showActivityContent();
    }

    @Override
    public void showNetworkError() {
        getFragmentListener().showNetworkError(new LumiereBaseActivity.RetryApiCallback() {
            @Override
            public void retry() {
                mPresenter.retryLogin();
            }
        });
    }

    @Override
    public void showWrongUsernamePassword() {
        Snackbar.make(mEditUsername, R.string.login_failed_401, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showUsernameError() {
        showSnackBar(mEditUsername, getString(R.string.login_error_phone_number), Snackbar.LENGTH_LONG);
    }

    @Override
    public void showPasswordError() {
        showSnackBar(mEditUsername, getString(R.string.login_error_password), Snackbar.LENGTH_LONG);
    }

    @Override
    public void hideKeyboard() {
        // 1ST method
        View currentFocus = getActivity().getCurrentFocus();
        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            if (currentFocus != null) {
                if (currentFocus.getWindowToken() != null) {
                    inputManager.hideSoftInputFromWindow(
                            currentFocus.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }


    @Override
    public void setLoginButtonState(boolean enable) {
        mButtonLogin.setEnabled(enable);
    }

    @Override
    public void showLoginFailed() {
        Snackbar.make(mEditUsername, R.string.login_failed, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void navigateToHomeActivity() {
        mListener.navigateToHomeActivity();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }


    public interface OnFragmentInteractionListener {
        void navigateToHomeActivity();
    }
}