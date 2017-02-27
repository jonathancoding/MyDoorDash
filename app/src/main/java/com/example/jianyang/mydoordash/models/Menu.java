package com.example.jianyang.mydoordash.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianyang on 2/25/17.
 */

public class Menu implements Parcelable{
    private String id;
    private String name;
    private boolean isCatering;
    private String subTitle;

    protected Menu(Parcel in) {
        id = in.readString();
        name = in.readString();
        isCatering = in.readByte() != 0;
        subTitle = in.readString();
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeByte((byte) (isCatering ? 1 : 0));
        parcel.writeString(subTitle);
    }
}
