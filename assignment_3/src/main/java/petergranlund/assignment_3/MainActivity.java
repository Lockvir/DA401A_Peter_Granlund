package petergranlund.assignment_3;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.net.ConnectivityManagerCompat;
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

    String urlText = "https://api-v2launch.trakt.tv/movies/popular?extended=images";//"http://ip.jsontest.com/";//"https://apiv-2launch.trakt.tv/movies/popular?extended=images";
    String[] tractApiKey = {"trakt-api-key", "492a165927bfaff86b3030454939981d4e2d94c50515e15e42f41fbf57481a44"};
    String[] tractApiVerision = {"trakt-api-version", "2"};
    //"https://api.github.com/zen?access_token=0f892e365071c7e778a020e463d715b8ccb816f5";
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private List<Movie> movieList;

    private static final String TAG_TITLE = "title";
    private static final String TAG_YEAR = "year";
    private static final String TAG_IMAGE = "images";
    private static final String TAG_POSTER = "poster";
    private static final String TAG_THUMB = "thumb";
    //private static final String TAG_YEAR = "year";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            Log.i("MainActivity" , "onCreate");
        //ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        /*NetworkInfo networkInfo = connMgr.getNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            //fetch data
            //String toJSONArray =
            */
            new PageDownloaderTask().execute(urlText);


        //}
       /* else
        {
            //display error.
        }*/




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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


    private class PageDownloaderTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;
        //ProgressBar progressBar;


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Log.i("MainActivity", "onPreExecute");
        /*
        dialog = new ProgressDialog(dialog.getContext());
        dialog.setMessage("Loading, please wait");
        dialog.setTitle("Connecting server");
        dialog.show();
        dialog.setCancelable(false);
        //progressBar = new ProgressBar(progressBar.getContext());
        */
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
            //textView.setText(result);
            Log.i("MainActivity" , "onPostExecute");
            //Log.i("MainActivity", result);

            /** Write the objects to check if they are correct. */
            /*for (int i =0;i<movieList.size();i++)
            {
                Log.i("jsonParse", movieList.get(i).getTitle() + "\n" + String.valueOf(movieList.get(i).getYear()) + "\n" + movieList.get(i).getPicture());
            }*/

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
            // int len = 500;

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

                String inputString;
                while((inputString = streamReader.readLine()) != null)
                {
                    responseStrBuilder.append(inputString);
                }

                toJSONArray = responseStrBuilder.toString();

                //jsonObject = new JSONObject(toJSONArray);

                if (inputStream != null)
                {
                    inputStream.close();
                }

                Log.i("JSONGet", toJSONArray);

                ParseJSON(toJSONArray);

            } catch (Exception e /*JSONException e*/) {

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
