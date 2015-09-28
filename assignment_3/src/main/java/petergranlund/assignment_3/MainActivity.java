package petergranlund.assignment_3;

import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String urlText = "https://api-v2launch.trakt.tv/movies/popular?extended=images";
    String[] tractApiKey = {"trakt-api-key", "492a165927bfaff86b3030454939981d4e2d94c50515e15e42f41fbf57481a44"};
    String[] tractApiVerision = {"trakt-api-version", "2"};
    private JSONArray jsonArray;
    private List<Movie> movieList;

    private static final String TAG_TITLE = "title";
    private static final String TAG_YEAR = "year";
    private static final String TAG_IMAGE = "images";
    private static final String TAG_POSTER = "poster";
    private static final String TAG_THUMB = "thumb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            Log.i("MainActivity" , "onCreate");
            new PageDownloaderTask().execute(urlText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class PageDownloaderTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Log.i("MainActivity", "onPreExecute");
        }



        @Override
        protected String doInBackground(String... urls) {
            Log.i("MainActivity" , "doInBackground");
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("MainActivity" , "onPostExecute");


            //region Write the objects to check if they are correct. and debug stuff
            //Log.i("MainActivity", result);
            /*for (int i =0;i<movieList.size();i++)
            {
                Log.i("jsonParse", movieList.get(i).getTitle() + "\n" + String.valueOf(movieList.get(i).getYear()) + "\n" + movieList.get(i).getPicture());
            }*/
            //endregion

            TopFragment topFrag = new TopFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("movieList", (ArrayList<? extends Parcelable>) movieList);// putParcelable("movieList", (Parcelable) movieList);
            topFrag.setArguments(bundle);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.main_container, topFrag);
            ft.commit();
        }

        private String downloadUrl(String myUrl) throws IOException{
            Log.i("MainActivity" , "downloadUrl");
            Log.i("MainActivity" , "url : " + myUrl);
            InputStream inputStream = null;
            String toJSONArray = null;

            try{
                URL url = new URL(myUrl);


                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000 /** Milliseconds */);
                connection.setConnectTimeout(15000  /** Milliseconds */);
                connection.setRequestProperty(tractApiVerision[0], tractApiVerision[1]);
                connection.setRequestProperty(tractApiKey[0], tractApiKey[1]);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                /** Start the query */
                connection.connect();
                int response = connection.getResponseCode();
                Log.i("MainActivity", "The response is " + response);
                inputStream = connection.getInputStream();
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputString = null;
                while((inputString = streamReader.readLine()) != null)
                {
                    responseStrBuilder.append(inputString);
                }

                toJSONArray = responseStrBuilder.toString();

                if (inputStream != null)
                {
                    inputStream.close();
                }

                Log.i("JSONGet", toJSONArray);

                ParseJSON(toJSONArray);

            } catch (Exception e) {

                Log.i("MainActivity", "Error downloadUrl :" + e.getMessage());
                e.printStackTrace();
            } {
                if (inputStream != null)
                {
                    Log.i("MainActivity" ,"Finally" );
                    inputStream.close();
                }
            }
            return toJSONArray;
        }


    }

    private void ParseJSON(String jsonSting)
    {
        if (jsonSting!=null)
        {
            try{
                jsonArray = new JSONArray(jsonSting);
                movieList = new  ArrayList<Movie>();
                for (int i = 0;i<jsonArray.length();i++)
                {
                    JSONObject jObject = jsonArray.getJSONObject(i);
                    JSONObject jImage = jObject.getJSONObject(TAG_IMAGE);
                    JSONObject jThumb = jImage.getJSONObject(TAG_POSTER);

                    String title = jObject.getString(TAG_TITLE);
                    int year = jObject.getInt(TAG_YEAR);
                    String picture = jThumb.getString(TAG_THUMB);

                    movieList.add(new Movie(title,year,picture));

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("MainActivity", "Failed to parse");
            }
        }


    }
}
