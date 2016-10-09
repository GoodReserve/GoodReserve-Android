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
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.nitrico.lastadapter.BR;
import com.github.nitrico.lastadapter.LastAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityReserveSearchBinding;
import kr.edcan.rerant.databinding.MainFirstHeaderBinding;
import kr.edcan.rerant.databinding.ReserveRecyclerContentBinding;
import kr.edcan.rerant.model.MainHeader;
import kr.edcan.rerant.model.MainTopHeader;
import kr.edcan.rerant.model.Restaurant;
import kr.edcan.rerant.views.CartaTagView;

public class ReserveSearchActivity extends AppCompatActivity implements LastAdapter.LayoutHandler, LastAdapter.OnBindListener {

    ActivityReserveSearchBinding binding;
    ArrayList<Restaurant> arrayList = new ArrayList<>();
    int colorPrimary, colorSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reserve_search);
        initAppbarLayout();
        setDefault();
        setData();
    }

    private void setData() {
        arrayList.add(new Restaurant("레스토랑 드 태윤 프리미엄", "", "앗 앗 앗"));
        arrayList.add(new Restaurant("레스토랑 드 태윤 프리미엄", "", "앗 앗 앗"));
        arrayList.add(new Restaurant("레스토랑 드 태윤 프리미엄", "", "앗 앗 앗"));
        arrayList.add(new Restaurant("레스토랑 드 태윤 프리미엄", "", "앗 앗 앗"));
        arrayList.add(new Restaurant("레스토랑 드 태윤 프리미엄", "", "앗 앗 앗"));
        arrayList.add(new Restaurant("레스토랑 드 태윤 프리미엄", "", "앗 앗 앗"));
        arrayList.add(new Restaurant("레스토랑 드 태윤 프리미엄", "", "앗 앗 앗"));
        arrayList.add(new Restaurant("레스토랑 드 태윤 프리미엄", "", "앗 앗 앗"));
        binding.reserveSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        LastAdapter.with(arrayList, BR.item)
                .map(Restaurant.class, R.layout.reserve_recycler_content)
                .layoutHandler(this)
                .onBindListener(this)
                .into(binding.reserveSearchRecyclerView);
    }

    private void setDefault() {
        colorPrimary = getResources().getColor(R.color.colorPrimary);
        colorSub = getResources().getColor(R.color.textColorSub);
        String[] items = new String[]{"등록일 순", "예약자 비율 순", "예약 만료일 순"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.row_spn, items);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        binding.searchFilterSpinner.setAdapter(adapter);
    }

    private void initAppbarLayout() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setBackgroundColor(Color.WHITE);
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setTitle("예약하기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getItemLayout(@NotNull Object o, int i) {
        return R.layout.reserve_recycler_content;
    }

    @Override
    public void onBind(@NotNull Object o, @NotNull View view, int type, int position) {
        switch (type) {
            case R.layout.reserve_recycler_content:
                ReserveRecyclerContentBinding binding = DataBindingUtil.getBinding(view);
                setCurrentStatus(binding.reserveResCardStatus, false);
                binding.reserveResCardShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ReserveSearchActivity.this, "공유!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    public void setCurrentStatus(CartaTagView currentStatus, boolean canReserve) {
        currentStatus.setText((canReserve) ? "예약 가능" : "모두 예약됨");
        currentStatus.setTextColorForceFully((canReserve) ? colorPrimary : colorSub);
    }
}
