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
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.github.nitrico.lastadapter.BR;
import com.github.nitrico.lastadapter.LastAdapter;
import com.rey.material.widget.Slider;

import java.util.ArrayList;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityReserveCompleteBinding;
import kr.edcan.rerant.databinding.ActivityReserveExecuteBinding;
import kr.edcan.rerant.model.Menu;
import kr.edcan.rerant.model.ReserveFooter;

public class ReserveExecuteActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityReserveExecuteBinding binding;
    CardView payNow, payLater;

    int colorPrimary, personCount = 0;
    boolean isPayNow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reserve_execute);
        initAppbarLayout();
        setDefault();
    }

    private void setDefault() {
        colorPrimary = getResources().getColor(R.color.colorPrimary);
        payLater = binding.reserveByCash;
        payNow = binding.reserveByCard;
        payNow.setOnClickListener(this);
        payLater.setOnClickListener(this);
        setPayLayout(true);
        setPersonCount(1);
        binding.reserveDatePickerExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DatePickerDialog0
            }
        });
        binding.reservePersonSlider.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                setPersonCount(newValue);
            }
        });
    }

    private void initAppbarLayout() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setBackgroundColor(Color.WHITE);
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setTitle("예약하기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setPayLayout(boolean payNow) {
        isPayNow = payNow;
        binding.reserveByCard.setCardBackgroundColor((payNow) ? colorPrimary : Color.WHITE);
        binding.reserveByCash.setCardBackgroundColor((payNow) ? Color.WHITE : colorPrimary);
        binding.reserveByCardTitle.setTextColor((payNow) ? Color.WHITE : colorPrimary);
        binding.reserveByCardSubTitle.setTextColor((payNow) ? Color.WHITE : colorPrimary);
        binding.reserveByCashTitle.setTextColor((payNow) ? colorPrimary : Color.WHITE);
        binding.reserveByCashSubTitle.setTextColor((payNow) ? colorPrimary : Color.WHITE);
    }
    public void setPersonCount(int newValue){
        binding.reservePersonCount.setText(newValue+"명");
        personCount = newValue;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reserveByCard:
                setPayLayout(true);
                break;
            case R.id.reserveByCash:
                setPayLayout(false);
                break;

        }
    }


}
