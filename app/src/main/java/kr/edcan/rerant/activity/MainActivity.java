/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project @kotohana5706
 * All rights reversed.
 */

package kr.edcan.rerant.activity;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import kr.edcan.rerant.views.RoundImageView;


public class MainActivity extends AppCompatActivity implements LastAdapter.OnClickListener, LastAdapter.OnBindListener, LastAdapter.LayoutHandler {

    final static int PAGER_SCROLL_DELAY = 3000;
    ActivityMainBinding binding;
    ArrayList<Object> mainContentList;
    ArrayList<Restaurant> headerList;
    ArrayList<RoundImageView> indicatorArr;
    PagerAdapterClass pageAdapter;
    Handler viewPagerHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        binding.toolbar.setBackgroundColor(Color.WHITE);
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
        mainContentList.add(new MainHeader("찜한 음식점 리스트", "찜한 음식점에 따른 예약이 취소될 경우 빠르게 예약할 수 있습니다."));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));
        mainContentList.add(new MainContent("REZRO-4062", "종현이네 원조 보쌈 24시", "1일 23시간 43분 남음"));

        headerList = new ArrayList<>();
        headerList.add(new Restaurant("미스터 피자 어쩌고 저쩌고", "asdf", "일하기 싫다 아아아아ㅏ앙아ㅏ아아ㅏㄹㄱ"));
        headerList.add(new Restaurant("창림식 스웩 어쩌고 저꺼", "asdf", "일하기 싫다 아아아아ㅏ앙아ㅏ아아ㅏㄹㄱ"));
        headerList.add(new Restaurant("창림식 스웩 어쩌고 저꺼", "asdf", "일하기 싫다 아아아아ㅏ앙아ㅏ아아ㅏㄹㄱ"));
        headerList.add(new Restaurant("창림식 스웩 어쩌고 저꺼", "asdf", "일하기 싫다 아아아아ㅏ앙아ㅏ아아ㅏㄹㄱ"));
        headerList.add(new Restaurant("창림식 스웩 어쩌고 저꺼", "asdf", "일하기 싫다 아아아아ㅏ앙아ㅏ아아ㅏㄹㄱ"));
        pageAdapter = new PagerAdapterClass(getApplicationContext());
    }

    private void initUI() {
        getSupportActionBar().setLogo(R.drawable.title_actionbar_main);
        binding.mainRecycler.setVisibility(View.VISIBLE);
        binding.progressLoading.setVisibility(View.GONE);
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
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

    private void setViewPagerScroll(final ViewPager pager) {
        viewPagerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pager.setCurrentItem((pager.getCurrentItem() + 1 < headerList.size()) ? pager.getCurrentItem() + 1 : 0, true);
                setViewPagerScroll(pager);
            }
        }, PAGER_SCROLL_DELAY);
    }

    private void setViewPagerIndicator(LinearLayout parent) {
        indicatorArr = new ArrayList<>();
        int pixels = getResources().getDimensionPixelSize(R.dimen.indicator_width);
        LinearLayout.LayoutParams indicatorParams = new LinearLayout.LayoutParams(pixels, pixels);
        indicatorParams.setMargins(pixels / 2, 0, 0, 0);
        for (int i = 0; i < headerList.size(); i++) {
            RoundImageView view = new RoundImageView(getApplicationContext());
            view.setLayoutParams(indicatorParams);
            view.setImageDrawable(new ColorDrawable(Color.WHITE));
            parent.addView(view);
            indicatorArr.add(view);
        }
        updateViewPagerIndicator(0);
    }

    private void updateViewPagerIndicator(int currentItem) {
        for (int i = 0; i < indicatorArr.size(); i++) {
            if (i == currentItem) indicatorArr.get(i).setImageResource(R.color.indicatorPrimary);
            else indicatorArr.get(i).setImageResource(R.color.indicatorSub);
        }
    }

    @Override
    public void onClick(@NotNull Object o, @NotNull View view, int i, int i1) {

    }

    @Override
    public void onBind(@NotNull Object o, @NotNull View view, int type, int position) {
        switch (type) {
            case R.layout.main_first_header:
                MainFirstHeaderBinding binding = DataBindingUtil.getBinding(view);
                if (binding.viewPager.getAdapter() == null) {
                    binding.viewPager.setAdapter(pageAdapter);
                    setViewPagerScroll(binding.viewPager);
                    setViewPagerIndicator(binding.indicatorParent);
                    binding.viewPager.addOnPageChangeListener(pageListener);
                }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_mypage:
                startActivity(new Intent(getApplicationContext(), MyPageActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            updateViewPagerIndicator(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
}
