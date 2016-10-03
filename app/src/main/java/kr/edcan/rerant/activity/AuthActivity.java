package kr.edcan.rerant.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAuthBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
        initDefault();
    }

    private void initDefault() {
        binding.authFacebookLogin.setOnClickListener(this);
        binding.authNativeLogin.setOnClickListener(this);
        binding.authRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.authFacebookLogin:
                break;
            case R.id.authNativeLogin:
                break;
            case R.id.activity_register:
                break;
        }
    }
}
