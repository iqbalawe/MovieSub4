package com.dicoding.picodiploma.moviesubmission4.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.picodiploma.moviesubmission4.Config;
import com.dicoding.picodiploma.moviesubmission4.R;
import com.dicoding.picodiploma.moviesubmission4.model.Movie;
import com.dicoding.picodiploma.moviesubmission4.model.TvShow;
import com.dicoding.picodiploma.moviesubmission4.viewmodel.MovieViewModel;
import com.dicoding.picodiploma.moviesubmission4.viewmodel.TvShowViewModel;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailTvShowActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TV_SHOW = "extra_tv_show";
    private static final int ADD_TV_SHOW_RESULT_CODE = 101;
    private TvShow tvShow;
    private TvShowViewModel tvShowViewModel;

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

        tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        tvTitle.setText(tvShow.getOriginalName());
        tvOverview.setText(tvShow.getOverview());
        tvLanguage.setText(tvShow.getOriginalLanguage());
        tvVoteAverage.setText(String.valueOf(tvShow.getVoteAverage()));
        tvVoteCount.setText(String.valueOf(tvShow.getVoteCount()));

        String urlPhoto = Config.IMAGE_URL + tvShow.getPosterPath();
        Glide.with(this)
                .load(urlPhoto)
                .apply(new RequestOptions())
                .into(imgPhoto);

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getIdTvShow(tvShow.getId()).observe(this, tvShowById);
        imgFavorite.setOnClickListener(this);
    }

    private final Observer<List<TvShow>> tvShowById = new Observer<List<TvShow>>() {
        @Override
        public void onChanged(List<TvShow> tvShowList) {
            if (!tvShowList.isEmpty()) {
                tvShow.setFavorite(true);
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_favorite)
                        .into(imgFavorite);
                tvFavorite.setText(removeFavorite);

            } else {
                tvShow.setFavorite(false);
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
            if (!tvShow.isFavorite()) {
                Glide.with(this)
                        .load(R.drawable.ic_favorite)
                        .into(imgFavorite);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_TV_SHOW, tvShow);
                setResult(ADD_TV_SHOW_RESULT_CODE, intent);
            } else {
                Glide.with(this)
                        .load(R.drawable.ic_favorite_black)
                        .into(imgFavorite);
                Toast.makeText(this, tvShow.getOriginalName() + " " + removeFavorite,
                        Toast.LENGTH_SHORT).show();
                tvShowViewModel.deleteSelectedTvShow(tvShow);
            }
        }
        finish();
    }
}
