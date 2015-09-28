package petergranlund.assignment_3;

//import android.graphics.drawable.Drawable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Peter on 2015-09-20.
 */
public class Movie implements Parcelable {

    private String title;
    private int year;
    private String picture;

    public Movie(String title, int year, String picture) {
        this.title = title;
        this.year = year;
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    protected Movie(Parcel in) {
        title = in.readString();
        year = in.readInt();
        picture = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(year);
        dest.writeString(picture);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
