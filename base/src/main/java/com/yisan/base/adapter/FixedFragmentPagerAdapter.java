package com.yisan.base.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * 描述： FragmentPagerAdapter 和 FragmentStatePagerAdapter 区别
 * a、FragmentPagerAdapter
 * 中每一个Fragment都长存在与内存中，适用于比较固定的少量的Fragment。
 * FragmentPagerAdapter 在我们切换Fragment过程中不会销毁Fragment，
 * 只是调用事务中的detach方法。而在detach方法中只会销毁Fragment中的View，
 * 而不会销毁Fragment对象。
 * <p>
 * b、FragmentStatePagerAdapter
 * 中实现将只保留当前页面，当页面离开视线后，就会被消除，释放其资源。
 * 而在页面需要显示时，生成新的页面。在较多的Fragment的时候为了减少内存可适用。
 * FragmentStatePagerAdapter在我们切换Fragment，会把前面的Fragment直接销毁掉。
 *
 * @author：wzh
 * @description:
 * @packageName: com.library.basic_core.adapter
 * @date：2019/10/21 0021 上午 11:07
 */
public class FixedFragmentPagerAdapter extends FragmentPagerAdapter {


    private Fragment[] mFragments = null;
    private String[] mTitles = null;

    public FixedFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null && mTitles.length == getCount()) {
            return mTitles[position];
        }
        //如果title和fragment个数不对的话。设置title
        Fragment fragment = mFragments[position];
        if (fragment instanceof PageTitle) {
            PageTitle pageTitle = (PageTitle) fragment;
            return pageTitle.getPageTitle();
        }
        return "";
    }

    public void setFragmentList(Fragment... fragments) {
        this.mFragments = fragments;
    }

    public void setTitles(String... titles) {
        this.mTitles = titles;
    }

    public interface PageTitle {
        CharSequence getPageTitle();
    }
}
