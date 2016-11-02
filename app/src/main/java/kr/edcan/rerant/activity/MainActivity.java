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
import android.net.Network;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.nitrico.lastadapter.LastAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import kr.edcan.rerant.BR;
import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityMainBinding;
import kr.edcan.rerant.databinding.MainFirstHeaderBinding;
import kr.edcan.rerant.databinding.MainHeaderViewpagerLayoutBinding;
import kr.edcan.rerant.databinding.MainRecyclerContentBinding;
import kr.edcan.rerant.model.MainContent;
import kr.edcan.rerant.model.MainHeader;
import kr.edcan.rerant.model.MainTopHeader;
import kr.edcan.rerant.model.Reservation;
import kr.edcan.rerant.model.Restaurant;
import kr.edcan.rerant.model.User;
import kr.edcan.rerant.utils.DataManager;
import kr.edcan.rerant.utils.ImageSingleTon;
import kr.edcan.rerant.utils.NetworkHelper;
import kr.edcan.rerant.utils.NetworkInterface;
import kr.edcan.rerant.utils.StringUtils;
import kr.edcan.rerant.views.RoundImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements LastAdapter.OnClickListener, LastAdapter.OnBindListener, LastAdapter.LayoutHandler, View.OnClickListener {

    final static int PAGER_SCROLL_DELAY = 3000;
    ActivityMainBinding binding;
    ArrayList<Object> mainContentList;
    ArrayList<Restaurant> headerList;
    ArrayList<RoundImageView> indicatorArr;
    PagerAdapterClass pageAdapter;
    Handler viewPagerHandler = new Handler();
    DataManager manager;
    NetworkInterface service;
    Reservation reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        binding.toolbar.setBackgroundColor(Color.WHITE);
        binding.progressLoading.startAnimation();
        checkAuthStatus();
    }

    private void checkAuthStatus() {
        manager = new DataManager(this);
        service = NetworkHelper.getNetworkInstance();
        Pair<Boolean, User> userPair = manager.getActiveUser();
        if (!userPair.first) {
            startActivity(new Intent(getApplicationContext(), AuthActivity.class));
            finish();
        } else {
            // validate
            String token = userPair.second.getAuth_token();
            switch (userPair.second.getUserType()) {
                case 0:
                    Call<User> facebookLogin = NetworkHelper.getNetworkInstance().facebookLogin(token);
                    facebookLogin.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast.makeText(MainActivity.this, response.body().getName() + " 님 안녕하세요!", Toast.LENGTH_SHORT).show();
                                    manager.saveUserInfo(response.body(), 0);
                                    setRecommendData();
                                    break;
                                default:
                                    Toast.makeText(MainActivity.this, "로그인된 계정의 세션이 만료되어, 다시 로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                                    Log.e("asdf", response.code() + "");
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "서버와의 연결에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                            finish();
                            Log.e("asdf", t.getMessage());
                        }
                    });
                    break;
                case 1:
                    Call<User> authenticateUser = NetworkHelper.getNetworkInstance().authenticateUser(token);
                    authenticateUser.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast.makeText(MainActivity.this, response.body().getName() + " 님 안녕하세요!", Toast.LENGTH_SHORT).show();
                                    manager.saveUserInfo(response.body(), 1);
                                    setRecommendData();
                                    break;
                                default:
                                    Toast.makeText(MainActivity.this, "로그인된 계정의 세션이 만료되어, 다시 로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                                    Log.e("asdf", response.code() + "");
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "서버와의 연결에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                            finish();
                            Log.e("asdf", t.getMessage());
                        }
                    });
                    break;
            }
        }
    }

    private void setRecommendData() {
        headerList = new ArrayList<>();
        Call<ArrayList<Restaurant>> getRestaurantList = NetworkHelper.getNetworkInstance().getRestaurantList();
        getRestaurantList.enqueue(new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                switch (response.code()) {
                    case 200:
                        for (int i = 0; i < ((response.body().size() < 5) ? response.body().size() : 5); i++) {
                            headerList.add(response.body().get(i));
                            Log.e("asdf", response.body().get(i).getName());
                        }
                        pageAdapter = new PagerAdapterClass(getApplicationContext());
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "서버와의 연결에 문제가 있습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("asdf", response.code() + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "서버와의 연결에 문제가 있습니다.", Toast.LENGTH_SHORT).show();
                Log.e("asdf", t.getMessage());
            }
        });
        setReservationData();
    }

    private void setReservationData() {
        mainContentList = new ArrayList<>();
        mainContentList.add(new MainTopHeader());
        Call<ArrayList<Reservation>> getMyReservation = NetworkHelper.getNetworkInstance().getMyReservation(manager.getActiveUser().second.get_id());
        getMyReservation.enqueue(new Callback<ArrayList<Reservation>>() {
            @Override
            public void onResponse(Call<ArrayList<Reservation>> call, Response<ArrayList<Reservation>> response) {
                switch (response.code()) {
                    case 200:
                        if (response.body().size() >= 1) {
                            mainContentList.add(new MainHeader("곧 예약시간에 도달", "아래의 음식점에 예약한 시간이 얼마 남지 않았습니다."));
                            reservation = response.body().get(0);
                            mainContentList.add(new MainContent(reservation.getReservation_code(), reservation.getRestaurant_name(), reservation.getReservation_time().toLocaleString()));
                        } else mainContentList.add("");
                        initUI();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "서버와의 연결에 문제가 있습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("asdf", response.code() + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Reservation>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "서버와의 연결에 문제가 있습니다.", Toast.LENGTH_SHORT).show();
                Log.e("asdf1", t.getMessage());
            }
        });

    }

    private void initUI() {
        getSupportActionBar().setLogo(R.drawable.title_actionbar_main);
        binding.mainRecycler.setVisibility(View.VISIBLE);
        binding.progressLoading.setVisibility(View.GONE);
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        binding.bottomBar.setVisibility(View.VISIBLE);
        binding.mainRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.bottomReserveHistory.setOnClickListener(this);
        binding.bottomReserveLaunch.setOnClickListener(this);
        LastAdapter.with(mainContentList, BR.item)
                .layoutHandler(this)
                .map(MainTopHeader.class, R.layout.main_first_header)
                .map(MainHeader.class, R.layout.main_recycler_header)
                .map(MainContent.class, R.layout.main_recycler_content)
                .map(String.class, R.layout.main_recycler_blank)
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
                    binding.viewPager.setOffscreenPageLimit(5);
                    binding.viewPager.setAdapter(pageAdapter);
                    setViewPagerScroll(binding.viewPager);
                    setViewPagerIndicator(binding.indicatorParent);
                    binding.viewPager.addOnPageChangeListener(pageListener);
                }
                break;
            case R.layout.main_recycler_content:
                MainRecyclerContentBinding contentBinding = DataBindingUtil.getBinding(view);
                contentBinding.mainContentDetailExecute.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ReserveCompleteActivity.class).putExtra("id", reservation.get_id()));
                    }
                });
                contentBinding.mainContentAlertExecute.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shareText(
                                manager.getActiveUser().second.getName() + " 님이 품격있는 외식 문화의 시작, Rerant을 통해 " +
                                        reservation.getRestaurant_name() + "에서 예약했습니다. 더 많은 정보를 보려면 아래 링크를 클릭해 주세요. http://goo.gl/rerantdownload"
                        );

                    }
                });
        }
    }

    @Override
    public int getItemLayout(@NotNull Object item, int i) {
        if (item instanceof MainHeader) return R.layout.main_recycler_header;
        else if (item instanceof MainTopHeader) return R.layout.main_first_header;
        else if (item instanceof String) return R.layout.main_recycler_blank;
        else return R.layout.main_recycler_content;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottomReserveHistory:
                startActivity(new Intent(getApplicationContext(), ReserveLogActivity.class));
                break;
            case R.id.bottomReserveLaunch:
                startActivity(new Intent(getApplicationContext(), ReserveSearchActivity.class));
                break;
        }
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
            binding.viewPagerImage.setImageUrl(StringUtils.getFullImageUrl(data.getThumbnail()), ImageSingleTon.getInstance(MainActivity.this).getImageLoader());
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

    private void shareText(String s) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, s);
        startActivity(Intent.createChooser(sharingIntent, "예약 내용 공유하기"));
    }
}

