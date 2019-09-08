package com.dicoding.picodiploma.moviesubmission4.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.picodiploma.moviesubmission4.Config;
import com.dicoding.picodiploma.moviesubmission4.R;
import com.dicoding.picodiploma.moviesubmission4.model.TvShow;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TvShowFavoriteAdapter extends RecyclerView.Adapter<TvShowFavoriteAdapter.TvFavoriteViewHolder> {
    private Activity activity;
    private List<TvShow> listTv;

    public TvShowFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setTvFavoriteAdapter(List<TvShow> listTv) {
        this.listTv = listTv;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowFavoriteAdapter.TvFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_tv_show_favorite, parent, false);
        return new TvFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowFavoriteAdapter.TvFavoriteViewHolder holder, int position) {
        holder.tvTitle.setText(listTv.get(position).getOriginalName());
        holder.tvDate.setText(listTv.get(position).getFirstAirDate());
        holder.tvRate.setText(String.valueOf(listTv.get(position).getVoteAverage()));
        holder.tvPopularity.setText(String.valueOf(listTv.get(position).getPopularity()));
        String urlPhoto = Config.IMAGE_URL + listTv.get(position).getPosterPath();
        Glide.with(activity)
                .load(urlPhoto)
                .apply(new RequestOptions().override(100, 140))
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        if (listTv != null) {
            return listTv.size();
        } else {
            return 0;
        }
    }

    class TvFavoriteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView tvTitle;
        @BindView(R.id.txt_date)
        TextView tvDate;
        @BindView(R.id.txt_rate)
        TextView tvRate;
        @BindView(R.id.txt_popularity)
        TextView tvPopularity;
        @BindView(R.id.img_photo)
        ImageView imgPhoto;

        TvFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
