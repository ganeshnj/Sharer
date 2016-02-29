package com.ganesh.sharer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ganesh.sharer.R;
import com.ganesh.sharer.adapters.SettlementArrayAdapter;
import com.ganesh.sharer.models.Event;
import com.ganesh.sharer.models.Settlement;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettlementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettlementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettlementFragment extends Fragment {
    private static final String ARG_SETTLEMENTS = "settlements";

    private ArrayList<Settlement> mSettlements;

    private ListView mListViewSettlements;
    private SettlementArrayAdapter mAdapter;

    public SettlementFragment() {
        // Required empty public constructor
    }
    public static SettlementFragment newInstance(ArrayList<Settlement> settlements) {
        SettlementFragment fragment = new SettlementFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_SETTLEMENTS, settlements);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSettlements = getArguments().getParcelableArrayList(ARG_SETTLEMENTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settlement, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListViewSettlements = (ListView) view.findViewById(R.id.listViewSettlements);
        mAdapter = new SettlementArrayAdapter(getActivity(), R.layout.row_layout_settlements, mSettlements);
        mListViewSettlements.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        getActivity().setTitle(getString(R.string.Settlements));
    }

}
