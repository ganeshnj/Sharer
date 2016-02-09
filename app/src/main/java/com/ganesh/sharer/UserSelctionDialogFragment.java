package com.ganesh.sharer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.ganesh.sharer.adapters.FriendsArrayAdapter;
import com.ganesh.sharer.adapters.FriendsCheckArrayAdapter;
import com.ganesh.sharer.models.User;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserSelctionDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserSelctionDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSelctionDialogFragment extends DialogFragment {
    private static final String ARG_FRIENDS = "friends";
    private static final String ARG_AREADY_SELECTED = "already_selected";

    private ArrayList<User> mFriends;
    private ListView mListViewFriends;
    private FriendsCheckArrayAdapter mAdapter;
    private Button mButtonOk;

    public UserSelctionDialogFragment() {
        // Required empty public constructor
    }

    public static UserSelctionDialogFragment newInstance(ArrayList<User> friends) {
        UserSelctionDialogFragment fragment = new UserSelctionDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_FRIENDS, friends);
        fragment.setArguments(args);
        return fragment;
    }

    public static UserSelctionDialogFragment newInstance(ArrayList<User> friends, ArrayList<User> alreadySelected) {
        UserSelctionDialogFragment fragment = new UserSelctionDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_FRIENDS, friends);
        args.putParcelableArrayList(ARG_AREADY_SELECTED, alreadySelected);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new FriendsCheckArrayAdapter(getActivity(), R.layout.row_layout_friends, mFriends);

        if (getArguments() != null) {
            mFriends = getArguments().getParcelableArrayList(ARG_FRIENDS);
            ArrayList<User> alreadySelected = getArguments().getParcelableArrayList(ARG_AREADY_SELECTED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_selction_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButtonOk = (Button) view.findViewById(R.id.buttonOk);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddGroupActivity activity = (AddGroupActivity) getActivity();
                activity.onFinishingSelection(mAdapter.getSelectedUsers());
                UserSelctionDialogFragment.this.dismiss();
            }
        });

        mListViewFriends = (ListView) view.findViewById(R.id.listView_friends);

        mListViewFriends.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
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
        void onFinishingSelection(ArrayList<User> selectedUsers);
    }
}
