package petergranlund.assignment_4_2;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;


public class MarkerDialogFragment extends DialogFragment {

    private MapLocationObject mMapLocationObject;

    public MarkerDialogFragment() {
        Log.i("MarkerDialogFragment", "Constructor");
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i("MarkerDialogFragment", "onCreateDialog()");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        mMapLocationObject = getArguments().getParcelable("location");
        dialogBuilder.setTitle(mMapLocationObject.getmMarkerHeader());
        dialogBuilder.setMessage(mMapLocationObject.getLocationQuestion());
        dialogBuilder.setPositiveButton(mMapLocationObject.getAnswer1(), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int witch)
            {
                clickHandler(Dialog.BUTTON_POSITIVE);
            }});
        dialogBuilder.setNeutralButton(mMapLocationObject.getAnswer2(), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int witch)
            {
                clickHandler(Dialog.BUTTON_NEUTRAL);
            }});
        dialogBuilder.setNegativeButton(mMapLocationObject.getAnswer3(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int witch) {
                clickHandler(Dialog.BUTTON_NEGATIVE);
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        return dialog;
    }

    void clickHandler(int witch)
    {
        Log.i("MarkerDialogFragment", "onClick");
        switch (witch)
        {
            case Dialog.BUTTON_POSITIVE:
                Log.i("MarkerDialogFragment", "BUTTON_POSITIVE");
                Toast.makeText(getContext(),"          You did it!!!\nYou are so awesome :D!!",Toast.LENGTH_SHORT).show();
                //do suceeded logic
                break;
            case Dialog.BUTTON_NEUTRAL:
                Log.i("MarkerDialogFragment", "BUTTON_NEUTRAL");
                Toast.makeText(getContext(),"You failed :'(",Toast.LENGTH_SHORT).show();
                //do failed logic
                break;
            case Dialog.BUTTON_NEGATIVE:
                Log.i("MarkerDialogFragment", "BUTTON_NEGATIVE");
                Toast.makeText(getContext(),"You failed :'(",Toast.LENGTH_SHORT).show();
                //do failed logic
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("MarkerDialogFragment", "onAttach()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("MarkerDialogFragment", "onDetach()");
    }
}
