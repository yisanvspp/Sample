package com.yisan.sample.main.fragment;

import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.LazyFragment;
import com.yisan.sample.R;

/**
 * @author：wzh
 * @description: 主页
 * @packageName: com.yisan.sample.main.fragment
 * @date：2019/11/25 0025 下午 9:36
 */
@ViewLayoutInject(R.layout.fragment_home)
public class HomeFragment extends LazyFragment {

    private static final String TAG = "wzh_HomeFragment";


    public static HomeFragment create() {
        return new HomeFragment();
    }


    @Override
    protected void onFragmentPause() {
        //停止网络加载
    }

    @Override
    protected void onFragmentResume() {

    }

    @Override
    protected void onFragmentFirstVisible() {
        //第一次界面可见
    }

    @Override
    public void afterBindView() {
        super.afterBindView();
        initToolBar();
    }

    private void initToolBar() {

    }

}
