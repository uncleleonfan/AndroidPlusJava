package com.leon.androidplus.ui.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.androidplus.R;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.contract.RegisterContract;
import com.leon.androidplus.presenter.RegisterPresenter;
import com.leon.androidplus.utils.RegexUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterContract.View,
        View.OnFocusChangeListener {

    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.confirm_password)
    EditText mConfirmPassword;
    @BindView(R.id.register)
    Button mRegister;
    @BindView(R.id.user_name_input_layout)
    TextInputLayout mUserNameInputLayout;
    @BindView(R.id.password_input_layout)
    TextInputLayout mPasswordInputLayout;
    @BindView(R.id.confirm_password_input_layout)
    TextInputLayout mConfirmPasswordInputLayout;
    @BindView(R.id.progress_layout)
    LinearLayout mProgressLayout;

    @Inject
    RegisterPresenter mRegisterPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        super.init();

        //给EditText设置焦点监听，获取焦点后清除error的显示
        mUserName.setOnFocusChangeListener(this);
        mConfirmPassword.setOnFocusChangeListener(this);
        mPassword.setOnFocusChangeListener(this);

        //设置软键盘事件处理
        mConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                register();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRegisterPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegisterPresenter.dropView();
    }

    @OnClick(R.id.register)
    public void onViewClicked() {
        hideSoftKeyboard();
        register();
    }

    /**
     * 注册
     */
    private void register() {
        hideSoftKeyboard();
        String userName = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();

        if (checkUserName(userName)) {//检查用户名是否合法
            if (checkPassword(password)) {//检查密码是否合法
                if (confirmPassword.equals(password)) {//检查密码和确认密码是否一致
                    mProgressLayout.setVisibility(View.VISIBLE);
                    mConfirmPasswordInputLayout.setErrorEnabled(false);
                    mRegisterPresenter.register(userName, password);//开始注册
                } else {
                    mConfirmPasswordInputLayout.setErrorEnabled(true);
                    mConfirmPasswordInputLayout.setError(getString(R.string.error_confirm_password));
                }
            }
        }
    }


    private boolean checkPassword(String password) {
        if (password.length() == 0) {
            mPasswordInputLayout.setErrorEnabled(true);
            mPasswordInputLayout.setError(getString(R.string.error_password_empty));
            return false;
        } else if (!RegexUtils.isValidPassword(password)) {
            mPasswordInputLayout.setErrorEnabled(true);
            mPasswordInputLayout.setError(getString(R.string.error_password));
            return false;
        }
        return true;
    }

    private boolean checkUserName(String userName) {
        if (userName.length() == 0) {
            mUserNameInputLayout.setErrorEnabled(true);
            mUserNameInputLayout.setError(getString(R.string.error_user_name_empty));
            return false;
        } else if (!RegexUtils.isValidUserName(userName)) {
            mUserNameInputLayout.setErrorEnabled(true);
            mUserNameInputLayout.setError(getString(R.string.error_user_name));
            return false;
        }
        return true;
    }


    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.user_name:
                mUserNameInputLayout.setErrorEnabled(false);
                break;
            case R.id.password:
                mPasswordInputLayout.setErrorEnabled(false);
                break;
            case R.id.confirm_password:
                mConfirmPasswordInputLayout.setErrorEnabled(false);
                break;
        }
    }

    /**
     * 注册失败
     */
    @Override
    public void onRegisterFailed() {
        mProgressLayout.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.register_failed), Toast.LENGTH_SHORT).show();
    }

    /**
     * 注册成功
     */
    @Override
    public void onRegisterSuccess() {
        mProgressLayout.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(Constant.EXTRA_USER_NAME, mUserName.getText().toString().trim());
        intent.putExtra(Constant.EXTRA_PASSWORD, mPassword.getText().toString().trim());
        setResult(Constant.RESULT_CODE, intent);
        finish();
    }

    /**
     * 用户名已占用
     */
    @Override
    public void onUserNameTaken() {
        mProgressLayout.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.user_name_taken), Toast.LENGTH_SHORT).show();
    }
}
