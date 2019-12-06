package com.yisan.sample.main.home.sub;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.LazyFragment;
import com.yisan.sample.R;
import com.yisan.sample.http.RequestListener;
import com.yisan.sample.main.home.adapter.HomeArticleAdapter;
import com.yisan.sample.main.home.model.ArticleListBean;
import com.yisan.sample.main.home.request.HomeRequest;
import com.yisan.sample.utils.MultiStateUtils;
import com.yisan.sample.utils.RvAnimUtils;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import per.goweii.rxhttp.core.RxHttp;
import per.goweii.rxhttp.core.RxLife;

/**
 * @author：wzh
 * @description: home界面的子fragment
 * @packageName: com.yisan.sample.main.home.sub
 * @date：2019/11/27 0027 下午 4:27
 */
@ViewLayoutInject(R.layout.fragment_hone_sub_one)
public class SubOneFragment extends LazyFragment {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.msv)
    MultiStateView mMsv;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    /**
     * 数据其实页
     */
    private static final int PAGE_START = 0;
    /**
     * 适配器
     */
    private HomeArticleAdapter articleAdapter;
    /**
     * 当前数据的page页
     */
    private int currPage = PAGE_START;

    private RxLife rxLife;


    public static Fragment create() {
        return new SubOneFragment();
    }

    @Override
    protected void onFragmentPause() {

    }

    @Override
    protected void onFragmentResume() {
    }


    @Override
    protected void onFragmentFirstVisible() {
    }

    @Override
    public void afterBindView() {
        super.afterBindView();
        if (rxLife == null) {
            rxLife = RxLife.create();
        }

        initRecyclerViewList();

        MultiStateUtils.toLoading(mMsv);

        initData(currPage);

        initListener();
    }

    private void initListener() {


        mSmartRefreshLayout.setEnableAutoLoadMore(false);
        mSmartRefreshLayout.setEnableOverScrollBounce(true);
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnablePureScrollMode(false);
        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            //必须设置超时时间
            refreshLayout.finishRefresh((int) RxHttp.getRequestSetting().getTimeout(), false, false);
            //请求数据
            //刷新数据从起始页开始
            currPage = PAGE_START;
            //加载数据
            initData(currPage);

        });

        //上拉加载更多-使用适配器的加载更多
        articleAdapter.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableLoadMore(false);
        articleAdapter.setOnLoadMoreListener(() -> initData(currPage), mRecyclerView);


    }

    private void initData(int page) {

        HomeRequest.getArticleList(rxLife, page, true, new RequestListener<ArticleListBean>() {
            @Override
            public void onStart(Disposable d) {
            }

            @Override
            public void onSuccess(int code, ArticleListBean data) {

                currPage = data.curPage + PAGE_START;

                if (data.size > 0) {
                    if (data.curPage == 1) {
                        MultiStateUtils.toContent(mMsv);
                        articleAdapter.setNewData(data.datas);
                    } else {
                        articleAdapter.addData(data.datas);
                        articleAdapter.loadMoreComplete();
                    }
                } else {
                    MultiStateUtils.toEmpty(mMsv);
                }

                if (data.over) {
                    articleAdapter.loadMoreEnd();
                } else {
                    if (!articleAdapter.isLoadMoreEnable()) {
                        articleAdapter.setEnableLoadMore(true);
                    }
                }

                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadMore();
            }

            @Override
            public void onError(int code, String msg) {
                MultiStateUtils.toError(mMsv);
                mSmartRefreshLayout.finishRefresh(false);
                mSmartRefreshLayout.finishLoadMore(false);
                articleAdapter.loadMoreFail();
            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * RecyclerView 集合
     */
    private void initRecyclerViewList() {

        articleAdapter = new HomeArticleAdapter(getContext());
        RvAnimUtils.setAnim(articleAdapter, RvAnimUtils.RvAnim.ALPHAIN);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(articleAdapter);

    }


    @Override
    public void onPause() {
        super.onPause();
        if (rxLife != null) {
            rxLife.destroy();
        }
    }
}
