package com.example.jianyang.mydoordash.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jianyang.mydoordash.R;
import com.example.jianyang.mydoordash.adapters.RestaurantListAdapter;
import com.example.jianyang.mydoordash.models.Restaurant;
import com.example.jianyang.mydoordash.models.SharedPreference;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jianyang on 2/25/17.
 */

public class RestaurantListFragment extends Fragment{

    public static final String TAG = "RestaurantListFragment";
    private ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();
    private SharedPreference sharedPreference;
    private ListView restaurantListView;
    private ProgressBar mProgressBar;
    Activity activity;

    private View view = null;
    private String endpoint = "https://api.doordash.com/v2/restaurant/?lat=37.422740&lng=-122.139956";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        activity = getActivity();

        sharedPreference = new SharedPreference();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_restaurant_list, container,
                false);
        restaurantListView = (ListView) view.findViewById(R.id.list_restaurant);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progresBar);

        if (savedInstanceState == null) {
            mProgressBar.setVisibility(View.VISIBLE);
            getRestaurantListFromAPI();
        } else {
            RestaurantListAdapter adapter = new RestaurantListAdapter(getActivity(), restaurantArrayList);
            restaurantListView.setAdapter(adapter);
//            restaurantListView.setOnItemClickListener(new OnItemClickListener());
        }


        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("restaurantArrayList", restaurantArrayList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            restaurantArrayList = savedInstanceState.getParcelableArrayList("restaurantArrayList");
        }

    }

    @Override
    public void onResume() {
        activity.setTitle(R.string.app_name);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
        super.onResume();
    }

    private void getRestaurantListFromAPI() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(endpoint)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // ... check for failure using `isSuccessful` before proceeding
                // Read data on the worker thread
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       mProgressBar.setVisibility(View.GONE);
                    }
                });

                try {
                    String responseData = response.body().string();

                    System.out.println("jonathan" + responseData);
                    JSONArray results = new JSONArray(responseData);

                    System.out.println("jonathan" + results.toString());

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject object = results.getJSONObject(i);
                        Restaurant restaurant = new Restaurant();
                        restaurant.setId(object.getString("id"));
                        restaurant.setCover_img_url(object.getString("cover_img_url"));
                        restaurant.setDescription(object.getString("description"));
                        // loop array
                        JSONObject address =  object.getJSONObject("address");
                        JSONObject business =  object.getJSONObject("business");
                        restaurant.setAddress(address.getString("printable_address"));
                        restaurant.setName(business.getString("name"));

                        restaurantArrayList.add(restaurant);
                    }


                    System.out.println("jonathan" + restaurantArrayList.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // If there is any movies
                            if (restaurantArrayList.size() > 0) {
                                // Adding items to gridView
                                RestaurantListAdapter adapter = new RestaurantListAdapter(getActivity(), restaurantArrayList);
                                restaurantListView.setAdapter(adapter);
                                restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        //
                                        Toast.makeText(activity, "More features will be added soon!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            // Display empty data toast
                            else {
                                Toast.makeText(getActivity(), "No Restaurants Found!", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });


                } catch (JSONException e) {

                }

            }
        });

    }

//    class OnItemClickListener implements AdapterView.OnItemClickListener{
//
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            Restaurant restaurant = (Restaurant) adapterView.getItemAtPosition(i);
//            Toast.makeText(activity, restaurant.toString(), Toast.LENGTH_LONG).show();
//        }
//    }


//
//        ImageButton button = (ImageButton) view.findViewById(R.id.imageButton_favorite);
//
//        String tag = button.getTag().toString();
//        if (tag.equalsIgnoreCase("grey")) {
//            sharedPreference.addFavorite(activity, restaurantArrayList.get(i));
//
//            button.setTag("red");
//            button.setImageResource(R.drawable.heart_red);
//        } else {
//            sharedPreference.removeFavorite(activity, restaurantArrayList.get(i));
//            button.setTag("grey");
//            button.setImageResource(R.drawable.heart_grey);
//        }

}
