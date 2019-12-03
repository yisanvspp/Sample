package com.yisan.sample.main.home.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yisan.sample.R;
import com.yisan.sample.main.home.model.ArticleBean;
import com.yisan.sample.view.CollectView;


/**
 * @author：wzh
 * @description: 文章列表
 * @packageName: com.yisan.sample.main.home.adapter
 * @date：2019/11/29 0029 上午 9:43
 */
public class HomeArticleAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    private OnCollectViewClickListener mOnCollectViewClickListener = null;

    private Context context;
    private RequestOptions options;

    public HomeArticleAdapter(Context context) {
        super(R.layout.rv_item_article);
        this.context = context;

        if (options == null) {
            options = new RequestOptions();
        }
        options.placeholder(R.drawable.image_holder).error(R.drawable.image_holder);
    }

    public void setOnCollectViewClickListener(OnCollectViewClickListener onCollectViewClickListener) {
        mOnCollectViewClickListener = onCollectViewClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean item) {
        helper.setText(R.id.tv_title, Html.fromHtml(item.title));
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_time, item.niceDate);
//        helper.setText(R.id.tv_super_chapter_name, item.getSuperChapterName());
        helper.setText(R.id.tv_chapter_name, item.chapterName);
        LinearLayout ll_new = helper.getView(R.id.ll_new);
        if (item.isFresh()) {
            ll_new.setVisibility(View.VISIBLE);
        } else {
            ll_new.setVisibility(View.GONE);
        }
        ImageView iv_img = helper.getView(R.id.iv_img);
        if (!TextUtils.isEmpty(item.envelopePic)) {
            Glide.with(context).load(item.envelopePic).apply(options).into(iv_img);
            iv_img.setVisibility(View.VISIBLE);
        } else {
            iv_img.setVisibility(View.GONE);
        }
        CollectView cv_collect = helper.getView(R.id.cv_collect);
        if (item.isCollect()) {
            cv_collect.setChecked(true);
        } else {
            cv_collect.setChecked(false);
        }
        TextView tvTag = helper.getView(R.id.tv_tag);
        if (item.tags != null && item.tags.size() > 0) {
            tvTag.setText(item.tags.get(0).name);
            tvTag.setVisibility(View.VISIBLE);
        } else {
            tvTag.setVisibility(View.GONE);
        }
        cv_collect.setOnClickListener((CollectView.OnClickListener) v -> {
            if (mOnCollectViewClickListener != null) {
                mOnCollectViewClickListener.onClick(helper, v, helper.getAdapterPosition() - getHeaderLayoutCount());
            }
        });
    }


    public interface OnCollectViewClickListener {
        void onClick(BaseViewHolder helper, CollectView v, int position);
    }
}
