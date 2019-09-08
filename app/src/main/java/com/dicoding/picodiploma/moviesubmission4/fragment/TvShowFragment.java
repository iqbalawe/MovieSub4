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
import com.dicoding.picodiploma.moviesubmission4.activity.DetailTvShowActivity;
import com.dicoding.picodiploma.moviesubmission4.adapter.TvShowAdapter;
import com.dicoding.picodiploma.moviesubmission4.model.TvShow;
import com.dicoding.picodiploma.moviesubmission4.viewmodel.TvShowViewModel;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private TvShowAdapter tvShowAdapter;
    private TvShowViewModel tvShowViewModel;
    private static final int ADD_REQUEST_CODE = 1;
    private static final int ADD_TV_SHOW_RESULT_CODE = 101;
    @BindView(R.id.rvTvShow)
    RecyclerView rvTvShow;
    @BindString(R.string.add_favorite)
    String addFavorite;
    @BindView(R.id.progressBarTvShow)
    ProgressBar progressBarTvShow;


    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        progressBarTvShow.setVisibility(View.VISIBLE);
        rvTvShow.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvTvShow.setHasFixedSize(true);
        tvShowAdapter = new TvShowAdapter(getActivity());

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvShowData().observe(this, getTvShow);
    }

    private final Observer<List<TvShow>> getTvShow = new Observer<List<TvShow>>() {
        @Override
        public void onChanged(List<TvShow> tvShowList) {
            tvShowAdapter.setTvShowAdapter(tvShowList);
            tvShowAdapter.notifyDataSetChanged();
            rvTvShow.setAdapter(tvShowAdapter);
            progressBarTvShow.setVisibility(View.GONE);

            ItemClickSupport.addTo(rvTvShow).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(recyclerView.getContext(), DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_TV_SHOW, tvShowList.get(position));
                startActivityForResult(intent, ADD_REQUEST_CODE);
            });
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE) {
            if (resultCode == ADD_TV_SHOW_RESULT_CODE) {
                TvShow tvShow = data.getParcelableExtra(DetailTvShowActivity.EXTRA_TV_SHOW);
                tvShowViewModel.insertTvShow(tvShow);
                Toast.makeText(getContext(), tvShow.getOriginalName() + " " + addFavorite,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
