package petergranlund.assignment_3;

//import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.graphics.drawable.Drawable;
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
            holder.title = (TextView) convertView.findViewById(R.id.title_text);
            holder.year = (TextView) convertView.findViewById(R.id.year_text);
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
        holder.title.setText(mMovieList.get(position).getTitle());
        holder.year.setText(String.valueOf(mMovieList.get(position).getYear()));
        holder.position = position;
        new ImgDownloader(position,holder,mMovieList.get(position).getPicture()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);


        return convertView;
    }

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
        public TextView title;
        public TextView year;
        public ImageView poster;
        public ProgressBar progress;
        public int position;
    }
}
