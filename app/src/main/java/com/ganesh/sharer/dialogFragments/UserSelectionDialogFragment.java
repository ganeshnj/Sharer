package com.ganesh.sharer.dialogFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ganesh.sharer.R;
import com.ganesh.sharer.activities.AddEventActivity;
import com.ganesh.sharer.activities.AddSettlementActivity;
import com.ganesh.sharer.adapters.FriendsArrayAdapter;
import com.ganesh.sharer.adapters.FriendsCheckArrayAdapter;
import com.ganesh.sharer.adapters.GroupsArrayAdapter;
import com.ganesh.sharer.models.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserSelectionDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserSelectionDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSelectionDialogFragment extends DialogFragment {
    private static final String ARG_FRIENDS = "friends";

    private ArrayList<User> mFriends;
    private ListView mListViewFriends;
    private FriendsArrayAdapter mAdapter;
    private String mFromActivity;

    public UserSelectionDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserSelectionDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserSelectionDialogFragment newInstance(ArrayList<User> friends) {
        UserSelectionDialogFragment fragment = new UserSelectionDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_FRIENDS, friends);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFriends = getArguments().getParcelableArrayList(ARG_FRIENDS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_selection_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonCancel = (Button) view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSelectionDialogFragment.this.dismiss();
            }
        });

        mListViewFriends = (ListView) view.findViewById(R.id.listView_friends);
        mAdapter = new FriendsArrayAdapter(getActivity(), R.layout.row_layout_friends, mFriends);

        mListViewFriends.setAdapter(mAdapter);

        mListViewFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mFromActivity.equals(AddSettlementActivity.class.getSimpleName())) {
                    AddSettlementActivity activity = (AddSettlementActivity) getActivity();
                    activity.onFinishingUserSelection(mFriends.get(position));
                }
                UserSelectionDialogFragment.this.dismiss();
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    public String getFromActivity() {
        return mFromActivity;
    }

    public void setFromActivity(String mFromActivity) {
        this.mFromActivity = mFromActivity;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFinishingUserSelection(User selectedUser);
    }
}
