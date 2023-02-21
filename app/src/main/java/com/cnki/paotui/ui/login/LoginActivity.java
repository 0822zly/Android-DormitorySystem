package com.cnki.paotui.ui.login;

import static com.cnki.paotui.Ikeys.ISFROMMAIN;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cnki.paotui.App;
import com.cnki.paotui.BaseActivity;
import com.cnki.paotui.Ikeys;
import com.cnki.paotui.MainActivity;
import com.cnki.paotui.R;
import com.cnki.paotui.RegisterActivity;
import com.cnki.paotui.db.User;
import com.cnki.paotui.db.UserDao;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.SPUtil;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;


public class LoginActivity extends BaseActivity {

    private LoginViewModel loginViewModel;
    private EditText username;
    private EditText password;
    private Button login;
    private ProgressBar loading;
    private CheckBox checkBox;
    TextView regter;
    boolean isfromMain=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ImmersionBar.with(this).transparentBar().init();
        isfromMain=getIntent().getBooleanExtra(ISFROMMAIN,false);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        username=findViewById(R.id.username);
        checkBox=findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SPUtil.getInstance().setValue(Ikeys.ISAUTOLOGIN,b);
            }
        });
        checkBox.setChecked(SPUtil.getInstance().getBoolean(Ikeys.ISAUTOLOGIN));
        password=findViewById(R.id.password);
        regter=findViewById(R.id.regter);
        login=findViewById(R.id.login);
        loading=findViewById(R.id.loading);
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                login.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    username.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    password.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loading.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    //updateUiWithUser(loginResult.getSuccess());
                }


            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(username.getText().toString(),
                        password.getText().toString());
            }
        };
        username.addTextChangedListener(afterTextChangedListener);
        password.addTextChangedListener(afterTextChangedListener);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(username.getText().toString(),
                            password.getText().toString());
                }
                return false;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
//                loginViewModel.login(username.getText().toString(),
//                        password.getText().toString());
                ThreadPoolExecutorUtil.doTask(new Runnable() {
                    @Override
                    public void run() {
                        User user = JDBC.getInstance().queryuser(username.getText().toString(), password.getText().toString());
                        App.user=user;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(user!=null){
                                    updateUiWithUser();
                                }else {
                                    Toast.makeText(mContext, "登入失败", Toast.LENGTH_SHORT).show();
                                }
                                loading.setVisibility(View.GONE);
                            }
                        });
                    }
                });
            }
        });
        regter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(mContext, RegisterActivity.class);
               mContext.startActivity(intent);
            }
        });
        if(isfromMain){

        }else {
            if (SPUtil.getInstance().getBoolean(Ikeys.ISAUTOLOGIN)) {
                if (!TextUtils.isEmpty(SPUtil.getInstance().getString(Ikeys.USERNAME)) && !TextUtils.isEmpty(SPUtil.getInstance().getString(Ikeys.PASSWORD))) {
                    isAutoLogin = true;
                    loading.setVisibility(View.VISIBLE);
                    username.setText(SPUtil.getInstance().getString(Ikeys.USERNAME));
                    password.setText(SPUtil.getInstance().getString(Ikeys.PASSWORD));

                    ThreadPoolExecutorUtil.doTask(new Runnable() {
                        @Override
                        public void run() {
                            User user = JDBC.getInstance().queryuser(SPUtil.getInstance().getString(Ikeys.USERNAME),SPUtil.getInstance().getString(Ikeys.PASSWORD));
                            App.user=user;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(user!=null){
                                        updateUiWithUser();
                                    }else {
                                        Toast.makeText(mContext, "登入失败", Toast.LENGTH_SHORT).show();
                                    }
                                    loading.setVisibility(View.GONE);
                                }
                            });
                        }
                    });
                }
            }
        }
    }
    Handler handler=new Handler(Looper.getMainLooper());
    boolean isAutoLogin=false;//是否自动登入
    private void updateUiWithUser() {
        if(!isAutoLogin) {
            SPUtil.getInstance().setValue(Ikeys.USERNAME, username.getText().toString());
            SPUtil.getInstance().setValue(Ikeys.PASSWORD, password.getText().toString());
        }
        String welcome = getString(R.string.welcome) + username.getText().toString();

        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        if(!isfromMain) {
            Intent intent = new Intent(mContext, MainActivity.class);
            mContext.startActivity(intent);
        }
        finish();

    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}