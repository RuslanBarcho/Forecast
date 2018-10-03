package vintr.com.forecast;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import vintr.com.forecast.Adapters.DynamicFragmentAdapter;

public class MainActivity extends AppCompatActivity {

    ViewPager mFragmentViewPager;
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1){
                view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                this.getWindow().setNavigationBarColor(getColor(R.color.colorPrimary));
            }
        } else {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDarkGrey));
        }

        mFragmentViewPager = findViewById(R.id.pager);
        mTabLayout = findViewById(R.id.tabDots);
        mTabLayout.setupWithViewPager(mFragmentViewPager, true);
        mFragmentViewPager.setOffscreenPageLimit(5);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFragmentViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        addFragmentToViewPager(1);
        DynamicFragmentAdapter mDynamicFragmentAdapter = new DynamicFragmentAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mFragmentViewPager.setAdapter(mDynamicFragmentAdapter);
        mFragmentViewPager.setCurrentItem(0);
    }

    private void addFragmentToViewPager(int count){
        for (int i = 0; i < count; i++) mTabLayout.addTab(mTabLayout.newTab());
    }
}
