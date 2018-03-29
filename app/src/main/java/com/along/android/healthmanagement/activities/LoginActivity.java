package com.along.android.healthmanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.apis.Apis;
import com.along.android.healthmanagement.common.JsonCallback;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.along.android.healthmanagement.network.BaseResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.Date;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    //DatabaseHelper helper = new DatabaseHelper(this);
    public GoogleApiClient mGoogleApiClient;
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;
    EditText etEmail, etPassword;
    Button login, register, googleSignIn, forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);
        googleSignIn = (Button) findViewById(R.id.btn_google_sign_in);
        forgetPassword = (Button) findViewById(R.id.btn_forget_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                final User user = EntityManager.findOneBy(User.class, "email = ?", email);

                OkGo.<BaseResponse<User>>post(Apis.getLoginUrl())
                        .tag(this)
                        .params("email", email)
                        .params("passwd", password)
                        .execute(new JsonCallback<BaseResponse<User>>() {
                            @Override
                            public void onSuccess(Response<BaseResponse<User>> response) {
                                BaseResponse<User> data = response.body();
                                User userEntity = (User) data.data;
                                loginHandler(userEntity, password);
                            }

                            @Override
                            public void onError(Response<BaseResponse<User>> response) {
                                super.onError(response);
                            }
                        });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, NavigationDrawerActivity.class);
                //intent.setClass(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    public void loginHandler(User user, String password) {
        if(user != null && user.getPassword() != null) {
            if(user.getPassword().equals(password)) {
                if (user.getPasswordExpirationTime() == 0 || user.getPasswordExpirationTime() > new Date().getTime()) {
                    SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    sp.edit().putLong("uid",user.getId()).apply();

                    // Remove below code if not used
                    Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                    //intent.setClass(LoginActivity.this,MainActivity.class);
                    //intent.putExtra("email", email);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Password has expired", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    protected void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            User user = EntityManager.findOneBy(User.class, "email = ?", acct.getEmail());
            long userId;
            if(user == null) {
                user = new User();
                user.setRealname(acct.getDisplayName());
                user.setEmail(acct.getEmail());
                userId = user.save();
            }
            else {
                userId = user.getId();
            }
            SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
            sp.edit().putLong("uid",userId).apply();

            Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Username", acct.getDisplayName());
            intent.putExtra("Email", acct.getEmail());
            startActivity(intent);
        } else {
            Toast temp = Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT);
            temp.show();
        }
    }
}
