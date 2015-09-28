package petergranlund.assignment_3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Peter on 2015-09-18.
 */
public class AsyncAdapter extends BaseAdapter {

    //Object lock;
    LayoutInflater mInflater;
    Context mContext;
    List<Movie> mMovieList;

     public AsyncAdapter(Context context, List<Movie> movieList)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mMovieList = movieList;
        Log.i("AsyncAdapter", "Constructor");
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @Override
    public Movie getItem(int position) {
        return mMovieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /** For each view in the gridview the get view is called and then the complete view is returned
     * Using the async class ImgDownloader to keep grid fluid
     * and let the internet dependent action take its time */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Log.i("AsyncAdapter", "getView()");
        if (convertView == null)
        {
            /** This operation is expencive. So if we can we avoid inflating unnecessarily.
             * The previously loaded pages are kept in the background and we can get them
             * with the getTag() command.
             */
            convertView = mInflater.inflate(R.layout.fragment_top, parent,false);
            holder = new ViewHolder();
            holder.titleAndYear = (TextView) convertView.findViewById(R.id.title_text);
            holder.poster = (ImageView) convertView.findViewById(R.id.poster_img);
            holder.progress = (ProgressBar) convertView.findViewById(R.id.progress_progbar);
            convertView.setTag(holder);
        }
        else
        {
            /** If view has already been inflated previously. then we get that one*/
            holder = (ViewHolder) convertView.getTag();
        }

        /** Fill up the views here. */
        holder.titleAndYear.setText(mMovieList.get(position).getTitle() +"\n"+ mMovieList.get(position).getYear());
        holder.titleAndYear.setVisibility(View.VISIBLE);
        holder.position = position;
        new ImgDownloader(position,holder,mMovieList.get(position).getPicture()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);


        return convertView;
    }

    /** This is an inner class that is tied directly to the function of the outer class.
     * It downloads a picture from the internet and shows a progress bar while doing it */
    private class ImgDownloader extends AsyncTask<String ,Integer, Bitmap>
    {
        private int mPosition;
        private ViewHolder mHolder;
        private String mPosterUrl;

        public ImgDownloader(int position, ViewHolder holder, String posterUrl)
        {
            mPosition = position;
            mHolder = holder;
            mPosterUrl = posterUrl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mHolder.progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            InputStream inputStream;
            //Log.i("AsyncAdapter", "doInBackground");
            publishProgress(1);
            try {
                inputStream = (InputStream) new URL(mPosterUrl).getContent();
                publishProgress(2);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                publishProgress(3);
                return bitmap;
            } catch (Exception e) {
                e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            if (mHolder.position == mPosition)
            {
                mHolder.poster.setImageBitmap(bitmap);
            }
            /*if (bitmap == null)
            {
                Log.i("AsyncAdapter", "onPostExcecute Bitmap is null");
            }*/
            mHolder.progress.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mHolder.progress.setProgress(values[0]);
        }
    }

    private static class ViewHolder
    {
        public TextView titleAndYear;
        public ImageView poster;
        public ProgressBar progress;
        public int position;
    }
}
