/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project @kotohana5706
 * All rights reversed.
 */

package kr.edcan.rerant.activity;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(getApplicationContext(), MyPageActivity.class));
        finish();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        binding.progressLoading.startAnimation();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                initUI();
//            }
//        }, 2000);
    }
    private void initUI(){
        getSupportActionBar().setTitle("GoodReserve");
        binding.progressComplete.setVisibility(View.VISIBLE);
        binding.progressLoading.setVisibility(View.GONE);
        binding.toolbar.setTitleTextColor(Color.WHITE);
    }
}
