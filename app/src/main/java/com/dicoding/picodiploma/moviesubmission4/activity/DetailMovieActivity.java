package com.dicoding.picodiploma.moviesubmission4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.picodiploma.moviesubmission4.Config;
import com.dicoding.picodiploma.moviesubmission4.R;
import com.dicoding.picodiploma.moviesubmission4.model.Movie;
import com.dicoding.picodiploma.moviesubmission4.viewmodel.MovieViewModel;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "extra_movie";
    private static final int ADD_MOVIE_RESULT_CODE = 101;
    private Movie movie;
    private MovieViewModel movieViewModel;

    @BindView(R.id.txt_title)
    TextView tvTitle;
    @BindView(R.id.txt_overview)
    TextView tvOverview;
    @BindView(R.id.txt_vote_average)
    TextView tvVoteAverage;
    @BindView(R.id.txt_language)
    TextView tvLanguage;
    @BindView(R.id.txt_vote_count)
    TextView tvVoteCount;
    @BindView(R.id.txt_favorite)
    TextView tvFavorite;
    @BindView(R.id.img_photo)
    ImageView imgPhoto;
    @BindView(R.id.img_favorite)
    ImageView imgFavorite;
    @BindString(R.string.remove_favorite)
    String removeFavorite;
    @BindString(R.string.add_to_favorite_list)
    String favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvLanguage.setText(movie.getOriginalLanguage());
        tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        tvVoteCount.setText(String.valueOf(movie.getVoteCount()));

        String urlPhoto = Config.IMAGE_URL + movie.getPosterPath();
        Glide.with(this)
                .load(urlPhoto)
                .apply(new RequestOptions())
                .into(imgPhoto);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getIdMovie(movie.getId()).observe(this, movieById);
        imgFavorite.setOnClickListener(this);
    }

    private final Observer<List<Movie>> movieById = new Observer<List<Movie>>() {
        @Override
        public void onChanged(List<Movie> movieList) {
            if (!movieList.isEmpty()) {
                movie.setFavorite(true);
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_favorite)
                        .into(imgFavorite);
                tvFavorite.setText(removeFavorite);
            } else {
                movie.setFavorite(false);
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_favorite_black)
                        .into(imgFavorite);
                tvFavorite.setText(favorite);
            }
        }
    };

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_favorite) {
            if (!movie.isFavorite()) {
                Glide.with(this)
                        .load(R.drawable.ic_favorite)
                        .into(imgFavorite);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_MOVIE, movie);
                setResult(ADD_MOVIE_RESULT_CODE, intent);
            } else {
                Glide.with(this)
                        .load(R.drawable.ic_favorite_black)
                        .into(imgFavorite);
                Toast.makeText(this, movie.getTitle() + " " + removeFavorite,
                        Toast.LENGTH_SHORT).show();
                movieViewModel.deleteSelectedMovie(movie);
            }
        }
        finish();
    }
}
