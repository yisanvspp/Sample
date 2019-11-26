package com.yisan.sample.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.yisan.aop.annotation.OnClickGap;
import com.yisan.base.adapter.FixedFragmentPagerAdapter;
import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.BaseActivity;
import com.yisan.base_ui.percent_imageview.PercentImageView;
import com.yisan.sample.R;
import com.yisan.sample.main.fragment.HomeFragment;
import com.yisan.sample.main.fragment.KnowledgeNavigationFragment;
import com.yisan.sample.main.fragment.MineFragment;
import com.yisan.sample.main.fragment.ProjectFragment;
import com.yisan.sample.main.fragment.WxFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 *
 * @author wzh
 * @packageName com.yisan.sample
 * @fileName MainActivity.java
 * @date 2019-11-24  下午 9:57
 */
@ViewLayoutInject(R.layout.activity_main)
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.iv_bb_home)
    PercentImageView mIvBbHome;
    @BindView(R.id.tv_bb_home)
    TextView mTvBbHome;
    @BindView(R.id.ll_bb_home)
    LinearLayout mLlBbHome;
    @BindView(R.id.iv_bb_knowledge)
    PercentImageView mIvBbKnowledge;
    @BindView(R.id.tv_bb_knowledge)
    TextView mTvBbKnowledge;
    @BindView(R.id.ll_bb_knowledge)
    LinearLayout mLlBbKnowledge;
    @BindView(R.id.ll_bb_wechat)
    LinearLayout mLlBbWechat;
    @BindView(R.id.iv_bb_project)
    PercentImageView mIvBbProject;
    @BindView(R.id.tv_bb_project)
    TextView mTvBbProject;
    @BindView(R.id.ll_bb_project)
    LinearLayout mLlBbProject;
    @BindView(R.id.iv_bb_mine)
    PercentImageView mIvBbMine;
    @BindView(R.id.tv_bb_mine)
    TextView mTvBbMine;
    @BindView(R.id.ll_bb_mine)
    LinearLayout mLlBbMine;
    @BindView(R.id.iv_bb_wechat)
    PercentImageView mIvBbWechat;
    @BindView(R.id.tv_bb_wechat)
    TextView mTvBbWechat;
    private FixedFragmentPagerAdapter adapter;

    public static void show(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }


    @Override
    protected void afterBindView() {
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(5);
        adapter = new FixedFragmentPagerAdapter(getSupportFragmentManager());
        adapter.setFragmentList(
                HomeFragment.create(),
                KnowledgeNavigationFragment.create(),
                WxFragment.create(),
                ProjectFragment.create(),
                MineFragment.create()
        );
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        onPageSelected(0);
    }

    @OnClickGap
    @OnClick({R.id.ll_bb_home, R.id.ll_bb_knowledge, R.id.ll_bb_wechat, R.id.ll_bb_project, R.id.ll_bb_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_bb_home:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.ll_bb_knowledge:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.ll_bb_wechat:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.ll_bb_project:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.ll_bb_mine:
                mViewPager.setCurrentItem(4);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        mIvBbHome.setColorFilter(ContextCompat.getColor(getContext(), R.color.third));
        mIvBbKnowledge.setColorFilter(ContextCompat.getColor(getContext(), R.color.third));
        mIvBbWechat.setColorFilter(ContextCompat.getColor(getContext(), R.color.third));
        mIvBbProject.setColorFilter(ContextCompat.getColor(getContext(), R.color.third));
        mIvBbMine.setColorFilter(ContextCompat.getColor(getContext(), R.color.third));

        mTvBbHome.setTextColor(ContextCompat.getColor(getContext(), R.color.third));
        mTvBbKnowledge.setTextColor(ContextCompat.getColor(getContext(), R.color.third));
        mTvBbWechat.setTextColor(ContextCompat.getColor(getContext(), R.color.third));
        mTvBbProject.setTextColor(ContextCompat.getColor(getContext(), R.color.third));
        mTvBbMine.setTextColor(ContextCompat.getColor(getContext(), R.color.third));

        switch (position) {
            default:
                break;
            case 0:
                mIvBbHome.setColorFilter(ContextCompat.getColor(getContext(), R.color.main));
                mTvBbHome.setTextColor(ContextCompat.getColor(getContext(), R.color.main));
                break;
            case 1:
                mIvBbKnowledge.setColorFilter(ContextCompat.getColor(getContext(), R.color.main));
                mTvBbKnowledge.setTextColor(ContextCompat.getColor(getContext(), R.color.main));
                break;
            case 2:
                mIvBbWechat.setColorFilter(ContextCompat.getColor(getContext(), R.color.main));
                mTvBbWechat.setTextColor(ContextCompat.getColor(getContext(), R.color.main));

                break;
            case 3:
                mIvBbProject.setColorFilter(ContextCompat.getColor(getContext(), R.color.main));
                mTvBbProject.setTextColor(ContextCompat.getColor(getContext(), R.color.main));

                break;
            case 4:
                mIvBbMine.setColorFilter(ContextCompat.getColor(getContext(), R.color.main));
                mTvBbMine.setTextColor(ContextCompat.getColor(getContext(), R.color.main));

                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
