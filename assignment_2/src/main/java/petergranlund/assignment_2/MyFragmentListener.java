package petergranlund.assignment_2;

import android.app.Activity;
import android.app.ListFragment;

/**
 * Created by Peter on 2015-09-13.
 */
public class MyFragmentListener extends ListFragment
{
    /**
     *      This is the object that is checed for interaction.
     */
    OnMyFragmentListener mCallback;

    /**
     *This is the interface method that is to be implemented
     */
    public interface OnMyFragmentListener{
        void onArticleSelected(MovieRef movie/*int position*/);
    }

    /**     This method is supposed to force the child to implement this method. I think?
            Otherwise the mCallback object is never initialized and will be null!!
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallback = (OnMyFragmentListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + "Must implement OnMyFragmentInteractionListener") ;
        }
    }
}
