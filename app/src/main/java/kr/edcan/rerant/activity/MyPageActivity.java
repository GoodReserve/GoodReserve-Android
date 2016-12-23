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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.edcan.rerant.R;
import kr.edcan.rerant.adapter.CommonListViewAdapter;
import kr.edcan.rerant.databinding.ActivityMyPageBinding;
import kr.edcan.rerant.model.CommonListData;
import kr.edcan.rerant.utils.DataManager;

public class MyPageActivity extends AppCompatActivity {

    ActivityMyPageBinding binding;
    DataManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_page);
        initAppbarLayout();
        initDefault();
    }

    private void initAppbarLayout() {
        manager = new DataManager(getApplicationContext());
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setBackgroundColor(Color.WHITE);
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setTitle("마이페이지");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initDefault() {
        ArrayList<CommonListData> arrayList = new ArrayList<>();
        arrayList.add(new CommonListData("이메일 및 비밀번호 변경", "계정으로 등록된 이메일을 변경하거나 보안을 위해 비밀번호를 재설정합니다."));
        arrayList.add(new CommonListData("예약 시 푸시알림 설정", "예약시간에 받을 푸시알림을 언제 받을지 설정합니다."));
        arrayList.add(new CommonListData("GPS 항상 켜기", "빠르게 음식점을 탐색할 수 있도록 GPS를 항상 켭니다."));
        arrayList.add(new CommonListData("로그아웃", "Rerant에서 로그아웃 합니다."));
        arrayList.add(new CommonListData("서비스 탈퇴", "Rerant에서 영구적으로 탈퇴합니다."));
        binding.myPageListView.setAdapter(new CommonListViewAdapter(getApplicationContext(), arrayList));
        binding.myPageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 3:
                        manager.removeAllData();
                        startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                        finish();
                        break;
                    default:
                        Toast.makeText(MyPageActivity.this, "정식 서비스 이용때 지원 예정입니다.", Toast.LENGTH_SHORT).show();
                        break;


                }
            }
        });
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
