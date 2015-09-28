package petergranlund.assignment_3;


//import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment
{
    private List<Movie> mMovieList;

    public TopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieList = getArguments().getParcelableArrayList("movieList");

        /*for (int i =0;i<mMovieList.size();i++)
        {
            Log.i("TopFragment", mMovieList.get(i).getTitle() + "\n" + String.valueOf(mMovieList.get(i).getYear()) + "\n" + mMovieList.get(i).getPicture());
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("TopFragment", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_top, container,false);
        AsyncAdapter asyncAdapter = new AsyncAdapter(getContext(),mMovieList);
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_overview_grid);
        gridView.setAdapter(asyncAdapter);
        Log.i("TopFragment", "number of movies " + Integer.toString(mMovieList.size()));

        return rootView;
    }
}
