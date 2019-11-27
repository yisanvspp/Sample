package com.yisan.sample.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yisan.sample.R;

import java.util.List;

/**
 * @author：wzh
 * @description: 字符串-集合布局
 * @packageName: com.yisan.sample.main.adapter
 * @date：2019/11/27 0027 上午 10:05
 */
public class StringAdapter extends RecyclerView.Adapter<StringAdapter.VH> {
    private List<String> mList;

    public void setNewData(List<String> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public StringAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home_fragment, parent, false);
        return new StringAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StringAdapter.VH holder, int position) {

        holder.mTvContent.setText(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class VH extends RecyclerView.ViewHolder {
        private TextView mTvContent;

        public VH(@NonNull View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
