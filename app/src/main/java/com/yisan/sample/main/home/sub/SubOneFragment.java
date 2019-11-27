package com.yisan.sample.main.home.sub;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.LazyFragment;
import com.yisan.sample.R;
import com.yisan.sample.main.adapter.StringAdapter;

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

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

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
}
