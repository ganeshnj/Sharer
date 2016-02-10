package com.ganesh.sharer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ganesh.sharer.R;
import com.ganesh.sharer.adapters.EventsArrayAdapter;
import com.ganesh.sharer.models.Event;

import java.util.ArrayList;

public class EventsFragment extends Fragment {

    private static final String ARG_EVENTS = "events";

    private ArrayList<Event> mEvents;

    private ListView mListViewEvents;
    private EventsArrayAdapter mAdapter;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEvents = getArguments().getParcelableArrayList(ARG_EVENTS);
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
        mAdapter = new EventsArrayAdapter(getActivity(), R.layout.row_layout_friends, mEvents);
        mListViewEvents.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        getActivity().setTitle("Events");
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
