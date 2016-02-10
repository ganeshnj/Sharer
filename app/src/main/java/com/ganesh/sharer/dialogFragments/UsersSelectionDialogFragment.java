package com.ganesh.sharer.dialogFragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.ganesh.sharer.R;
import com.ganesh.sharer.activities.AddEventActivity;
import com.ganesh.sharer.activities.AddGroupActivity;
import com.ganesh.sharer.activities.EditGroupActivity;
import com.ganesh.sharer.adapters.FriendsCheckArrayAdapter;
import com.ganesh.sharer.models.User;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UsersSelectionDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UsersSelectionDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersSelectionDialogFragment extends DialogFragment {
    private static final String ARG_FRIENDS = "friends";
    private static final String ARG_AREADY_SELECTED = "already_selected";

    private ArrayList<User> mFriends;
    private ArrayList<User> mAlreadySelected;
    private ListView mListViewFriends;
    private FriendsCheckArrayAdapter mAdapter;
    private Button mButtonOk;
    private Button mButtonCancel;

    private String mFromActivity;

    public UsersSelectionDialogFragment() {
        // Required empty public constructor
    }

    public static UsersSelectionDialogFragment newInstance(ArrayList<User> friends) {
        UsersSelectionDialogFragment fragment = new UsersSelectionDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_FRIENDS, friends);
        fragment.setArguments(args);
        return fragment;
    }

    public static UsersSelectionDialogFragment newInstance(ArrayList<User> friends, ArrayList<User> alreadySelected) {
        UsersSelectionDialogFragment fragment = new UsersSelectionDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_FRIENDS, friends);
        args.putParcelableArrayList(ARG_AREADY_SELECTED, alreadySelected);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mFriends = getArguments().getParcelableArrayList(ARG_FRIENDS);
            mAlreadySelected = getArguments().getParcelableArrayList(ARG_AREADY_SELECTED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users_selection_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButtonOk = (Button) view.findViewById(R.id.buttonOk);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getFromActivity().equals(AddGroupActivity.class.getSimpleName())){
                    AddGroupActivity activity = (AddGroupActivity) getActivity();
                    activity.onFinishingUsersSelection(mAdapter.getSelectedUsers());
                }
                else if (getFromActivity().equals(EditGroupActivity.class.getSimpleName())) {
                    EditGroupActivity activity = (EditGroupActivity) getActivity();
                    activity.onFinishingUsersSelection(mAdapter.getSelectedUsers());
                }
                else if (getFromActivity().equals(AddEventActivity.class.getSimpleName())) {
                    AddEventActivity activity = (AddEventActivity) getActivity();
                    activity.onFinishingUsersSelection(mAdapter.getSelectedUsers());
                }
                UsersSelectionDialogFragment.this.dismiss();
            }
        });

        mButtonCancel = (Button) view.findViewById(R.id.buttonCancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersSelectionDialogFragment.this.dismiss();
            }
        });

        mListViewFriends = (ListView) view.findViewById(R.id.listView_friends);
        mAdapter = new FriendsCheckArrayAdapter(getActivity(), R.layout.row_layout_friends, mFriends);

        if (mAlreadySelected != null){
            mAdapter.setBoolSelectedUsers(mAlreadySelected);
        }

        mListViewFriends.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public String getFromActivity() {
        return mFromActivity;
    }

    public void setFromActivity(String fromActivity) {
        this.mFromActivity = fromActivity;
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
        void onFinishingUsersSelection(ArrayList<User> selectedUsers);
    }
}
