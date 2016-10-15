package kr.edcan.rerant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityAuthBinding;
import kr.edcan.rerant.model.User;
import kr.edcan.rerant.utils.DataManager;
import kr.edcan.rerant.utils.NetworkHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    public static Activity activity;
    public static void finishThis() {
        if (activity != null) activity.finish();
    }

    private Call<User> facebookLogin;
    private DataManager manager;
    CallbackManager fbManager;
    ActivityAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
        initDefault();
    }

    private void initDefault() {
        fbManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();
        setFacebookCallback();
        binding.authFacebookLogin.setOnClickListener(this);
        binding.authNativeLogin.setOnClickListener(this);
        binding.authRegister.setOnClickListener(this);
    }

    private void setFacebookCallback() {
        LoginManager.getInstance().registerCallback(fbManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                facebookLogin = NetworkHelper.getNetworkInstance().facebookLogin(loginResult.getAccessToken().getToken());
                facebookLogin.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        switch (response.code()) {
                            case 200:
                                manager.saveUserInfo(response.body(), 0);
                                manager.saveFacebookCredential(loginResult.getAccessToken().getToken());
                                break;
                            default:
                                Toast.makeText(AuthActivity.this, "인증오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(AuthActivity.this, "인증오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("asdf", t.getMessage());
                    }
                });
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
        switch (view.getId()) {
            case R.id.authFacebookLogin:
                binding.authFacebookLaunch.performClick();
                break;
            case R.id.authNativeLogin:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
