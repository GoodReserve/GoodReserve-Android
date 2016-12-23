/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project @kotohana5706
 * All rights reversed.
 */

package kr.edcan.rerant.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.nitrico.lastadapter.LastAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import kr.edcan.rerant.BR;
import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityShoppingCartBinding;
import kr.edcan.rerant.databinding.ShoppingcartRecyclerContentBinding;
import kr.edcan.rerant.databinding.ShoppingcartRecyclerFooterBinding;
import kr.edcan.rerant.model.Bucket;
import kr.edcan.rerant.model.Menu;
import kr.edcan.rerant.model.ReserveFooter;
import kr.edcan.rerant.utils.DataManager;
import kr.edcan.rerant.utils.ImageSingleTon;
import kr.edcan.rerant.utils.NetworkHelper;
import kr.edcan.rerant.utils.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartActivity extends AppCompatActivity implements LastAdapter.LayoutHandler, LastAdapter.OnBindListener, LastAdapter.OnClickListener {

    public static Activity activity;

    public static void finishThis() {
        if (activity != null) activity.finish();
    }

    ArrayList<Object> arrayList;
    ActivityShoppingCartBinding binding;
    DataManager manager;
    Bucket bucket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart);
        initAppbarLayout();
        setDefault();
    }

    private void setDefault() {
        arrayList = new ArrayList<>();
        manager = new DataManager(getApplicationContext());
        final Call<Bucket> getBucketInfo = NetworkHelper.getNetworkInstance().getBucketInfo(manager.getCurrentBucket().second.get_id());
        getBucketInfo.enqueue(new Callback<Bucket>() {
            @Override
            public void onResponse(Call<Bucket> call, Response<Bucket> response) {
                switch (response.code()) {
                    case 200:
                        manager.saveCurrentBucket(response.body(), manager.getCurrentBucket().second.getRestaurantId());
                        bucket = response.body();
                        for (Menu m : response.body().getMenus()) {
                            arrayList.add(m);
                        }
                        arrayList.add(new ReserveFooter());
                        LastAdapter.with(arrayList, BR.item)
                                .layoutHandler(ShoppingCartActivity.this)
                                .map(Menu.class, R.layout.shoppingcart_recycler_content)
                                .map(ReserveFooter.class, R.layout.shoppingcart_recycler_footer)
                                .onBindListener(ShoppingCartActivity.this)
                                .onClickListener(ShoppingCartActivity.this)
                                .into(binding.shoppingRecyclerView);
                        break;
                    default:
                        Toast.makeText(ShoppingCartActivity.this, "서버와의 연동에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("asdf", response.code() + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<Bucket> call, Throwable t) {
                Toast.makeText(ShoppingCartActivity.this, "서버와의 연동에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("asdf", t.getMessage());
            }
        });
        binding.reserveExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReserveExecuteActivity.class));
            }
        });
    }

    private void initAppbarLayout() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setBackgroundColor(Color.WHITE);
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setTitle("예약할 주문 목록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.shoppingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
    public int getItemLayout(@NotNull Object item, int i) {
        if (item instanceof Menu) return R.layout.shoppingcart_recycler_content;
        else return R.layout.shoppingcart_recycler_footer;
    }

    @Override
    public void onBind(@NotNull Object o, @NotNull View view, int type, int position) {
        switch (type) {
            case R.layout.shoppingcart_recycler_content:
                Menu m = (Menu) o;
                ShoppingcartRecyclerContentBinding contentBinding = DataBindingUtil.getBinding(view);
                contentBinding.shoppingCartImage.setImageUrl(StringUtils.getFullImageUrl(m.getThumbnail()), ImageSingleTon.getInstance(this).getImageLoader());
                break;
            case R.layout.shoppingcart_recycler_footer:
                ShoppingcartRecyclerFooterBinding binding = DataBindingUtil.getBinding(view);
                binding.shoppingList.setText(bucket.getMenus().get(0).getName()+" 외 "+(bucket.getMenus().size()-1+" 건"));
                binding.shoppingMoney.setText(StringUtils.getTotalMoney(bucket.getMenus()));
                break;
        }
    }

    @Override
    public void onClick(@NotNull Object o, @NotNull View view, int i, int i1) {

    }
}
