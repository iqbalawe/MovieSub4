package com.dicoding.picodiploma.moviesubmission4.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.picodiploma.moviesubmission4.R;
import com.dicoding.picodiploma.moviesubmission4.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        ViewPagerAdapter fragmentAdapter = new ViewPagerAdapter(getChildFragmentManager());
        fragmentAdapter.addFragment(new FavoriteMovieFragment(), getString(R.string.movie));
        fragmentAdapter.addFragment(new FavoriteTvShowFragment(), getString(R.string.tv_show));
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
