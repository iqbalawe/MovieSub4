package com.dicoding.picodiploma.moviesubmission4.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.picodiploma.moviesubmission4.ItemClickSupport;
import com.dicoding.picodiploma.moviesubmission4.R;
import com.dicoding.picodiploma.moviesubmission4.activity.DetailMovieActivity;
import com.dicoding.picodiploma.moviesubmission4.adapter.MovieAdapter;
import com.dicoding.picodiploma.moviesubmission4.model.Movie;
import com.dicoding.picodiploma.moviesubmission4.viewmodel.MovieViewModel;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;
    private static final int ADD_REQUEST_CODE = 1;
    private static final int ADD_MOVIE_RESULT_CODE = 101;
    @BindView(R.id.rvMovie)
    RecyclerView rvMovie;
    @BindString(R.string.add_favorite)
    String addFavorite;
    @BindView(R.id.progressBarMovie)
    ProgressBar progressBarMovie;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        progressBarMovie.setVisibility(View.VISIBLE);
        rvMovie.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvMovie.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(view.getContext());

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovieData().observe(this, getMovie);
    }

    private final Observer<List<Movie>> getMovie = new Observer<List<Movie>>() {
        @Override
        public void onChanged(List<Movie> movieList) {
            movieAdapter.setListMovie(movieList);
            rvMovie.setAdapter(movieAdapter);
            movieAdapter.notifyDataSetChanged();
            progressBarMovie.setVisibility(View.GONE);
            ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
                Intent movieIntent = new Intent(getActivity(), DetailMovieActivity.class);
                movieIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieList.get(position));
                startActivityForResult(movieIntent, ADD_REQUEST_CODE);
            });
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE) {
            if (resultCode == ADD_MOVIE_RESULT_CODE) {
                Movie movie;
                movie = data.getParcelableExtra(DetailMovieActivity.EXTRA_MOVIE);
                movieViewModel.insert(movie);
                Toast.makeText(getContext(), movie.getTitle() + " " + addFavorite
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }
}
