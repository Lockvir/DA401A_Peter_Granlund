package petergranlund.assignment_2;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MyFragmentListener.OnMyFragmentListener {

    MovieManager mMovieManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity", "onCreate");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        /**
         *  Not initializing object here. Still unsure about how bundles work. Otherwise this object has an array of objects.
         */
        mMovieManager = new MovieManager(this,R.array.movies);
        Fragment mof = new MoviesOverviewFragment();

        /**
        *   Creating a bundle that can be used to send objects to other fragments and activitys.
        *   Very useful when working on multiple threads.
        *   The parcelable needs extra code inside the object to work.
        *   Don't know how to send objects holding a ListArray of objects.
        *   Serializable is an alternative but it is slower. and not android specific.
        */
        Bundle bundle = new Bundle();
        bundle.putParcelable("mMovieManager", mMovieManager);
        mof.setArguments(bundle);


        ft.add(R.id.empty_container, mof);
        ft.commit();


    }

    /**
     * Auto generated.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.i("MainActivity", "onCreateOptionsMenu");
        return true;
    }

    /**
     * Auto generated.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /** Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
         */
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
    *   Custom made interaction listener. This listens for interaction from the fragment
    *   it is connected to. Puts the array position that it gets from the interaction
    *   that signifies the selected item and puts it in a bundle so that the other fragment
    *   will know what information to get. Then starts a new fragment that.
    * */
    @Override
    public void onArticleSelected(MovieRef movie/*int position*/) {

        Fragment mif = new MovieInformationFragment();
        Bundle bundle = new Bundle();
        //bundle.putInt("movie", movie);/*"position",position*/);
        bundle.putParcelable("movie", movie);
        mif.setArguments(bundle);

    getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.empty_container, mif)
            .addToBackStack(null).commit();
    }
}
