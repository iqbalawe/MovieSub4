package com.dicoding.picodiploma.moviesubmission4.adapter;

import android.content.Context;
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
import com.dicoding.picodiploma.moviesubmission4.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private List<Movie> listMovie;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setListMovie(List<Movie> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.tvTitle.setText(listMovie.get(position).getTitle());
        holder.tvDate.setText(listMovie.get(position).getReleaseDate());
        holder.tvRate.setText(String.valueOf(listMovie.get(position).getVoteAverage()));
        holder.tvPopularity.setText(String.valueOf(listMovie.get(position).getPopularity()));
        String urlPhoto = Config.IMAGE_URL + listMovie.get(position).getPosterPath();
        Glide.with(context)
                .load(urlPhoto)
                .apply(new RequestOptions().override(100, 140))
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
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

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
