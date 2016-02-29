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
import com.ganesh.sharer.adapters.FriendsArrayAdapter;
import com.ganesh.sharer.models.User;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FriendsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FRIENDS = "friends";

    // TODO: Rename and change types of parameters
    private ArrayList<User> mUsers;

    private ListView mListViewFriends;
    private FriendsArrayAdapter mAdapter;
    private AdapterView.AdapterContextMenuInfo mContextMenuInfo;

    public FriendsFragment() {
        // Required empty public constructor
    }

    public static FriendsFragment newInstance(ArrayList<User> users) {
        FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_FRIENDS, users);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsers = getArguments().getParcelableArrayList(ARG_FRIENDS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListViewFriends = (ListView) view.findViewById(R.id.listView_friends);
        registerForContextMenu(mListViewFriends);

        mAdapter = new FriendsArrayAdapter(getActivity(), R.layout.row_layout_friends, mUsers);
        mListViewFriends.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        getActivity().setTitle(getString(R.string.Friends));
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
        inflater.inflate(R.menu.friends_context, menu);
        mContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        User user = mUsers.get(mContextMenuInfo.position);

        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(getActivity(), EditFriendActivity.class);
                intent.putExtra(EditFriendActivity.ARG_USER_ID, user.getUserId());
                getActivity().startActivity(intent);
                break;
            case R.id.delete:
                if (Repository.deleteUser(user)) {
                    mUsers.remove(user);
                    mAdapter.notifyDataSetChanged();
                }

                break;
        }
        return true;
    }

}
