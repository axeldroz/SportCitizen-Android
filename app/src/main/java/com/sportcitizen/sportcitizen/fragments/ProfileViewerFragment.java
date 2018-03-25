package com.sportcitizen.sportcitizen.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.dbutils.NotificationEventListener;
import com.sportcitizen.sportcitizen.dbutils.ProfileEventListener;
import com.sportcitizen.sportcitizen.models.NotificationModel;
import com.sportcitizen.sportcitizen.viewholders.ProfileViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileViewerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileViewerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ProfileViewHolder _holder;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private FirebaseUser _user;
    private DatabaseReference _dbRef;

    private NotificationModel _notification;

    public ProfileViewerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileViewerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileViewerFragment newInstance(String param1, String param2) {
        ProfileViewerFragment fragment = new ProfileViewerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            Log.d("mPAram1", mParam1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            Log.d("mPAram2", mParam2);
        }
        initDatabaseRef();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        DatabaseReference ref = null;

        view = inflater.inflate(R.layout.fragment_profile_viewer, container, false);
        Log.d("ON CREATE VIEW", "ON CREATE VIEW");
        Log.d("FromId", mParam2);
        ref = mDatabase.getReference("users").child(mParam2);

        _holder = new ProfileViewHolder(view, ref, this.getActivity());
        setProfileInfoListener(_holder, ref);
        return (view);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Initalise firebase
     */
    private void initDatabaseRef() {
        _user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            mDatabase = FirebaseDatabase.getInstance();
        }catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
        mDatabaseRef = mDatabase.getReference();
        _dbRef = mDatabase.getReference("users").child(_user.getUid());
    }

    private void setProfileInfoListener(final ProfileViewHolder holder, DatabaseReference ref) {
        ref.addValueEventListener(new ProfileEventListener(holder));
    }
}
