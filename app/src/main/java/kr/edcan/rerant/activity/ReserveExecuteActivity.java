/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project @kotohana5706
 * All rights reversed.
 */

package kr.edcan.rerant.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rey.material.app.Dialog;
import com.rey.material.widget.Slider;

import java.util.Date;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityReserveExecuteBinding;
import kr.edcan.rerant.databinding.ShoppingcartRecyclerContentBinding;
import kr.edcan.rerant.model.Reservation;
import kr.edcan.rerant.model.Restaurant;
import kr.edcan.rerant.utils.DataManager;
import kr.edcan.rerant.utils.NetworkHelper;
import kr.edcan.rerant.utils.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReserveExecuteActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityReserveExecuteBinding binding;
    CardView payNow, payLater;
    DataManager manager;

    int colorPrimary, personCount = 0;
    boolean isPayNow = true;
    Restaurant data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reserve_execute);
        initAppbarLayout();
        setDefault();
    }

    private void setDefault() {
        manager = new DataManager(getApplicationContext());
        final Call<Restaurant> getRestaurantInfo = NetworkHelper.getNetworkInstance().getRestaurantInfo(manager.getCurrentBucket().second.getRestaurantId());
        getRestaurantInfo.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                switch (response.code()) {
                    case 200:
                        data = response.body();
                        colorPrimary = getResources().getColor(R.color.colorPrimary);
                        payLater = binding.reserveByCash;
                        payNow = binding.reserveByCard;
                        payNow.setOnClickListener(ReserveExecuteActivity.this);
                        payLater.setOnClickListener(ReserveExecuteActivity.this);
                        setPayLayout(true);
                        setPersonCount(1);
                        binding.reservePersonSlider.setValueRange(0, data.getReservation_max(), true);
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
                        binding.reserveExecute.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new MaterialDialog.Builder(ReserveExecuteActivity.this)
                                        .title("확인해주세요!")
                                        .positiveColor(colorPrimary)
                                        .content(personCount + " 명으로 " + data.getName() + " 에 예약을 진행합니다.\n" +
                                                manager.getCurrentBucket().second.getMenus().get(0).getName() + " 포함 총 " + manager.getCurrentBucket().second.getMenus().size() + " 개 메뉴를 " + ((isPayNow) ? "선금결제" : "현장결제") + "로주문합니다.")
                                        .positiveText("예약하기")
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                Call<Reservation> generateReservation = NetworkHelper.getNetworkInstance().generateReservation(
                                                        manager.getCurrentBucket().second.getRestaurantId(),
                                                        manager.getActiveUser().second.get_id(),
                                                        new Date(System.currentTimeMillis()),
                                                        personCount,
                                                        (isPayNow)?0:1,
                                                        manager.getCurrentBucket().second.get_id(),
                                                        StringUtils.getTotalMoneyint(manager.getCurrentBucket().second.getMenus())
                                                );
                                                generateReservation.enqueue(new Callback<Reservation>() {
                                                    @Override
                                                    public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                                                        switch (response.code()){
                                                            case 200:
                                                                Toast.makeText(ReserveExecuteActivity.this, "예약이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                                                                startActivity(new Intent(getApplicationContext(), ReserveCompleteActivity.class).putExtra("id", response.body().get_id()));
                                                                finish();
                                                                ShoppingCartActivity.finishThis();
                                                                ReserveSearchActivity.finishThis();
                                                                ReserveSearchInfoActivity.finishThis();
                                                                manager.destroyAllBucket();
                                                                break;
                                                            default:
                                                                Log.e("asdf", response.code()+"");
                                                                Toast.makeText(ReserveExecuteActivity.this, "서버와의 연동에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                                                break;
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Reservation> call, Throwable t) {
                                                        Toast.makeText(ReserveExecuteActivity.this, "서버와의 연동에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                                        Log.e("asdf", t.getMessage()+"");

                                                    }
                                                });
                                            }
                                        })
                                        .show();
                            }
                        });
                        break;
                    default:
                        Toast.makeText(ReserveExecuteActivity.this, "서버와의 연동에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                Toast.makeText(ReserveExecuteActivity.this, "서버와의 연동에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("asdf", t.getMessage());

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

    public void setPersonCount(int newValue) {
        binding.reservePersonCount.setText(newValue + "명");
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
