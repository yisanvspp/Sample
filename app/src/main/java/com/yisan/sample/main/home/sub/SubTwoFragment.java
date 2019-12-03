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
import com.yisan.sample.main.home.adapter.KnowledgeAdapter;
import com.yisan.sample.main.knowledge.model.ChapterBean;
import com.yisan.sample.utils.MultiStateUtils;

import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * @author：wzh
 * @description: home界面的子fragment
 * @packageName: com.yisan.sample.main.home.sub
 * @date：2019/11/27 0027 下午 4:27
 */
@ViewLayoutInject(value = R.layout.fragment_home_sub_two)
public class SubTwoFragment extends LazyFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.msv)
    MultiStateView mMsv;
    private KnowledgeAdapter adapter;

    public static Fragment create() {

        return new SubTwoFragment();
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

    @Override
    public void afterBindView() {
        super.afterBindView();
        initRecyclerViewList();
    }

    /**
     * RecyclerView 集合
     */
    private void initRecyclerViewList() {

        adapter = new KnowledgeAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

    }

    private void initData() {

        RxHttp.request(RequestClientManager.getService(HttpApiService.class).getKnowledgeList(), "tree/json").request(new RxRequest.ResultCallback<List<ChapterBean>>() {

            @Override
            public void onStart(Disposable d) {
                MultiStateUtils.toLoading(mMsv);
            }

            @Override
            public void onSuccess(int code, List<ChapterBean> data) {

                if (data.size() > 0) {
                    MultiStateUtils.toContent(mMsv);
                } else {
                    MultiStateUtils.toEmpty(mMsv);
                }
                adapter.setNewData(data);

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
}
