/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project @kotohana5706
 * All rights reversed.
 */

package kr.edcan.rerant.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import kr.edcan.rerant.model.Reservation;
import kr.edcan.rerant.model.Restaurant;
import kr.edcan.rerant.utils.NetworkHelper;
import kr.edcan.rerant.views.CartaTagView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReserveSearchActivity extends AppCompatActivity implements LastAdapter.LayoutHandler, LastAdapter.OnBindListener, LastAdapter.OnClickListener {


    private static final int FILTER_INTENT_CODE = 6974;
    ActivityReserveSearchBinding binding;
    ArrayList<Restaurant> arrayList = new ArrayList<>();
    int colorPrimary, colorSub;
    String menu[] = {"한식", "양식", "중식", "일식"};
    String meeting[] = {"회식", "회의", "커플", "가족"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reserve_search);
        initAppbarLayout();
        setDefault();
        setData();
    }

    private void setData() {
        Call<ArrayList<Restaurant>> getRestaurantList = NetworkHelper.getNetworkInstance().getRestaurantList();
        getRestaurantList.enqueue(new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                switch (response.code()) {
                    case 200:
                        for (Restaurant restaurant : response.body()) {
                            arrayList.add(restaurant);
                        }
                        initUI();
                        break;
                    default:
                        Toast.makeText(ReserveSearchActivity.this, "서버와의 연결에 문제가 있습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("asdf", response.code() + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {
                Toast.makeText(ReserveSearchActivity.this, "서버와의 연결에 문제가 있습니다.", Toast.LENGTH_SHORT).show();
                Log.e("asdf", t.getMessage());
            }
        });
    }

    private void initUI() {
        binding.reserveSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        LastAdapter.with(arrayList, BR.item)
                .map(Restaurant.class, R.layout.reserve_recycler_content)
                .layoutHandler(this)
                .onBindListener(this)
                .onClickListener(this)
                .into(binding.reserveSearchRecyclerView);
    }

    private void setDefault() {
        colorPrimary = getResources().getColor(R.color.colorPrimary);
        colorSub = getResources().getColor(R.color.textColorSub);
        String[] items = new String[]{"등록일 순", "예약자 비율 순", "예약 만료일 순"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.row_spn, items);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        binding.searchFilterSpinner.setAdapter(adapter);
        binding.searchFilterSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), FilterSelectActivity.class), FILTER_INTENT_CODE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILTER_INTENT_CODE:
                if (resultCode == RESULT_OK) {
                    binding.searchTagParent.setVisibility(View.VISIBLE);
                    setFilter(data.getIntExtra("menuType", 0)
                            , data.getIntExtra("meetingType", 0));
                } else binding.searchTagParent.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setFilter(int menuType, int meetingType) {
        binding.menuType.setText(menu[menuType]);
        binding.meetingType.setText(meeting[meetingType]);
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
                Restaurant data = (Restaurant) o;
                ReserveRecyclerContentBinding binding = DataBindingUtil.getBinding(view);
                setCurrentStatus(binding.reserveResCardStatus, (data.getReservation_check() == 0));
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

    @Override
    public void onClick(@NotNull Object o, @NotNull View view, int type, int position) {
        switch (type) {
            case R.layout.reserve_recycler_content:
                startActivity(new Intent(getApplicationContext(), ReserveSearchInfoActivity.class)
                        .putExtra("restaurant_id", arrayList.get(position).get_id()));
                break;
        }
    }
}
