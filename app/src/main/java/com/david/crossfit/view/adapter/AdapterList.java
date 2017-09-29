package com.david.crossfit.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.david.crossfit.R;
import com.david.crossfit.model.vo.VideoData;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.animators.internal.ViewHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vitaly on 16.05.2016.
 */
public class AdapterList extends UltimateViewAdapter<RecyclerView.ViewHolder> {

    private Picasso mPicasso;
    private final ArrayList<VideoData> DATA;
    private Interpolator interpolator = new LinearInterpolator();
    private int lastPosition = 5;
    private final int ANIMATION_DURATION = 300;

    public AdapterList(Context context, ArrayList<VideoData> list) {
        DATA = list;
        mPicasso = Picasso.with(context);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_video_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getAdapterItemCount() {
        return DATA.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position < getItemCount()
                && (customHeaderView != null ? position <= DATA.size() : position < DATA.size())
                && (customHeaderView == null || position > 0)) {
            VideoData item;
            item = DATA.get(customHeaderView != null ? position - 1 : position);
            // Set data to the view
            ((ViewHolder) holder).txtTitle.setText(item.getTitle());
            ((ViewHolder) holder).txtDuration.setText(item.getDuration());
            ((ViewHolder) holder).txtPublished.setText(item.getPublishedAt());
            mPicasso.load(item.getUrl()).placeholder(R.mipmap.empty_photo).error(R.mipmap.empty_photo).into(((ViewHolder) holder).imgThumbnail);
        }

        boolean isFirstOnly = true;
        if (!isFirstOnly || position > lastPosition) {
            // Add animation to the item
            for (Animator anim : getAdapterAnimations(holder.itemView,
                    AdapterAnimationType.SlideInLeft)) {
                anim.setDuration(ANIMATION_DURATION).start();
                anim.setInterpolator(interpolator);
            }
            lastPosition = position;
        } else {
            ViewHelper.clear(holder.itemView);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    public static class ViewHolder extends UltimateRecyclerviewViewHolder {
        private TextView txtTitle, txtPublished, txtDuration;
        private ImageView imgThumbnail;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtDuration = (TextView) v.findViewById(R.id.txtDuration);
            txtPublished = (TextView) v.findViewById(R.id.txtPublishedAt);
            imgThumbnail = (ImageView) v.findViewById(R.id.imgThumbnail);
        }
    }

}