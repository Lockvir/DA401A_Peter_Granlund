package petergranlund.da401a_peter_granlund;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

public class Assignment_1 extends AppCompatActivity
        implements FirstScreen.OnFragmentInteractionListener
        , SecondScreen.OnFragmentInteractionListener {

    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_1);

        if(findViewById(R.id.not_here) != null) {

            if (savedInstanceState != null)
            {
                return;
            }

            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            ft.add(R.id.not_here, new FirstScreen());
            ft.commit();
        }
        Log.i("Assignment_1", "onCreate");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assignment_1, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("FromFirstScreen", "onFragmentInteraction");
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.not_here, new SecondScreen());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
