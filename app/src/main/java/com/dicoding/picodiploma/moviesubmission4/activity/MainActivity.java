package com.dicoding.picodiploma.moviesubmission4.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dicoding.picodiploma.moviesubmission4.R;
import com.dicoding.picodiploma.moviesubmission4.fragment.FavoriteFragment;
import com.dicoding.picodiploma.moviesubmission4.fragment.MovieFragment;
import com.dicoding.picodiploma.moviesubmission4.fragment.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        toolbar = getSupportActionBar();

        loadFragment(new MovieFragment());

        if (savedInstanceState == null) {
            setFragment(new MovieFragment(), getResources().getString(R.string.movie));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.menu_movie:
                toolbar.setTitle(getString(R.string.movie));
                fragment = new MovieFragment();
                loadFragment(fragment);
                return true;
            case R.id.menu_tv_show:
                toolbar.setTitle(getString(R.string.tv_show));
                fragment = new TvShowFragment();
                loadFragment(fragment);
                return true;
            case R.id.menu_favorite:
                toolbar.setTitle(getString(R.string.favorite_list));
                fragment = new FavoriteFragment();
                loadFragment(fragment);
                return true;
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void setFragment(Fragment fragment, String title) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_container, fragment);
            ft.commit();
        }
        getSupportActionBar().setTitle(title);
    }
}
