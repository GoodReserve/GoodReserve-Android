package kr.edcan.rerant.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityLoginBinding;
import kr.edcan.rerant.model.User;
import kr.edcan.rerant.utils.DataManager;
import kr.edcan.rerant.utils.NetworkHelper;
import kr.edcan.rerant.utils.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    DataManager manager;
    ActivityLoginBinding binding;
    Call<User> userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initDefault();
    }

    private void initDefault() {
        manager = new DataManager(getApplicationContext());
        binding.loginExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }

    private void checkLogin() {
        if (!StringUtils.validEmail(binding.loginEmail)) {
            Toast.makeText(this, "올바른 이메일 형식을 입력해주세요!", Toast.LENGTH_SHORT).show();
        } else if (!StringUtils.fullFilled(binding.loginEmail, binding.loginPassword)) {
            Toast.makeText(this, "빈칸 없이 입력해주세요!", Toast.LENGTH_SHORT).show();
        } else {
            userLogin = NetworkHelper.getNetworkInstance().nativeLogin(
                    binding.loginEmail.getText().toString().trim(), binding.loginPassword.getText().toString().trim()
            );
            userLogin.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    switch (response.code()) {
                        case 200:
                            manager.saveUserInfo(response.body(), 1);
                            Toast.makeText(LoginActivity.this, response.body().getName() + "님 안녕하세요!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            AuthActivity.finishThis();
                            finish();
                            break;
                        case 400:
                            Toast.makeText(LoginActivity.this, "이메일 주소 혹은 비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
                        default:
                            Log.e("asdf", response.code() + "");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("asdf", t.getMessage());
                }
            });
        }
    }
}
