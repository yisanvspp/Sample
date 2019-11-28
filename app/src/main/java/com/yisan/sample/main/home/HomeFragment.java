package com.yisan.sample.main.home;

import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.yisan.base.adapter.FixedFragmentPagerAdapter;
import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.LazyFragment;
import com.yisan.sample.R;
import com.yisan.sample.main.home.sub.SubFourFragment;
import com.yisan.sample.main.home.sub.SubOneFragment;
import com.yisan.sample.main.home.sub.SubThreeFragment;
import com.yisan.sample.main.home.sub.SubTwoFragment;

import butterknife.BindView;

/**
 * @author：wzh
 * @description: 主页
 * @packageName: com.yisan.sample.main.fragment
 * @date：2019/11/25 0025 下午 9:36
 */
@ViewLayoutInject(R.layout.fragment_home)
public class HomeFragment extends LazyFragment {

    private static final String TAG = "wzh_HomeFragment";
    @BindView(R.id.iv_top_bg)
    ImageView mIvTopBg;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;


    public static HomeFragment create() {
        return new HomeFragment();
    }


    @Override
    protected void onFragmentPause() {
        //停止网络加载
    }

    @Override
    protected void onFragmentResume() {
        //界面resume时调用
    }

    @Override
    protected void onFragmentFirstVisible() {
        //第一次界面可见
    }

    @Override
    public void afterBindView() {
        super.afterBindView();

        initAppBarLayoutScroll();
        initMagicIndicator();


    }


    /**
     * 初始化 MagicIndicator控件-该控件替换TabLayout
     */
    private void initMagicIndicator() {

        String[] tabs = new String[]{"热搜榜", "toolbar", "aop", "bannerHeader"};

        FixedFragmentPagerAdapter adapter = new FixedFragmentPagerAdapter(getChildFragmentManager());
        adapter.setTitles(tabs);
        adapter.setFragmentList(
                SubOneFragment.create(),
                SubTwoFragment.create(),
                SubThreeFragment.create(),
                SubFourFragment.create()
        );
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }


    /**
     * AppBarLayout偏移监听
     */
    private void initAppBarLayoutScroll() {

        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {

            // TODO: 2019/11/27 这边需要优化

            float offsetY = (float) (Math.abs(verticalOffset) / 1.0);
            float appBarLayoutHeight = (float) (appBarLayout.getMeasuredHeight() / 1.0);
            float currentY = (float) ((mToolBar.getMeasuredHeight() + offsetY) / 1.0);
            float ratio = 0.0f;

            if (offsetY != 0.0) {
                ratio = (currentY / (appBarLayoutHeight)) + 0.1f;
                if (ratio > 0.7) {
                    mToolBar.setAlpha(1.0f);
                } else {
                    mToolBar.setAlpha(ratio);
                }
            } else {
                mToolBar.setAlpha(0.0f);
            }
            Log.e(TAG, "initAppBarLayoutScroll: " + offsetY + " " + ratio);
        });

    }


}
