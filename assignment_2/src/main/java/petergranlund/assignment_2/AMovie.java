package petergranlund.assignment_2;

import android.graphics.drawable.Drawable;

/**
 * Created by Peter on 2015-09-09.
 */
public class AMovie {

    private String movieTitle;
    private String movieYear;
    private String movieDescription;
    private Drawable moviePoster;
    private Drawable movieFanart;

    public AMovie(String movieTitle,String movieYear,String movieDescription,Drawable movieFanart,Drawable moviePoster )
    {
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.movieDescription = movieDescription;
        this.moviePoster = moviePoster;
        this.movieFanart = movieFanart;
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

    public Drawable getMoviePoster() {
        return moviePoster;
    }

    public Drawable getMovieFanart() {
        return movieFanart;
    }
}
