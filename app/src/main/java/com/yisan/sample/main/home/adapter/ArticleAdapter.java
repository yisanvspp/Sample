package com.yisan.sample.main.home.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yisan.sample.R;
import com.yisan.sample.main.home.model.ArticleBean;
import com.yisan.sample.utils.StringUtils;
import com.yisan.sample.view.CollectView;


/**
 * 公众号文章
 *
 * @author wzh
 * @packageName com.yisan.sample.main.home.adapter
 * @fileName ArticleAdapter.java
 * @date 2019-12-03  上午 11:48
 */
public class ArticleAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    private OnItemChildViewClickListener mOnItemChildViewClickListener = null;

    public ArticleAdapter() {
        super(R.layout.rv_item_article);
    }

    public static void bindArticle(View view, ArticleBean item, OnCollectListener onCollectListener) {
        TextView tv_top = view.findViewById(R.id.tv_top);
        TextView tv_new = view.findViewById(R.id.tv_new);
        TextView tv_author = view.findViewById(R.id.tv_author);
        TextView tv_tag = view.findViewById(R.id.tv_tag);
        TextView tv_time = view.findViewById(R.id.tv_time);
        ImageView iv_img = view.findViewById(R.id.iv_img);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_desc = view.findViewById(R.id.tv_desc);
        TextView tv_chapter_name = view.findViewById(R.id.tv_chapter_name);
        CollectView cv_collect = view.findViewById(R.id.cv_collect);

        if (item.top) {
            tv_top.setVisibility(View.VISIBLE);
        } else {
            tv_top.setVisibility(View.GONE);
        }
        if (item.isFresh()) {
            tv_new.setVisibility(View.VISIBLE);
        } else {
            tv_new.setVisibility(View.GONE);
        }
        tv_author.setText(item.getAuthor());
        if (item.tags != null && item.tags.size() > 0) {
            tv_tag.setText(item.tags.get(0).name);
            tv_tag.setVisibility(View.VISIBLE);
        } else {
            tv_tag.setVisibility(View.GONE);
        }
        tv_time.setText(item.niceDate);
        if (!TextUtils.isEmpty(item.envelopePic)) {
            // ImageLoader.image(iv_img, item.getEnvelopePic());
            Glide.with(iv_img).load(item.envelopePic).into(iv_img);
            iv_img.setVisibility(View.VISIBLE);
        } else {
            iv_img.setVisibility(View.GONE);
        }
        tv_title.setText(Html.fromHtml(item.title));
        if (TextUtils.isEmpty(item.desc)) {
            tv_desc.setVisibility(View.GONE);
            tv_title.setSingleLine(false);
        } else {
            tv_desc.setVisibility(View.VISIBLE);
            tv_title.setSingleLine(true);
            String desc = Html.fromHtml(item.desc).toString();
            desc = StringUtils.removeAllBank(desc, 2);
            tv_desc.setText(desc);
        }
        tv_chapter_name.setText(Html.fromHtml(formatChapterName(item.superChapterName, item.chapterName)));
        if (item.isCollect()) {
            cv_collect.setChecked(true);
        } else {
            cv_collect.setChecked(false);
        }

        cv_collect.setOnClickListener(new CollectView.OnClickListener() {
            @Override
            public void onClick(CollectView v) {
                if (!v.isChecked()) {
                    if (onCollectListener != null) {
                        onCollectListener.collect(item, v);
                    }
                } else {
                    if (onCollectListener != null) {
                        onCollectListener.uncollect(item, v);
                    }
                }
            }
        });
    }

    private static String formatChapterName(String... names) {
        StringBuilder format = new StringBuilder();
        for (String name : names) {
            if (!TextUtils.isEmpty(name)) {
                if (format.length() > 0) {
                    format.append("·");
                }
                format.append(name);
            }
        }
        return format.toString();
    }

    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        mOnItemChildViewClickListener = onItemChildViewClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean item) {
        bindArticle(helper.itemView, item, new OnCollectListener() {
            @Override
            public void collect(ArticleBean item, CollectView v) {
                if (mOnItemChildViewClickListener != null) {
                    mOnItemChildViewClickListener.onCollectClick(helper, v, helper.getAdapterPosition() - getHeaderLayoutCount());
                }
            }

            @Override
            public void uncollect(ArticleBean item, CollectView v) {
                if (mOnItemChildViewClickListener != null) {
                    mOnItemChildViewClickListener.onCollectClick(helper, v, helper.getAdapterPosition() - getHeaderLayoutCount());
                }
            }
        });
    }

    public interface OnItemChildViewClickListener {
        void onCollectClick(BaseViewHolder helper, CollectView v, int position);
    }

    public interface OnCollectListener {
        void collect(ArticleBean item, CollectView v);

        void uncollect(ArticleBean item, CollectView v);
    }
}
