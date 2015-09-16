package petergranlund.assignment_2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Peter on 2015-09-13.
 *
 * Ran into a Out of memory problem and crash. I think this is because the pictures are to big in
 * size. So instead of loding them all into memory i stor onely the reference to them. Sadly this
 * forces all who use this class to also need a connection to the Resouces.
 * Maybe there is a way to scale down the pictures in program?
 */
public class MovieRef implements Parcelable {

    private String movieTitle;
    private String movieYear;
    private String movieDescription;
    private int moviePosterId;
    private int movieFanartId;

    public MovieRef(String movieTitle,String movieYear,String movieDescription,int movieFanartId,int moviePosterId) {
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.movieDescription = movieDescription;
        this.moviePosterId = moviePosterId;
        this.movieFanartId = movieFanartId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public int getMoviePoster() {
        return moviePosterId;
    }

    public int getMovieFanart() {
        return movieFanartId;
    }


    public MovieRef(Parcel in)
    {
        String[] data = new String[3];
        int[] intData = new int[2];

        in.readStringArray(data);
        this.movieTitle = data[0];
        this.movieYear = data[1];
        this.movieDescription = data[2];
        in.readIntArray(intData);
        this.moviePosterId = intData[0];
        this.movieFanartId = intData[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(
                new String[] {
                        this.movieTitle
                        , this.movieYear
                        , this.movieDescription})};

    }
}
