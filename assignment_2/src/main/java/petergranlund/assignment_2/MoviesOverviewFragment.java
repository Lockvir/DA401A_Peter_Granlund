package petergranlund.assignment_2;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import petergranlund.da401a_peter_granlund.MovieAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesOverviewFragment extends Fragment implements AdapterView.OnItemClickListener {


    MovieManager mMovieManager;
    private MyFragmentListener.OnMyFragmentListener mCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieManager = getArguments().getParcelable("mMovieManager");
         /*try{
            // mMovieManager.PopulateMovies(getContext(),R.array.movies);
        }
        catch (Exception e)
        {
            Log.i("MoviesOverviewFragment", e.getMessage());
        }*/
    }

    /**
    * Creates the view and returns it. There is a big memory problem on the grid view because the
    * pictures are to big the phone runs out of memory and crashes :( don't know if there is a way
    * to scale down the resolution of the picture. If that even helps.
    * Been looking around. Apparently it's a known android bug. and has nothing to do with the
    * size of the picture. there is a workaround that may work. where yo manually do a bitmap decode
    * stream of the pictures.
    * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("MoviesOverviewFragment", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_movies_overview,container,false);
        MovieAdapter movieAdapter = new MovieAdapter(getContext(),mMovieManager);
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_overview_grid);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(this);
        Log.i("MoviesOverviewFragment", "number of elements : " + Integer.toString(movieAdapter.getCount()));

        return rootView;
    }

    /**
     * Must have. otherwise the mCallback object will be null.
      */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (MyFragmentListener.OnMyFragmentListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    /**
     *     Uses the onArticleSelected method to communicate to the activity that an interaction
     *     has happened and sends the position in the array that corresponds to the item that
     *     was selected.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mCallback.onArticleSelected(mMovieManager.GetMovie(position));
        Log.i("MoviesOverviewFragment", "onItemClick Outside");
    }
}


