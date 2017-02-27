package com.example.jianyang.mydoordash.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jianyang.mydoordash.R;
import com.example.jianyang.mydoordash.adapters.RestaurantListAdapter;
import com.example.jianyang.mydoordash.models.Restaurant;
import com.example.jianyang.mydoordash.models.SharedPreference;

import java.util.ArrayList;

/**
 * Created by jianyang on 2/25/17.
 */

public class FavoriteListFragment extends Fragment {


    public static String TAG = "FavoriteListFragment";
    private ListView mListView;
    private ArrayList<Restaurant> mFavoriteList = new ArrayList<>();
    SharedPreference sharedPreference;

    RestaurantListAdapter restaurantListAdapter;
    Activity activity;

    private View view = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        //Get favorite restaurants from sharedPrefs
        sharedPreference = new SharedPreference();
        mFavoriteList = sharedPreference.getFavorites(activity);

        if (mFavoriteList == null || mFavoriteList.isEmpty()) {
            showAlert(getResources().getString(R.string.no_favorites_items),
                    getResources().getString(R.string.no_favorites_msg));
        } else {
            mListView = (ListView) view.findViewById(R.id.list_restaurant);
            restaurantListAdapter = new RestaurantListAdapter(activity, mFavoriteList);
            mListView.setAdapter(restaurantListAdapter);
        }

        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        activity.setTitle(R.string.favorites);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.favorites);
        super.onResume();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    public void showAlert(String title, String message) {
        if (activity != null && !activity.isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getFragmentManager().popBackStackImmediate();
                        }
                    });
            alertDialog.show();
        }
    }
}
