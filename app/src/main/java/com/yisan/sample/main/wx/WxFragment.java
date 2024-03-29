package com.yisan.sample.main.wx;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.LazyFragment;
import com.yisan.base_ui.toolbar.YsToolBar;
import com.yisan.sample.R;

import butterknife.BindView;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.sample.main.fragment
 * @date：2019/11/25 0025 下午 9:36
 */
@ViewLayoutInject(R.layout.fragment_wx)
public class WxFragment extends LazyFragment {
    private static final String TAG = "wzh_WxFragment";
    @BindView(R.id.ys_tool_bar)
    YsToolBar mYsToolBar;

    public static WxFragment create() {
        return new WxFragment();
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
        initToolBar();
    }

    private void initToolBar() {
        LinearLayout contentView = mYsToolBar.getContentView();
        TextView tvTitle = contentView.findViewById(R.id.tv_title);
        TextView tvRight = contentView.findViewById(R.id.tv_right);
        contentView.findViewById(R.id.iv_finish).setVisibility(View.INVISIBLE);
        tvTitle.setText(R.string.main_wx_fragment_title);
        tvRight.setVisibility(View.INVISIBLE);
    }
}
