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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.picodiploma.moviesubmission4.ItemClickSupport;
import com.dicoding.picodiploma.moviesubmission4.R;
import com.dicoding.picodiploma.moviesubmission4.activity.DetailMovieActivity;
import com.dicoding.picodiploma.moviesubmission4.activity.DetailTvShowActivity;
import com.dicoding.picodiploma.moviesubmission4.adapter.TvShowFavoriteAdapter;
import com.dicoding.picodiploma.moviesubmission4.model.TvShow;
import com.dicoding.picodiploma.moviesubmission4.viewmodel.TvShowViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment {


    private static final int ADD_REQUEST_CODE = 1;
    private TvShowFavoriteAdapter favoriteAdapter;
    @BindView(R.id.rvTvShow)
    RecyclerView rvTvShow;


    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        rvTvShow.setLayoutManager(new LinearLayoutManager(view.getContext()));
        favoriteAdapter = new TvShowFavoriteAdapter(getActivity());
        rvTvShow.setHasFixedSize(true);
        rvTvShow.setAdapter(favoriteAdapter);

        TvShowViewModel tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getAllTvShow().observe(this, getFavoriteTvShow);

    }

    private final Observer<List<TvShow>> getFavoriteTvShow = new Observer<List<TvShow>>() {
        @Override
        public void onChanged(List<TvShow> tvShowList) {
            favoriteAdapter.setTvFavoriteAdapter(tvShowList);
            favoriteAdapter.notifyDataSetChanged();
            ItemClickSupport.addTo(rvTvShow).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(getContext(), DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_TV_SHOW, tvShowList.get(position));
                startActivityForResult(intent, ADD_REQUEST_CODE);
            });
        }
    };
}
