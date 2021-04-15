package com.dss.orderingsystemforchef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.dss.orderingsystemforchef.adapter.ViewPagerAdapter;
import com.dss.orderingsystemforchef.fragments.FeedbackFragment;
import com.dss.orderingsystemforchef.fragments.FlowFragment;
import com.dss.orderingsystemforchef.fragments.MenuFragment;
import com.dss.orderingsystemforchef.fragments.OrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        //ViewPager2
        List<Fragment> fragmentList = initFragmentList();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragmentList);
        viewPager2.setAdapter(viewPagerAdapter);

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
}