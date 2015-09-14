package petergranlund.assignment_2;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//import android.graphics.Movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 2015-09-09.
 */
public class MovieManager implements Parcelable {

    private List<MovieRef> mMovieListRef;

    MovieManager()
    {
        Log.i("MovieManager", "Constructer()");
        mMovieListRef = new ArrayList<MovieRef>();
    }

    public void PopulateMovies(Context context, int listId)
    {
        try {
            Log.i("MovieManager", "PopulateMovies()");
            Resources res = context.getResources();
            TypedArray movieLoadList = res.obtainTypedArray(listId);
            Log.i("MovieManager", "Hej");
            for (int i = 0; i < movieLoadList.length() - 1; i++) {
                int id = movieLoadList.getResourceId(i,0);
               TypedArray movie = res.obtainTypedArray(id);
                mMovieListRef.add(new MovieRef(movie.getString(0), movie.getString(1), movie.getString(2), movie.getResourceId(3,3),movie.getResourceId(4,3)));// Had to change from storing the pictures to storing the int id for the drawables because they are to big for the phone to handle and craches the program with out of memory exception.
                movie.recycle();
            }
            Log.i("MovieManager", Integer.toString(mMovieListRef.size()));
            movieLoadList.recycle();
        }
        catch (Exception e)
        {
            Log.i("MovieManager", e.getMessage());
        }
    }

    public List<MovieRef> GetMovieList()
    {
        return mMovieListRef;
    }

    public MovieRef GetMovieByName(String name)
    {
        for (MovieRef movie: mMovieListRef)
        {
            if (movie.getMovieTitle().equals(name))
            {
                return movie;
            }
        }
        return null;
    }

    public int GetSizeOfMovieList()
    {
        return mMovieListRef.size();
    }

    public MovieRef GetMovie(int index)
    {
        return mMovieListRef.get(index);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mMovieListRef);
    }

    /*
    Saved for future reference.
    //Static Field used to regenerate object, individually or as arrays
    public static  final Parcelable.Creator<MovieManager> CREATOR = new Parcelable.ClassLoaderCreator<MovieManager>()
    {

        @Override
        public MovieManager createFromParcel(Parcel source) {
            return new MovieManager();
        }

        @Override
        public MovieManager[] newArray(int size) {
            return new MovieManager[0];
        }

        @Override
        public MovieManager createFromParcel(Parcel source, ClassLoader loader) {
            return null;//new MovieManager(source,loader);
        }

    };
    //To use with creator from parcel, reads back fields IN THE ORDER they were written
    public MovieManager(Parcel source)
    {
        Log.i("MovieManager", "From Parcel" + Integer.toString( mMovieList.size()));
        mMovieList = source.readArrayList();
    }
    */
}
