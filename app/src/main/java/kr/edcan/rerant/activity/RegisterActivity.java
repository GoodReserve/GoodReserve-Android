/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project @kotohana5706
 * All rights reversed.
 */

package kr.edcan.rerant.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityRegisterBinding;
import kr.edcan.rerant.utils.StringUtils;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        initDefault();
    }

    private void initDefault() {
        binding.registerExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRegister();
            }
        });
    }

    private void checkRegister() {
        if (!StringUtils.validEmail(binding.registerEmail)) {
            Toast.makeText(this, "올바른 이메일 형식을 입력해주세요!", Toast.LENGTH_SHORT).show();
        } else if(!StringUtils.checkPassword(binding.registerPassword, binding.registerRePassword)){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "회원가입", Toast.LENGTH_SHORT).show();
        }
    }
}
