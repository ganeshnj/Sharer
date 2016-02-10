package com.ganesh.sharer.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ganesh.sharer.DatabaseContext;
import com.ganesh.sharer.R;
import com.ganesh.sharer.Repository;
import com.ganesh.sharer.activities.EditFriendActivity;
import com.ganesh.sharer.activities.EditGroupActivity;
import com.ganesh.sharer.adapters.GroupsArrayAdapter;
import com.ganesh.sharer.models.Group;
import com.ganesh.sharer.models.User;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_GROUPS = "groups";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Group> mGroups;

    private ListView mListViewGroups;
    private GroupsArrayAdapter mAdapter;
    private AdapterView.AdapterContextMenuInfo mContextMenuInfo;


    public GroupsFragment() {
        // Required empty public constructor
    }

    public static GroupsFragment newInstance(ArrayList<Group> groups) {
        GroupsFragment fragment = new GroupsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_GROUPS, groups);
        fragment.setArguments(args);
        return fragment;
    }

    public static GroupsFragment newInstance(String param1, String param2) {
        GroupsFragment fragment = new GroupsFragment();
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
            mGroups = getArguments().getParcelableArrayList(ARG_GROUPS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_groups, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListViewGroups = (ListView) view.findViewById(R.id.listView_groups);
        registerForContextMenu(mListViewGroups);

        mAdapter = new GroupsArrayAdapter(getActivity(), R.layout.row_layout_groups, mGroups);
        mListViewGroups.setAdapter(mAdapter);
        mListViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Group group = mGroups.get(position);

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.content_frame, EventsFragment.newInstance(group.getEvents(), group.getTitle())).commitAllowingStateLoss();
            }
        });
        mAdapter.notifyDataSetChanged();

        getActivity().setTitle("Groups");
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.groups_context, menu);
        mContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Group group = mGroups.get(mContextMenuInfo.position);

        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(getActivity(), EditGroupActivity.class);
                intent.putExtra(EditGroupActivity.ARG_GROUP_ID, group.getGroupID());
                getActivity().startActivity(intent);
                break;
            case R.id.delete:
                if (Repository.deleteGroup(group)) {
                    mGroups.remove(group);
                    mAdapter.notifyDataSetChanged();
                }

                break;
        }
        return true;
    }
}
