/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project @kotohana5706
 * All rights reversed.
 */

package kr.edcan.rerant.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.github.nitrico.lastadapter.LastAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityReserveSearchInfoBinding;

import com.github.nitrico.lastadapter.BR;

import kr.edcan.rerant.model.CommonListData;


public class ReserveSearchInfoActivity extends AppCompatActivity implements LastAdapter.LayoutHandler, LastAdapter.OnBindListener {

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
    }

    private void setDefault() {
        binding.reserveInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.bg_login));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.bg_login));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.bg_login));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.bg_login));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.bg_login));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.bg_login));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.bg_login));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.bg_login));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.bg_login));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.bg_login));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.bg_login));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.bg_login));
        arrayList.add(new CommonListData("asdf", "asdf", R.drawable.bg_login));
        LastAdapter.with(arrayList, BR.item)
                .map(CommonListData.class, R.layout.common_listview_content)
                .layoutHandler(this)
                .onBindListener(this)
                .into(binding.reserveInfoRecyclerView);
    }

    @Override
    public int getItemLayout(@NotNull Object item, int i) {
        return R.layout.common_listview_content;
    }

    @Override
    public void onBind(@NotNull Object o, @NotNull View view, int type, int pos) {
        switch (type) {
            case R.layout.common_listview_content:

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
}
