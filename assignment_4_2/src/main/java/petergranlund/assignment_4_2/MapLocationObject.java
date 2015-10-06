package petergranlund.assignment_4_2;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Peter on 2015-09-29.
 */
public class MapLocationObject implements Parcelable {
    private LatLng mLatLng;
    private String mCountry;
    private String mCity;
    private String mMarkerHeader;
    private String mMarkerText;
    private Marker mMarker;
    private String locationQuestion;
    private String answer1;
    private String answer2;
    private String answer3;

    public MapLocationObject(double lat, double lng, String mCountry, String mCity, String mMarkerHeader) {
        Log.i("MapLocationObject", "Constructor");
        this.mLatLng = new LatLng(lat,lng);
        this.mCountry = mCountry;
        this.mCity = mCity;
        this.mMarkerHeader = mMarkerHeader;
        mMarker = null;
        locationQuestion = "Who proposed that we should aim to maximize happiness in the world?";
        answer1 = "Jeremy\nBentham";
        answer2 = "Immanuel\nKant";
        answer3 = "Plato";
    }

    public LatLng getmLatLng() {
        return mLatLng;
    }

    public String getmCountry() {
        return mCountry;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmMarkerHeader() {
        return mMarkerHeader;
    }

    public String getLocationQuestion() {
        return locationQuestion;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public Marker MakeMaker(GoogleMap mMap)
    {
        Log.i("MapLocationObject", "MakeMarker");
        mMarker = mMap.addMarker(new MarkerOptions().position(mLatLng).title(mMarkerHeader).snippet(mMarkerText).icon(BitmapDescriptorFactory.fromResource(R.drawable.army)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 13));
        return mMarker;
    }

    public Marker getmMarker() {
        return mMarker;
    }

    protected MapLocationObject(Parcel in) {
        mLatLng = (LatLng) in.readValue(LatLng.class.getClassLoader());
        mCountry = in.readString();
        mCity = in.readString();
        mMarkerHeader = in.readString();
        mMarkerText = in.readString();
        mMarker = (Marker) in.readValue(Marker.class.getClassLoader());
        locationQuestion = in.readString();
        answer1 = in.readString();
        answer2 = in.readString();
        answer3 = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mLatLng);
        dest.writeString(mCountry);
        dest.writeString(mCity);
        dest.writeString(mMarkerHeader);
        dest.writeString(mMarkerText);
        dest.writeValue(mMarker);
        dest.writeString(locationQuestion);
        dest.writeString(answer1);
        dest.writeString(answer2);
        dest.writeString(answer3);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MapLocationObject> CREATOR = new Parcelable.Creator<MapLocationObject>() {
        @Override
        public MapLocationObject createFromParcel(Parcel in) {
            return new MapLocationObject(in);
        }

        @Override
        public MapLocationObject[] newArray(int size) {
            return new MapLocationObject[size];
        }
    };
}
