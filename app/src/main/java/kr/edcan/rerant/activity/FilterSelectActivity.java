/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project @kotohana5706
 * All rights reversed.
 */

package kr.edcan.rerant.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityFilterSelectBinding;

public class FilterSelectActivity extends AppCompatActivity {

    ActivityFilterSelectBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_select);
        initAppbarLayout();
    }

    private void initAppbarLayout() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setBackgroundColor(Color.WHITE);
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setTitle("필터");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
