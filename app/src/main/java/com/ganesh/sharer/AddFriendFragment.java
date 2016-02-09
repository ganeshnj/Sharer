package com.ganesh.sharer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ganesh.sharer.models.User;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFriendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFriendFragment extends Fragment {

    private EditText mEditTextFirstname;
    private EditText mEditTextLastname;
    private EditText mEditTextEmail;

    public AddFriendFragment() {
        // Required empty public constructor
    }

    public static AddFriendFragment newInstance() {
        AddFriendFragment fragment = new AddFriendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        getActivity().setTitle("Add a friend");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_friend, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mEditTextFirstname = (EditText) view.findViewById(R.id.editTextFirstname);
        this.mEditTextLastname = (EditText) view.findViewById(R.id.editTextLastname);
        this.mEditTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.add_friend, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_friend:
                if (saveFriend(mEditTextFirstname.getText().toString(), mEditTextLastname.getText().toString(), mEditTextEmail.getText().toString())){
                    DatabaseContext context = new DatabaseContext();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, FriendsFragment.newInstance(context.getUsers())).commitAllowingStateLoss();
                }
                break;
        }
        return true;

    }

    private boolean saveFriend(String firstname, String lastname, String email) {
        boolean isError = false;
        if (firstname == null || firstname.isEmpty()){
            mEditTextFirstname.setError("Firstname is required");
            isError = true;
        }

        if (lastname == null || lastname.isEmpty()){
            mEditTextLastname.setError("Lastname is required");
            isError = true;
        }

        if (email == null || email.isEmpty()){
            mEditTextEmail.setError("Email is required");
            isError = true;
        }

        if (!isError){
            User user = new User(firstname, lastname, email);
            DatabaseContext context = new DatabaseContext();
            context.addUser(user);
        }

        return !isError;
    }
}
