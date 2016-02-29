package com.ganesh.sharer.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ganesh.sharer.R;
import com.ganesh.sharer.Repository;
import com.ganesh.sharer.activities.DetailEventActivity;
import com.ganesh.sharer.activities.EditFriendActivity;
import com.ganesh.sharer.adapters.EventsArrayAdapter;
import com.ganesh.sharer.models.Event;

import java.util.ArrayList;

public class EventsFragment extends Fragment {

    private static final String ARG_EVENTS = "events";
    private static final String ARG_TITLE = "title";

    private ArrayList<Event> mEvents;
    private String mTitle;

    private ListView mListViewEvents;
    private EventsArrayAdapter mAdapter;
    private TextView mTextViewPositives;
    private TextView mTextViewNegatives;

    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance(ArrayList<Event> events) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_EVENTS, events);
        fragment.setArguments(args);
        return fragment;
    }

    public static EventsFragment newInstance(ArrayList<Event> events, String title) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_EVENTS, events);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEvents = getArguments().getParcelableArrayList(ARG_EVENTS);
            mTitle = getArguments().getString(ARG_TITLE, "Events");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListViewEvents = (ListView) view.findViewById(R.id.listView_events);
        mListViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailEventActivity.class);
                intent.putExtra(DetailEventActivity.ARG_EVENT_ID, mEvents.get(position).getEventID());
                getActivity().startActivity(intent);
            }
        });
        mAdapter = new EventsArrayAdapter(getActivity(), R.layout.row_layout_friends, mEvents);
        mListViewEvents.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        getActivity().setTitle(mTitle);

        double p = Repository.getAllPositive(mEvents);
        double n = Repository.getAllNegative(mEvents);
        String s = Repository.getCurrency().getSymbol();

        mTextViewNegatives = (TextView) view.findViewById(R.id.textViewNegatives);
        mTextViewNegatives.setText('-' + s + Math.abs(n));

        mTextViewPositives = (TextView) view.findViewById(R.id.textViewPositives);
        mTextViewPositives.setText('+' + s + p);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
