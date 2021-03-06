package petergranlund.da401a_peter_granlund;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstScreen.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstScreen extends Fragment implements View.OnClickListener {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstScreen newInstance(String param1, String param2) {
        FirstScreen fragment = new FirstScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FirstScreen() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Log.i("FirstScreen", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("FirstScreen", "onCreateView");
        View v = inflater.inflate(R.layout.fragment_first_screen, container,false);
        View buttonView = v.findViewById(R.id.button);
        buttonView.setOnClickListener(this);

        return v;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
            Log.i("FirstScreen", "onButtonPressed : isNotNull");
        }
        Log.i("FirstScreen", "onButtonPressed : isNull");
    }


    @Override
    public void onClick(View v) {
        Log.i("FirstScreen", "onClick");
        mListener.onFragmentInteraction(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("FirstScreen", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("FirstScreen", "onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("FirstScreen", "onDestroyView");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("FirstScreen", "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("FirstScreen", "onStop");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
