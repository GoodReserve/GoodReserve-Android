/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project @kotohana5706
 * All rights reversed.
 */

package kr.edcan.rerant.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityRegisterBinding;
import kr.edcan.rerant.model.User;
import kr.edcan.rerant.utils.DataManager;
import kr.edcan.rerant.utils.NetworkHelper;
import kr.edcan.rerant.utils.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Call<User> registerUser;
    DataManager manager;
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        initDefault();
    }

    private void initDefault() {
        manager = new DataManager(getApplicationContext());
        binding.registerExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRegister();
            }
        });
    }

    private void checkRegister() {
        if (StringUtils.fullFilled(binding.registerEmail, binding.registerName, binding.registerPassword, binding.registerPhoneInput, binding.registerRePassword)) {
            if (!StringUtils.validEmail(binding.registerEmail)) {
                Toast.makeText(this, "올바른 이메일 형식을 입력해주세요!", Toast.LENGTH_SHORT).show();
            } else if (!StringUtils.checkPassword(binding.registerPassword, binding.registerRePassword)) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
            } else {
                registerUser = NetworkHelper.getNetworkInstance().registerUser(
                        binding.registerName.getText().toString().trim(), binding.registerPhoneInput.getText().toString().trim(),
                        binding.registerPassword.getText().toString(), binding.registerRePassword.getText().toString().trim(),
                        binding.registerEmail.getText().toString().trim()
                );
                registerUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        switch (response.code()) {
                            case 200:
                                Toast.makeText(RegisterActivity.this, "입력하신 정보로 가입되었습니다.\n로그인해주세요!.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                                break;
                            case 409:
                                Toast.makeText(RegisterActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(RegisterActivity.this, "서버와의 연동에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                Log.e("asdf", response.code() + "");
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "서버와의 연동에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("asdf", t.getMessage());

                    }
                });
            }
        } else Toast.makeText(RegisterActivity.this, "빈칸 없이 입력해주세요!", Toast.LENGTH_SHORT).show();
    }
}
