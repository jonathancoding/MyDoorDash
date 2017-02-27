package com.example.jianyang.mydoordash.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jianyang on 2/26/17.
 */

public class SharedPreference {
    public static final String PREFS_NAME = "MY_DOOR_DASH";
    public static final String FAVORITES = "Restaurant_Favorite";

    public SharedPreference() {
        super();
    }

    public void saveFavorites(Context context, ArrayList<Restaurant> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);

        if (favorites != null && !favorites.isEmpty()) {
            System.out.println("restuarant: saving" + favorites.get(0).getId());
        }

        editor.commit();
    }

    public void addFavorite(Context context, Restaurant restaurant) {
        ArrayList<Restaurant> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<>();
        favorites.add(restaurant);
        System.out.println("restuarant: adding" + restaurant.getId());
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Restaurant restaurant) {
        ArrayList<Restaurant> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(restaurant);
            System.out.println("restuarant: removing" + restaurant.getId());
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Restaurant> getFavorites(Context context) {
        SharedPreferences settings;
        List<Restaurant> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Restaurant[] favoriteItems = gson.fromJson(jsonFavorites,
                    Restaurant[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else
            return null;

        return (ArrayList<Restaurant>) favorites;
    }
}
