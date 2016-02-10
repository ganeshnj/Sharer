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
import com.ganesh.sharer.activities.AddGroupActivity;
import com.ganesh.sharer.activities.EditGroupActivity;
import com.ganesh.sharer.adapters.FriendsCheckArrayAdapter;
import com.ganesh.sharer.adapters.GroupsArrayAdapter;
import com.ganesh.sharer.models.Group;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupSelectionDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupSelectionDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupSelectionDialogFragment extends DialogFragment {

    private static final String ARG_GROUPS = "groups";
    private static final String ARG_AREADY_SELECTED = "already_selected";

    private OnFragmentInteractionListener mListener;

    private ArrayList<Group> mGroups;
    private Group mSelectedGroup;
    private ListView mListViewGroups;
    private GroupsArrayAdapter mAdapter;


    private String mFromActivity;

    public GroupSelectionDialogFragment() {
        // Required empty public constructor
    }

    public static GroupSelectionDialogFragment newInstance(ArrayList<Group> groups) {
        GroupSelectionDialogFragment fragment = new GroupSelectionDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_GROUPS, groups);
        fragment.setArguments(args);
        return fragment;
    }

    public static GroupSelectionDialogFragment newInstance(ArrayList<Group> groups, Group group) {
        GroupSelectionDialogFragment fragment = new GroupSelectionDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_GROUPS, groups);
        args.putParcelable(ARG_AREADY_SELECTED, group);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGroups = getArguments().getParcelableArrayList(ARG_GROUPS);
            mSelectedGroup = getArguments().getParcelable(ARG_AREADY_SELECTED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_selection_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonCancel = (Button) view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupSelectionDialogFragment.this.dismiss();
            }
        });

        mListViewGroups = (ListView) view.findViewById(R.id.listView_groups);
        mAdapter = new GroupsArrayAdapter(getActivity(), R.layout.row_layout_groups, mGroups);

        mListViewGroups.setAdapter(mAdapter);

        mListViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mFromActivity.equals(AddEventActivity.class.getSimpleName())) {
                    AddEventActivity activity = (AddEventActivity) getActivity();
                    activity.onFinishingGroupSelection(mGroups.get(position));
                }
                GroupSelectionDialogFragment.this.dismiss();
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFinishingGroupSelection(Group selectedGroup);
    }

    public String getFromActivity() {
        return mFromActivity;
    }

    public void setFromActivity(String fromActivity) {
        this.mFromActivity = fromActivity;
    }

}
