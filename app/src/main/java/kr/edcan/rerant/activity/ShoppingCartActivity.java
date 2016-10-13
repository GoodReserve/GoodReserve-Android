/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project @kotohana5706
 * All rights reversed.
 */

package kr.edcan.rerant.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.github.nitrico.lastadapter.BR;
import com.github.nitrico.lastadapter.LastAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import kr.edcan.rerant.R;
import kr.edcan.rerant.databinding.ActivityShoppingCartBinding;
import kr.edcan.rerant.databinding.ShoppingcartRecyclerFooterBinding;
import kr.edcan.rerant.model.Menu;
import kr.edcan.rerant.model.ReserveFooter;

public class ShoppingCartActivity extends AppCompatActivity implements LastAdapter.LayoutHandler, LastAdapter.OnBindListener, LastAdapter.OnClickListener {


    ArrayList<Object> arrayList;
    ActivityShoppingCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart);
        initAppbarLayout();
        setDefault();
    }

    private void setDefault() {
        arrayList = new ArrayList<>();
        arrayList.add(new Menu("한글로 드 스테이크", "10000"));
        arrayList.add(new Menu("한글로 드 스테이크", "10000"));
        arrayList.add(new Menu("한글로 드 스테이크", "10000"));
        arrayList.add(new Menu("한글로 드 스테이크", "10000"));
        arrayList.add(new Menu("한글로 드 스테이크", "10000"));
        arrayList.add(new ReserveFooter());
        LastAdapter.with(arrayList, BR.item)
                .layoutHandler(this)
                .map(Menu.class, R.layout.shoppingcart_recycler_content)
                .map(ReserveFooter.class, R.layout.shoppingcart_recycler_footer)
                .onBindListener(this)
                .onClickListener(this)
                .into(binding.shoppingRecyclerView);
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
            case R.layout.shoppingcart_recycler_footer:
                ShoppingcartRecyclerFooterBinding binding = DataBindingUtil.getBinding(view);
                binding.shoppingList.setText("asdf 외 2건");
                binding.shoppingMoney.setText(100000000 + "원");
                break;
        }
    }

    @Override
    public void onClick(@NotNull Object o, @NotNull View view, int i, int i1) {

    }
}
