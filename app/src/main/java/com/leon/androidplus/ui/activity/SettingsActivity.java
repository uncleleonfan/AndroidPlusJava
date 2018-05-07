package com.leon.androidplus.ui.activity;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.leon.androidplus.BuildConfig;
import com.leon.androidplus.R;
import com.leon.androidplus.event.LogoutEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.about)
    Button mAbout;
    @BindView(R.id.logout)
    Button mLogout;
    @BindView(R.id.version)
    TextView mVersion;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void init() {
        super.init();
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.settings);
        String version = String.format(getString(R.string.version),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        mVersion.setText(version);
    }

    @OnClick({R.id.about, R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.about:
                navigateTo(AboutActivity.class);
                break;
            case R.id.logout:
                logout();
                break;
        }
    }

    private void logout() {
        new AlertDialog.Builder(this).setTitle(R.string.logout)
                .setMessage(R.string.confirm_logout)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AVUser.logOut();
                        navigateTo(LoginActivity.class);
                        EventBus.getDefault().post(new LogoutEvent());
                    }
                }).show();
    }

}
