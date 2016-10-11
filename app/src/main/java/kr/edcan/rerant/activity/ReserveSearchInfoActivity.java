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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.github.nitrico.lastadapter.LastAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityReserveSearchInfoBinding;

import com.github.nitrico.lastadapter.BR;

import kr.edcan.rerant.databinding.CommonListviewContentBinding;
import kr.edcan.rerant.model.CommonListData;
import kr.edcan.rerant.model.MainHeader;
import kr.edcan.rerant.model.ReserveFooter;
import kr.edcan.rerant.model.ReserveMenu;


public class ReserveSearchInfoActivity extends AppCompatActivity implements LastAdapter.LayoutHandler, LastAdapter.OnBindListener, LastAdapter.OnClickListener {

    ArrayList<Object> arrayList;
    ActivityReserveSearchInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reserve_search_info);
        initAppbarLayout();
        setDefault();
    }

    private void initAppbarLayout() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("레스토랑 드 세그");
        getSupportActionBar().setSubtitle("양식");
        binding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedTitleStyle);
        binding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedTitleStyle);
        binding.collapsingToolbar.setExpandedTitleMargin(
                getResources().getDimensionPixelSize(R.dimen.common_titlemargin),
                0,
                0,
                getResources().getDimensionPixelSize(R.dimen.common_titlemargin)
        );
    }

    private void setDefault() {
        binding.reserveInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        arrayList.add(new CommonListData("현재 예약 가능", "지금 예약을 요청할 수 있습니다.", R.drawable.ic_reservesebu_reservestatus));
        arrayList.add(new CommonListData("2시간 내에 예약 취소 가능", "이후 예약 취소 시 패널티가 발생할 수 있습니다.", R.drawable.ic_reservesebu_reservecancel));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.ic_reservesebu_call));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.ic_reservesebu_location));
        arrayList.add(new MainHeader("메뉴", "식사할 메뉴를 선택해 주세요."));

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
        else if(item instanceof MainHeader)
            return R.layout.main_recycler_header;
        else if(item instanceof ReserveMenu) return R.layout.reserve_searchinfo_menu_content;
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
                break;
            case R.layout.main_first_header:
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
    }3
}
