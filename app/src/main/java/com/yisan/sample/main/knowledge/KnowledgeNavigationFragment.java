package com.yisan.sample.main.knowledge;

import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
            permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE}
            , requestCode = 1)
    private void getLocation() {

        Log.e(TAG, "成功授权");
    }


    @PermissionDenied
    private void permissionDenied(int requestCode) {
        switch (requestCode) {
            case 1:
                Log.e(TAG, "当次拒绝");
                break;
            default:
                break;
        }
    }

    @PermissionDeniedForever
    private void permissionDeniedForever(int requestCode) {
        switch (requestCode) {
            case 1:
                Log.e(TAG, "永久拒绝");
                break;
            default:
                break;
        }
    }

}
