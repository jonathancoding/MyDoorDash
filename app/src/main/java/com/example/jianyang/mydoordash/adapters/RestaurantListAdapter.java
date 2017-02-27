package com.example.jianyang.mydoordash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jianyang.mydoordash.R;
import com.example.jianyang.mydoordash.models.Restaurant;
import com.example.jianyang.mydoordash.models.SharedPreference;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jianyang on 2/25/17.
 */

public class RestaurantListAdapter extends ArrayAdapter<Restaurant>{

    private Context context;
    private ArrayList<Restaurant> mResturantList;
    private SharedPreference sharedPreference;


    public RestaurantListAdapter(Context context, ArrayList<Restaurant> restaurantList)
    {
        super(context, R.layout.restaurant_list_item, restaurantList);
        this.context = context;
        mResturantList = restaurantList;
        sharedPreference = new SharedPreference();
    }


    @Override
    public int getCount() {
        return mResturantList.size();
    }

    @Override
    public Restaurant getItem(int i) {
        return mResturantList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    static class ViewHolderItem
    {
        TextView tvName;
        TextView tvAddress;
        TextView tvDescription;

        ImageView thumbNail;
        ImageButton favoriteButton;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolderItem viewHolder;
        // Pass imageView reference to viewHolder so that we could reuse it
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.restaurant_list_item, null);
            viewHolder = new ViewHolderItem();
            viewHolder.thumbNail = (ImageView) convertView.findViewById(R.id.imageView_restaurant_thumbnail);
            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tv_restaurant_address);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tv_restaurant_description);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_restaurant_name);
            viewHolder.favoriteButton = (ImageButton)convertView.findViewById(R.id.imageButton_favorite);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        // Populate imageView with image url

        if ( mResturantList.get(i).getCover_img_url().equals("")) {
            Picasso.with(context).load(android.R.drawable.ic_btn_speak_now).into(viewHolder.thumbNail);
        } else {
            Picasso.with(context).load(mResturantList.get(i).getCover_img_url()).into(viewHolder.thumbNail);
        }

        viewHolder.tvName.setText(mResturantList.get(i).getName());
        viewHolder.tvAddress.setText(mResturantList.get(i).getAddress());
        viewHolder.tvDescription.setText(mResturantList.get(i).getDescription());

        if (checkFavoriteItem(mResturantList.get(i))) {
            viewHolder.favoriteButton.setImageResource(R.drawable.heart_red);
            viewHolder.favoriteButton.setTag("red");
        } else {
            viewHolder.favoriteButton.setImageResource(R.drawable.heart_grey);
            viewHolder.favoriteButton.setTag("grey");
        }

        viewHolder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = view.getTag().toString();
                if (tag.equalsIgnoreCase("grey")) {
                    sharedPreference.addFavorite(context, mResturantList.get(i));
                    viewHolder.favoriteButton.setTag("red");
                    viewHolder.favoriteButton.setImageResource(R.drawable.heart_red);
                } else {
                    sharedPreference.removeFavorite(context, mResturantList.get(i));
                    viewHolder.favoriteButton.setTag("grey");
                    viewHolder.favoriteButton.setImageResource(R.drawable.heart_grey);
                }
            }
        });


        return convertView;
    }


    public boolean checkFavoriteItem(Restaurant restaurant) {
        boolean check = false;
        ArrayList<Restaurant> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (Restaurant restaurant1 : favorites) {
                System.out.println("restuarant1: checking" + restaurant1.getId());
                System.out.println("restuarant: checking" + restaurant.getId());
                if (restaurant1.equals(restaurant)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public void add(Restaurant restaurant) {
        super.add(restaurant);
        mResturantList.add(restaurant);
        notifyDataSetChanged();
    }

    @Override
    public void remove(Restaurant restaurant) {
        super.remove(restaurant);
        mResturantList.remove(restaurant);
        notifyDataSetChanged();
    }

}
