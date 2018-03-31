package com.wujingjingguanxueyuan.yidaogan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int layoutId;
    private int position;
    private boolean isClick = true;

    public CommonAdapter(int layoutId, List<T> datas) {
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        this.layoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    public void setClickItem(boolean flag) {
        isClick = flag;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;
        if(mContext == null){
            mContext = parent.getContext();
        }
        CommonViewHolder holder = CommonViewHolder.get(mContext, convertView, parent, layoutId, position);

        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(CommonViewHolder holder, T item);


    public void swapData(List<T> data) {
        if(data != null){
            mDatas = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean areAllItemsEnabled() {
        return isClick;
    }

    @Override
    public boolean isEnabled(int position) {
        return isClick;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
