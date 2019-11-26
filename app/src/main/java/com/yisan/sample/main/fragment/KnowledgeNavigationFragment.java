package com.yisan.sample.main.fragment;

import android.Manifest;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.yisan.aop.annotation.PermissionDenied;
import com.yisan.aop.annotation.PermissionDeniedForever;
import com.yisan.aop.annotation.PermissionNeed;
import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.LazyFragment;
import com.yisan.base_ui.toolbar.YsToolBar;
import com.yisan.sample.R;

import butterknife.BindView;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.sample.main.fragment
 * @date：2019/11/25 0025 下午 9:38
 */
@ViewLayoutInject(R.layout.fragment_knowledge)
public class KnowledgeNavigationFragment extends LazyFragment {
    private static final String TAG = "wzh_Knowledge";
    @BindView(R.id.ys_tool_bar)
    YsToolBar mYsToolBar;

    public static Fragment create() {
        return new KnowledgeNavigationFragment();
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

        getLocation();
    }

    private void initToolBar() {
        LinearLayout contentView = mYsToolBar.getContentView();
        TextView tvTitle = contentView.findViewById(R.id.tv_title);
        TextView tvRight = contentView.findViewById(R.id.tv_right);
        contentView.findViewById(R.id.iv_finish).setVisibility(View.INVISIBLE);

        tvTitle.setText(R.string.main_know_fragment_title);
        tvRight.setVisibility(View.INVISIBLE);
    }


    @PermissionNeed(
            permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}
            , requestCode = 1)
    private void getLocation() {
        Toast.makeText(getContext(), "成功授权", Toast.LENGTH_SHORT).show();
    }


    @PermissionDenied
    private void permissionDenied(int requestCode) {
        switch (requestCode) {
            case 1:
                Toast.makeText(getContext(), "当次拒绝", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @PermissionDeniedForever
    private void permissionDeniedForever(int requestCode) {
        switch (requestCode) {
            case 1:
                Toast.makeText(getContext(), "永久拒绝", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}
