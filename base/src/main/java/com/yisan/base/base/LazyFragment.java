package com.yisan.base.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 作者：吴知航
 * 描述：Fragment的懒加载。
 * 预期效果：fragment1 切换到  fragment2 。 fragment2可见的时候加载数据。fragment1 立刻停止。
 * fragment3 走生命周期，不加载数据。 支持viewpager内部嵌套一层viewpager。
 * 时间：2019/9/12  下午 2:23
 * <p>
 * fragment的生命周期：onAttach ---> onCreate --->onCreateView ---> onActivityCreated ----> onResume
 * ---> onPause ---> onDestroyView ---> onDetach
 * 懒加载关注的生命周期: onCreateView ---> onActivityCreated ----> onResume  ---> onPause
 * 非生命周期方法 ： setUserVisibleHint(boolean isVisibleToUser) :该方法：优先于 onAttach（）调用
 */
public abstract class LazyFragment extends BaseFragment {

    /**
     * 根布局
     */
    protected View mRootView = null;

    /**
     * View是否已经创建
     */
    protected boolean mIsViewCreated = false;

    /**
     * 当前fragment是否第一次可见
     */
    protected boolean mIsFirstVisible = true;

    /**
     * 为了获得 Fragment 不可见的状态，和再次回到可见状态的判断，需要增加一个 currentVisibleState 标志位，
     * 该标志位在 onResume 中和 onPause 中结合 getUserVisibleHint 的返回值来决定是否应该回调可见和不可见状态函数
     */
    protected boolean mCurrentVisibleState = false;


    /**
     * Set a hint to the system about whether this fragment's UI is currently visible
     * to the user. This hint defaults to true and is persistent across fragment instance
     * state save and restore.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 用 isViewCreated 进行判断，如果isViewCreated false，说明它没有被创建
        if (mIsViewCreated) {
            if (isVisibleToUser && !mCurrentVisibleState) {
                //当前fragment可见、
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && mCurrentVisibleState) {
                //当前fragment不可见
                dispatchUserVisibleHint(false);
            }
        }
    }


    /**
     * 用FragmentTransaction来控制fragment的hide和show时，
     * 那么这个方法就会被调用。每当你对某个Fragment使用hide
     * 或者是show的时候，那么这个Fragment就会自动调用这个方法。
     * https://blog.csdn.net/u013278099/article/details/72869175
     * <p>
     * 你会发现使用hide和show这时fragment的生命周期不再执行，
     * 不走任何的生命周期，
     * 这样在有的情况下，数据将无法通过生命周期方法进行刷新，
     * 所以你可以使用onHiddenChanged方法来解决这问题。
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden) {
            dispatchUserVisibleHint(false);
        } else {
            dispatchUserVisibleHint(true);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //必须调用父类的onCreateView方法
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void afterBindView() {
        // 将 View 创建完成标志位设为 true
        mIsViewCreated = true;
        // 本次分发主要时用于分发默认tab可见状态，这种状况下它的生命周期是：
        // 1 fragment setUserVisibleHint: true-》onAttach-》onCreate-》onCreateView-》onResume
        // 默认 Tab getUserVisibleHint() = true !isHidden() = true
        // 对于非默认 tab 或者非默认显示的 Fragment 在该生命周期中只做了 isViewCreated 标志位设置 可见状态将不会在这里分发
        if (!isHidden() && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //在滑动或者跳转的过程中，第一次创建fragment的时候均会调用onResume方法，类似于在tab1 滑到tab2，此时tab3会缓存，这个时候会调用tab3 fragment的
        //onResume，所以，此时是不需要去调用 dispatchUserVisibleHint(true)的，因而出现了下面的if
        if (!mIsFirstVisible) {
            //由于Activity 中如果有多个fragment，然后从Activity1 跳转到Activity2，此时会有多个fragment会在activity1缓存，此时，如果再从activity2跳转回
            //activity1，这个时候会将所有的缓存的fragment进行onResume生命周期的重复，这个时候我们无需对所有缓存的fragnment 调用dispatchUserVisibleHint(true)
            //我们只需要对可见的fragment进行加载，因此就有下面的if
            if (!isHidden() && !mCurrentVisibleState && getUserVisibleHint()) {
                dispatchUserVisibleHint(true);
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCurrentVisibleState && getUserVisibleHint()) {
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsViewCreated = false;
        mIsFirstVisible = false;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 统一处理用户可见信息分发
     * 分第一次可见，可见，不可见分发
     *
     * @param isVisible
     */
    private void dispatchUserVisibleHint(boolean isVisible) {
        //事实上作为父 Fragment 的 BottomTabFragment2 并没有分发可见事件，
        // 他通过 getUserVisibleHint() 得到的结果为 false，首先我想到的
        // 是能在负责分发事件的方法中判断一下当前父 fragment 是否可见，
        // 如果父 fragment 不可见我们就不进行可见事件的分发
        if (isVisible && isParentInvisible()) {
            return;
        }

        if (mCurrentVisibleState == isVisible) {
            //当前可见不可见状态为改变、退出操作
            return;
        }
        if (isVisible) {
            //可见
            if (mIsFirstVisible) {
                //第一次可见
                mIsFirstVisible = false;
                onFragmentFirstVisible();
            }
            onFragmentResume();
        } else {
            //不可见
            onFragmentPause();
        }
        mCurrentVisibleState = isVisible;
    }

    /**
     * 判断父fragment是否不可见
     */
    private boolean isParentInvisible() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof LazyFragment) {
            LazyFragment fragment = (LazyFragment) parentFragment;
            return !fragment.isSupportVisible();
        }
        return false;
    }

    private boolean isSupportVisible() {
        return mCurrentVisibleState;
    }


    /**
     * fragment 停止数据
     */
    protected abstract void onFragmentPause();

    /**
     * fragment 每次界面onResume的时候都调用该方法
     */
    protected abstract void onFragmentResume();

    /**
     * fragment 第一次可见调用-加载数据
     */
    protected abstract void onFragmentFirstVisible();

}
