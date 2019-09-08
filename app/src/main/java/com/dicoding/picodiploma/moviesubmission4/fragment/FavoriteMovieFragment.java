package com.dicoding.picodiploma.moviesubmission4.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.picodiploma.moviesubmission4.ItemClickSupport;
import com.dicoding.picodiploma.moviesubmission4.R;
import com.dicoding.picodiploma.moviesubmission4.activity.DetailMovieActivity;
import com.dicoding.picodiploma.moviesubmission4.adapter.MovieFavoriteAdapter;
import com.dicoding.picodiploma.moviesubmission4.model.Movie;
import com.dicoding.picodiploma.moviesubmission4.viewmodel.MovieViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {
    private static final int ADD_REQUEST_CODE = 1;
    private MovieFavoriteAdapter favoriteAdapter;
    @BindView(R.id.rvMovie)
    RecyclerView rvMovie;


    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        rvMovie.setLayoutManager(new LinearLayoutManager(view.getContext()));
        favoriteAdapter = new MovieFavoriteAdapter(getActivity());
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(favoriteAdapter);

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getAllMovie().observe(this, getFavoriteMovie);

    }

    private final Observer<List<Movie>> getFavoriteMovie = new Observer<List<Movie>>() {
        @Override
        public void onChanged(List<Movie> movieResultsList) {
            favoriteAdapter.setFavoriteAdapter(movieResultsList);
            favoriteAdapter.notifyDataSetChanged();
            ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(getContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieResultsList.get(position));
                startActivityForResult(intent, ADD_REQUEST_CODE);
            });
        }
    };
}
