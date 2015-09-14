package petergranlund.da401a_peter_granlund;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import petergranlund.assignment_2.AMovie;
import petergranlund.assignment_2.MovieManager;
import petergranlund.assignment_2.MovieRef;
import petergranlund.assignment_2.R;

/**
 * Created by Peter on 2015-09-10.
 */
public class MovieAdapter extends BaseAdapter {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private MovieManager mMovieManager;

    public MovieAdapter(Context c, MovieManager movieManager)
    {
        Log.i("MovieAdapter", "Constructor");
        this.mContext = c;
        mLayoutInflater = LayoutInflater.from(c);
        this.mMovieManager = movieManager;
    }
/*
    @Override
    public int getCount() {
        return mMovieManager.GetSizeOfMovieList();
    }

    @Override
    public AMovie getItem(int position) {
        return mMovieManager.GetMovie(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        return convertView;
    }
    */

    @Override
    public int getCount() {
        return mMovieManager.GetSizeOfMovieList();
    }

    @Override
    public MovieRef getItem(int position) {
        return mMovieManager.GetMovie(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //AMovie movie = mMovieManager.GetMovie(position);
        MovieRef movie = mMovieManager.GetMovie(position);
        if (convertView == null)
        {
            convertView = mLayoutInflater.inflate(R.layout.fragment_movies_overview,parent,false);
            Log.i("MovieAdapter", "getView Infalte");
        }

        //the (ImageView) mean we are casting it to widget image view in stead of having a normal view. android.widget.ImageView;
        ImageView gridPicture = (ImageView) convertView.findViewById(R.id.in_grid_picture);
        if (gridPicture != null)
        {
            gridPicture.setImageDrawable(convertView.getResources().getDrawable(movie.getMoviePoster()));
            Log.i("MovieAdapter", "getView setDrawable");
        }

        //the (TextView) mean we are casting it to widget text view in stead of having a normal view. android.widget.TextView;
        TextView gridMovieTitleAndYear = (TextView) convertView.findViewById(R.id.in_grid_title_name_year);
        if(gridMovieTitleAndYear != null)
        {
            gridMovieTitleAndYear.setText(movie.getMovieTitle() + "\n" + movie.getMovieYear());
            Log.i("MovieAdapter", "getView setText");
        }

        Log.i("MovieAdapter", "getView " + position);

        return convertView;
    }
}
