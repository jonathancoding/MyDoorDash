package com.example.jianyang.mydoordash.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jianyang on 2/25/17.
 */

public class Restaurant implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String cover_img_url;
    private String address;
    private ArrayList<Menu> menus;

    public Restaurant() {

    }
    protected Restaurant(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        cover_img_url = in.readString();
        address = in.readString();
        menus = in.createTypedArrayList(Menu.CREATOR);
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover_img_url() {
        return cover_img_url;
    }

    public void setCover_img_url(String cover_img_url) {
        this.cover_img_url = cover_img_url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(cover_img_url);
        parcel.writeString(address);
        parcel.writeTypedList(menus);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Restaurant && ((Restaurant) o).getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
