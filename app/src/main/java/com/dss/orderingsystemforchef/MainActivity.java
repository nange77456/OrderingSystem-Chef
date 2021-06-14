package com.dss.orderingsystemforchef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.dss.orderingsystemforchef.adapter.ViewPagerAdapter;
import com.dss.orderingsystemforchef.fragments.FeedbackFragment;
import com.dss.orderingsystemforchef.fragments.FlowFragment;
import com.dss.orderingsystemforchef.fragments.MenuFragment;
import com.dss.orderingsystemforchef.fragments.OrderFragment;
import com.dss.orderingsystemforchef.util.FileUtil;
import com.dss.orderingsystemforchef.util.GenerateUserSigUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kongzue.dialog.v3.TipDialog;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.imsdk.v2.V2TIMUserInfo;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 承载四个主页的 activity
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        ViewPager2 viewPager2 = findViewById(R.id.viewpager2);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // 登录im
        loginIM();

        //ViewPager2
        List<Fragment> fragmentList = initFragmentList();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragmentList);
        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.setUserInputEnabled(false);

        //底部导航栏和ViewPager互相绑定
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page1:
                        viewPager2.setCurrentItem(0);
                        toolbar.setTitle(R.string.title_page0);
                        return true;
                    case R.id.page2:
                        viewPager2.setCurrentItem(1);
                        toolbar.setTitle(R.string.title_page1);
                        return true;
                    case R.id.page3:
                        viewPager2.setCurrentItem(2);
                        toolbar.setTitle(R.string.title_page2);
                        return true;
                    case R.id.page4:
                        viewPager2.setCurrentItem(3);
                        toolbar.setTitle(R.string.title_page3);
                        return true;
                    default:
                        return false;
                }
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigation.setSelectedItemId(R.id.page1);
                        toolbar.setTitle(R.string.title_page0);
                        break;
                    case 1:
                        bottomNavigation.setSelectedItemId(R.id.page2);
                        toolbar.setTitle(R.string.title_page1);
                        break;
                    case 2:
                        bottomNavigation.setSelectedItemId(R.id.page3);
                        toolbar.setTitle(R.string.title_page2);
                        break;
                    case 3:
                        bottomNavigation.setSelectedItemId(R.id.page4);
                        toolbar.setTitle(R.string.title_page3);
                        break;
                }
            }
        });


    }

    /**
     * 初始化 FragmentList
     * @return fragmentList
     */
    private List<Fragment> initFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>(4);
        fragmentList.add(new FlowFragment());
        fragmentList.add(new OrderFragment());
        fragmentList.add(new MenuFragment());
        fragmentList.add(new FeedbackFragment());
        return fragmentList;
    }

    private void loginIM(){
        V2TIMManager.getInstance().login(FileUtil.getUserID(), GenerateUserSigUtil.genTestUserSig(FileUtil.getUserID())
                , new V2TIMCallback() {
                    @Override
                    public void onError(int code, String desc) {
                        TipDialog.show(MainActivity.this,"IM登录失败，请重启应用",TipDialog.TYPE.ERROR)
                        .setTipTime(5000);
                    }

                    @Override
                    public void onSuccess() {
                        Log.e("tag","IM登录成功");
                        setIMListener();
                    }
                });
    }

    private void setIMListener(){
        V2TIMManager.getInstance().addSimpleMsgListener(new V2TIMSimpleMsgListener() {
            @Override
            public void onRecvC2CCustomMessage(String msgID, V2TIMUserInfo sender, byte[] customData) {
                String msg = new String(customData, StandardCharsets.UTF_8);
                Gson gson = new Gson();
                if(msg.startsWith("newOrder:")){
                    // 新订单

                }else if(msg.startsWith("newSuggestion:")){
                    // 新反馈

                }
            }
        });
    }

}