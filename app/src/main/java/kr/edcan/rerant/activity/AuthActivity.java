package kr.edcan.rerant.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    CallbackManager fbManager;
    ActivityAuthBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
        initDefault();
    }

    private void initDefault() {
        fbManager = CallbackManager.Factory.create();
        setFacebookCallback();
        binding.authFacebookLogin.setOnClickListener(this);
        binding.authNativeLogin.setOnClickListener(this);
        binding.authRegister.setOnClickListener(this);
    }

    private void setFacebookCallback(){
        LoginManager.getInstance().registerCallback(fbManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("asdf", loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Log.e("asdf", "Canceled");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.authFacebookLogin:
                binding.authFacebookLaunch.performClick();
                break;
            case R.id.authNativeLogin:
                break;
            case R.id.authRegister:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbManager.onActivityResult(requestCode, resultCode, data);
    }
}
