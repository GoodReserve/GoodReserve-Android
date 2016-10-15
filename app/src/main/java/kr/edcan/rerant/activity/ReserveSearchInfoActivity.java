/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project @kotohana5706
 * All rights reversed.
 */

package kr.edcan.rerant.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.nitrico.lastadapter.LastAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityReserveSearchInfoBinding;

import com.github.nitrico.lastadapter.BR;

import kr.edcan.rerant.databinding.CommonListviewContentBinding;
import kr.edcan.rerant.model.CommonListData;
import kr.edcan.rerant.model.MainHeader;
import kr.edcan.rerant.model.ReserveBenefit;
import kr.edcan.rerant.model.ReserveMenu;
import kr.edcan.rerant.model.Restaurant;
import kr.edcan.rerant.utils.NetworkHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReserveSearchInfoActivity extends AppCompatActivity implements LastAdapter.LayoutHandler, LastAdapter.OnBindListener, LastAdapter.OnClickListener {

    Intent intent;
    String restaurant_id;
    ArrayList<Object> arrayList;
    Restaurant data;
    ActivityReserveSearchInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reserve_search_info);
        initAppbarLayout();
        setData();
    }

    private void initAppbarLayout() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        binding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedTitleStyle);
        binding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedTitleStyle);
        binding.collapsingToolbar.setExpandedTitleMargin(
                getResources().getDimensionPixelSize(R.dimen.common_titlemargin),
                0,
                0,
                getResources().getDimensionPixelSize(R.dimen.common_titlemargin)
        );
    }

    private void setData() {
        arrayList = new ArrayList<>();
        intent = getIntent();
        restaurant_id = intent.getStringExtra("restaurant_id");
        binding.reserveInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Call<Restaurant> getRestaurantInfo = NetworkHelper.getNetworkInstance().getRestaurantInfo(restaurant_id);
        getRestaurantInfo.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                switch (response.code()) {
                    case 200:
                        data = response.body();
                        initUI();
                        break;
                    default:
                        Toast.makeText(ReserveSearchInfoActivity.this, "서버와의 연동에 문제가 발생했습니다!", Toast.LENGTH_SHORT).show();
                        Log.e("asdf", response.code() + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                Toast.makeText(ReserveSearchInfoActivity.this, "서버와의 연동에 문제가 발생했습니다!", Toast.LENGTH_SHORT).show();
                Log.e("asdf", t.getMessage());
            }
        });

    }

    private void initUI() {
        int currentStatus = data.getReservation_check();
        int maxTime = data.getReservation_cancel();
        String number = data.getPhone();
        String location = data.getAddress();
        String title = data.getName();
        getSupportActionBar().setTitle(title);
        arrayList.add(new CommonListData("현재 예약 " + ((currentStatus == 0) ? "가능" : "불가"), "지금 예약을 요청할 수 " + ((currentStatus == 0) ? "있습니다." : "없습니다."), R.drawable.ic_reservesebu_reservestatus));
        arrayList.add(new CommonListData((maxTime / 60) + "시간 내에 예약 취소 가능", "이후 예약 취소 시 패널티가 발생할 수 있습니다.", R.drawable.ic_reservesebu_reservecancel));
        arrayList.add(new CommonListData(number, "음식점에 전화로 문의할수 있습니다.", R.drawable.ic_reservesebu_call));
        arrayList.add(new CommonListData(location, "", R.drawable.ic_reservesebu_location));
        arrayList.add(new MainHeader("메뉴", "식사할 메뉴를 선택해 주세요."));
        arrayList.add(new ReserveMenu("", "한글로 드 스테이크", 40620));
        arrayList.add(new MainHeader("혜택", "예약이 성사될 시 제공되는 혜택입니다."));
        arrayList.add(new ReserveBenefit("10%", "한글로 드 스테이크 메뉴를 주문 시 한글로 드 스테이크 올 그란디움 시크릿 베이크드로 업그레이드 제공."));
        LastAdapter.with(arrayList, BR.item)
                .map(CommonListData.class, R.layout.common_listview_content)
                .map(MainHeader.class, R.layout.main_first_header)
                .layoutHandler(this)
                .onBindListener(this)
                .onClickListener(this)
                .into(binding.reserveInfoRecyclerView);
    }


    @Override
    public int getItemLayout(@NotNull Object item, int i) {
        if (item instanceof CommonListData)
            return R.layout.common_listview_content;
        else if (item instanceof MainHeader)
            return R.layout.main_recycler_header;
        else if (item instanceof ReserveMenu) return R.layout.reserve_searchinfo_menu_content;
        else if (item instanceof ReserveBenefit) return R.layout.reserve_searchinfo_menu_benefit;
        return 0;
    }

    @Override
    public void onBind(@NotNull Object o, @NotNull View view, int type, int pos) {
        switch (type) {
            case R.layout.common_listview_content:
                CommonListData data = (CommonListData) arrayList.get(pos);
                CommonListviewContentBinding binding = DataBindingUtil.getBinding(view);
                binding.commonListViewIcon.setVisibility(View.VISIBLE);
                binding.commonListViewIcon.setImageResource(data.getIcon());
                binding.commonListViewTitle.setText(data.getTitle());
                binding.commonListViewContent.setText(data.getContent());
                binding.commonListViewTitle.setTextColor(Color.BLACK);
                if (data.getContent().trim().equals(""))
                    binding.commonListViewContent.setVisibility(View.GONE);
                break;
        }
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
    public void onClick(@NotNull Object o, @NotNull View view, int type, int position) {
        Log.e("asdf", "asdf" + position);
    }
}
