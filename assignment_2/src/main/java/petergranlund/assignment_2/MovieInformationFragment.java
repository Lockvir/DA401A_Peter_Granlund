package petergranlund.assignment_2;


import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieInformationFragment extends Fragment {


    public MovieInformationFragment() {
        // Required empty public constructor
    }

    /*
    *   Would have been better if i used the MovieManager to get the information. But since i
    *   don't know how to send objects in objects i settled for this solution. I had problems
    *   storing the drawables in MovieManager so thouse would have to be loaded here anyway. :(
    *   The title year has problems if it is too long. and goes behind the picture
    *   needs to be corrected.
    * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int witchMovie = getArguments().getInt("position");
        Resources resources = getResources();
        TypedArray typedArray = resources.obtainTypedArray(resources.obtainTypedArray(R.array.movies).getResourceId(witchMovie, 0));
        View rootView = inflater.inflate(R.layout.fragment_movie_information,container,false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.picture_movie_info_frag);
        imageView.setImageDrawable(typedArray.getDrawable(3));

        TextView titleYearTextView = (TextView) rootView.findViewById(R.id.title_and_year_movie_info_frag);
        titleYearTextView.setText(typedArray.getText(0) + "\n" + typedArray.getText(1));

        TextView descriptionTextView = (TextView) rootView.findViewById(R.id.description_movie_info_frag);
        descriptionTextView.setText(typedArray.getString(2));

        Log.i("MovieInfoFrag", "description text : " + typedArray.getString(2));

        typedArray.recycle();
        return rootView;
    }


}
