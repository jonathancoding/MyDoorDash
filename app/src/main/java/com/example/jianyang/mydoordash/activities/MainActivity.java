package com.example.jianyang.mydoordash.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jianyang.mydoordash.R;
import com.example.jianyang.mydoordash.fragments.FavoriteListFragment;
import com.example.jianyang.mydoordash.fragments.RestaurantListFragment;



public class MainActivity extends AppCompatActivity {

    private Fragment contentFragment;
    RestaurantListFragment restaurantListFragment;
    FavoriteListFragment favoriteListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(FavoriteListFragment.TAG)) {
                    if (fragmentManager.findFragmentByTag(FavoriteListFragment.TAG) != null) {
                        setFragmentTitle(R.string.favorites);
                        contentFragment = fragmentManager
                                .findFragmentByTag(FavoriteListFragment.TAG);
                    }
                } else if (fragmentManager.findFragmentByTag(RestaurantListFragment.TAG) != null) {
                    restaurantListFragment = (RestaurantListFragment) fragmentManager
                            .findFragmentByTag(RestaurantListFragment.TAG);
                    contentFragment = restaurantListFragment;
                }
            }
        } else {
            restaurantListFragment = new RestaurantListFragment();
            switchContent(restaurantListFragment, RestaurantListFragment.TAG);
        }

    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof RestaurantListFragment
                || fm.getBackStackEntryCount() == 0) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof FavoriteListFragment) {
            outState.putString("content", FavoriteListFragment.TAG);
        } else {
            outState.putString("content", restaurantListFragment.TAG);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_favorites:
                setFragmentTitle(R.string.favorites);
                favoriteListFragment = new FavoriteListFragment();
                switchContent(favoriteListFragment, FavoriteListFragment.TAG);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setFragmentTitle(int resourseId) {
        setTitle(resourseId);
        getSupportActionBar().setTitle(resourseId);

    }

    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate());

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            //Only FavoriteListFragment is added to the back stack.
            if (!(fragment instanceof RestaurantListFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }
}
