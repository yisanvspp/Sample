package com.yisan.sample.main.home.sub;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.LazyFragment;
import com.yisan.http.RxHttp;
import com.yisan.http.request.RequestClientManager;
import com.yisan.http.request.RxRequest;
import com.yisan.sample.R;
import com.yisan.sample.api.HttpApiService;
import com.yisan.sample.api.WanResponse;
import com.yisan.sample.main.home.adapter.HomeArticleAdapter;
import com.yisan.sample.main.home.model.ArticleListBean;
import com.yisan.sample.utils.JsonUtil;
import com.yisan.sample.utils.MultiStateUtils;
import com.yisan.sample.utils.RvAnimUtils;
import com.yisan.sample.utils.SmartRefreshUtils;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * @author：wzh
 * @description: home界面的子fragment
 * @packageName: com.yisan.sample.main.home.sub
 * @date：2019/11/27 0027 下午 4:27
 */
@ViewLayoutInject(R.layout.fragment_hone_sub_one)
public class SubOneFragment extends LazyFragment {

    private static final String TAG = "wzh_SubOneFragment";


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private static final int PAGE_START = 0;
    @BindView(R.id.msv)
    MultiStateView mMsv;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    private HomeArticleAdapter articleAdapter;

    private SmartRefreshUtils mSmartRefreshUtils;

    private int currPage = PAGE_START;


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
            initData(currPage);

        });

        //上拉加载更多-使用适配器的加载更多
        articleAdapter.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableLoadMore(false);
        articleAdapter.setOnLoadMoreListener(() -> {
            initData(currPage);

        }, mRecyclerView);
    }

    private void initData(int page) {

        RxHttp.request(RequestClientManager.getService(HttpApiService.class).getArticleList(page)
                , "article/list/" + currPage + "/json")
                .request(new RxRequest.ResultCallback<ArticleListBean>() {
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
                    public void onFailed(int code, String msg) {
                        MultiStateUtils.toError(mMsv);
                        mSmartRefreshUtils.fail();
                        articleAdapter.loadMoreFail();
                    }

                    @Override
                    public void onCacheSuccess(String data) {
                        try {

                            WanResponse wanResponse = JsonUtil.parserArticleListJson(data);

                            ArticleListBean articleListBean = (ArticleListBean) wanResponse.getData();

                            if (articleListBean.size > 0) {
                                MultiStateUtils.toContent(mMsv);
                            } else {
                                MultiStateUtils.toEmpty(mMsv);
                            }

                            if (articleAdapter != null) {
                                articleAdapter.setNewData(articleListBean.datas);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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


}
