package com.yisan.sample.main.fragment;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.LazyFragment;
import com.yisan.sample.R;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.sample.main.fragment
 * @date：2019/11/25 0025 下午 9:38
 */
@ViewLayoutInject(R.layout.fragment_knowledge)
public class KnowledgeNavigationFragment extends LazyFragment {
    private static final String TAG = "wzh_Knowledge";

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
        Log.e(TAG, "onFragmentFirstVisible: ");
    }
}
