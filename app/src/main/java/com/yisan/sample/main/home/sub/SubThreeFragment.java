package com.yisan.sample.main.home.sub;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kennyc.view.MultiStateView;
import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.LazyFragment;
import com.yisan.http.RxHttp;
import com.yisan.http.request.RequestClientManager;
import com.yisan.http.request.RxRequest;
import com.yisan.sample.R;
import com.yisan.sample.api.HttpApiService;
import com.yisan.sample.main.home.adapter.ArticleAdapter;
import com.yisan.sample.main.home.model.ArticleListBean;
import com.yisan.sample.utils.MultiStateUtils;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

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

    public static Fragment create() {
        return new SubThreeFragment();
    }

    @Override
    protected void onFragmentPause() {

    }

    @Override
    protected void onFragmentResume() {

    }

    @Override
    protected void onFragmentFirstVisible() {
        initData();
    }

    private void initData() {
        RxHttp.request(RequestClientManager.getService(HttpApiService.class).getWxArticleList(408, 1), "wxarticle" +
                "/list" +
                "/408" +
                "/1/json").request(new RxRequest.ResultCallback<ArticleListBean>() {
            @Override
            public void onStart(Disposable d) {
                MultiStateUtils.toLoading(mMsv);
            }

            @Override
            public void onSuccess(int code, ArticleListBean data) {

                if (data.size > 0) {
                    MultiStateUtils.toContent(mMsv);
                } else {
                    MultiStateUtils.toEmpty(mMsv);
                }
                adapter.setNewData(data.datas);
            }

            @Override
            public void onFailed(int code, String msg) {
                MultiStateUtils.toError(mMsv);
            }

            @Override
            public void onCacheSuccess(String data) {

            }

            @Override
            public void onFinish() {

            }
        });
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

        adapter = new ArticleAdapter();
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(adapter);

    }
}
