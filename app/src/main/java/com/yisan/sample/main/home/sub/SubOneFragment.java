package com.yisan.sample.main.home.sub;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.LazyFragment;
import com.yisan.sample.R;
import com.yisan.sample.main.adapter.StringAdapter;
import com.yisan.sample.main.home.api.ArticleListApi;
import com.yisan.sample.main.home.model.ArticleBean;
import com.yisan.sample.main.home.model.ArticleListBean;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author：wzh
 * @description: home界面的子fragment
 * @packageName: com.yisan.sample.main.home.sub
 * @date：2019/11/27 0027 下午 4:27
 */
@ViewLayoutInject(R.layout.fragment_hone_sub_one)
public class SubOneFragment extends LazyFragment {

    private static final String TAG = "SubOneFragment";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static Fragment create() {
        return new SubOneFragment();
    }

    @Override
    protected void onFragmentPause() {

    }

    private HttpOnNextListener listener = new HttpOnNextListener() {
        @Override
        public void onNext(Object o) {
            ArticleListBean bean = (ArticleListBean) o;

            for (ArticleBean b : bean.datas) {
                Log.e(TAG, "onNext: " + b.toString());
            }
        }
    };

    @Override
    protected void onFragmentResume() {
        ArticleListApi articleListApi = new ArticleListApi<ArticleListBean>(listener, this);
        HttpManager httpManager = HttpManager.getInstance();
        httpManager.doHttpDeal(articleListApi);
    }

    @Override
    public void afterBindView() {
        super.afterBindView();
        initRecyclerViewList();
    }

    /**
     * RecyclerView 集合
     */
    private void initRecyclerViewList() {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add("第" + i);
        }
        StringAdapter adapter = new StringAdapter();
        adapter.setNewData(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onFragmentFirstVisible() {


    }
}
