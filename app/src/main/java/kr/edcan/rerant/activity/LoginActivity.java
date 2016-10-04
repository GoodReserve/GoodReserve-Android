package kr.edcan.rerant.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityLoginBinding;
import kr.edcan.rerant.utils.StringUtils;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initDefault();
    }
    private void initDefault() {
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
        } else if(!StringUtils.fullFilled(binding.loginEmail, binding.loginPassword)){
            Toast.makeText(this, "빈칸 없이 입력해주세요!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "회원가입", Toast.LENGTH_SHORT).show();
        }
    }

}
