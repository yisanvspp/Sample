package com.yisan.sample.main.home.sub;

import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kennyc.view.MultiStateView;
import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.LazyFragment;
import com.yisan.sample.R;
import com.yisan.sample.main.home.adapter.ArticleAdapter;
import com.yisan.sample.utils.MultiStateUtils;

import butterknife.BindView;

/**
 * @author：wzh
 * @description: home界面的子fragment
 * @packageName: com.yisan.sample.main.home.sub
 * @date：2019/11/27 0027 下午 4:27
 */
@ViewLayoutInject(R.layout.fragment_hone_sub_three)
public class SubThreeFragment extends LazyFragment {

    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.msv)
    MultiStateView mMsv;
    private ArticleAdapter adapter;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MultiStateUtils.toEmpty(mMsv);

        }
    };

    public static Fragment create() {
        return new SubThreeFragment();
    }

    @Override
    protected void onFragmentPause() {

    }

    @Override
    protected void onFragmentResume() {

    }

    private Handler handler = new android.os.Handler();

    @Override
    protected void onFragmentFirstVisible() {
        MultiStateUtils.toLoading(mMsv);
        initData();
    }


    @Override
    public void afterBindView() {
        super.afterBindView();
        initRecyclerViewList();
    }

    private void initData() {

        handler.postDelayed(runnable, 2000);

    }

    /**
     * RecyclerView 集合
     */
    private void initRecyclerViewList() {

        adapter = new ArticleAdapter();
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(adapter);

    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}
