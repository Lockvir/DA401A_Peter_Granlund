package petergranlund.assignment_2;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 2015-09-09.
 */
public class MovieManager implements Parcelable {

    private List<MovieRef> mMovieListRef;

    MovieManager(Context context, int listId)
    {
        Log.i("MovieManager", "Constructer()");
        mMovieListRef = new ArrayList<MovieRef>();
        PopulateMovies(context,listId);
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
                /**
                 *                 Had to change from storing the pictures to storing the int id
                 *                 for the drawables because they are to big for the phone to handle
                 *                 and craches the program with out of memory exception.
                 */
                mMovieListRef.add(new MovieRef(movie.getString(0), movie.getString(1), movie.getString(2), movie.getResourceId(3,3),movie.getResourceId(4,3)));
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

    protected MovieManager(Parcel in)
    {
        if(in.readByte() == 0x01)
        {
            mMovieListRef = new ArrayList<MovieRef>();
            in.readList(mMovieListRef, MovieRef.class.getClassLoader());
        }
        else
        {
            mMovieListRef = null;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if(mMovieListRef == null)
        {
            dest.writeByte((byte) (0x00));
        }
        else
        {
            dest.writeByte((byte) (0x01));
            dest.writeList(mMovieListRef);
        }
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
