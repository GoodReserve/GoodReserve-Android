/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project @kotohana5706
 * All rights reversed.
 */

package kr.edcan.rerant.activity;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.nitrico.lastadapter.BR;
import com.github.nitrico.lastadapter.LastAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityMainBinding;
import kr.edcan.rerant.databinding.MainFirstHeaderBinding;
import kr.edcan.rerant.databinding.MainHeaderViewpagerLayoutBinding;
import kr.edcan.rerant.model.MainContent;
import kr.edcan.rerant.model.MainHeader;
import kr.edcan.rerant.model.MainTopHeader;
import kr.edcan.rerant.model.Restaurant;


public class MainActivity extends AppCompatActivity implements LastAdapter.OnClickListener, LastAdapter.OnBindListener, LastAdapter.LayoutHandler {

    ActivityMainBinding binding;
    ArrayList<Object> mainContentList;
    ArrayList<Restaurant> headerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        startActivity(new Intent(getApplicationContext(), AuthActivity.class));
//        finish();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        binding.progressLoading.startAnimation();
        setData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initUI();
            }
        }, 600);
    }

    private void setData() {
        mainContentList = new ArrayList<>();
        mainContentList.add(new MainTopHeader());
        mainContentList.add(new MainHeader("곧 예약시간에 도달", "아래의 음식점에 예약한 시간이 얼마 남지 않았습니다."));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));

        headerList = new ArrayList<>();
        headerList.add(new Restaurant("1", "미스터 피자 어쩌고 저쩌고", "일하기 싫다 아아아아ㅏ앙아ㅏ아아ㅏㄹㄱ"));
        headerList.add(new Restaurant("2", "창림식 스웩 어쩌고 저꺼", "일하기 싫다 아아아아ㅏ앙아ㅏ아아ㅏㄹㄱ"));
    }

    private void initUI() {
        getSupportActionBar().setTitle("GoodReserve");
        binding.mainRecycler.setVisibility(View.VISIBLE);
        binding.progressLoading.setVisibility(View.GONE);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.mainRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LastAdapter.with(mainContentList, BR.item)
                .layoutHandler(this)
                .map(MainTopHeader.class, R.layout.main_first_header)
                .map(MainHeader.class, R.layout.main_recycler_header)
                .map(MainContent.class, R.layout.main_recycler_content)
                .onClickListener(this)
                .onBindListener(this)
                .into(binding.mainRecycler);
    }

    @Override
    public void onClick(@NotNull Object o, @NotNull View view, int i, int i1) {

    }

    @Override
    public void onBind(@NotNull Object o, @NotNull View view, int type, int position) {
        switch (type) {
            case R.layout.main_first_header:
                MainFirstHeaderBinding binding = DataBindingUtil.getBinding(view);
                binding.viewPager.setAdapter(new PagerAdapterClass(getApplicationContext()));
                break;
        }
    }

    @Override
    public int getItemLayout(@NotNull Object item, int i) {
        if (item instanceof MainHeader) return R.layout.main_recycler_header;
        else if (item instanceof MainTopHeader) return R.layout.main_first_header;
        else return R.layout.main_recycler_content;
    }

    /**
     * PagerAdapter
     */
    private class PagerAdapterClass extends PagerAdapter {

        public PagerAdapterClass(Context c) {
            super();
        }

        @Override
        public int getCount() {
            return headerList.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            MainHeaderViewpagerLayoutBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.main_header_viewpager_layout, container, false);
            Restaurant data = headerList.get(position);
            binding.viewPagerResName.setText(data.getName());
            binding.viewPagerResLocation.setText(data.getAddress());
            binding.viewPagerImage.setImageResource(R.drawable.pusheen);
            binding.viewPagerImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(binding.getRoot(), 0);
            return binding.getRoot();
        }


        @Override
        public void destroyItem(View pager, int position, Object view) {
            ((ViewPager) pager).removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View pager, Object obj) {
            return pager == obj;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }
    }
}
