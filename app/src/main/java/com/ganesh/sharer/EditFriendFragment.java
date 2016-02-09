package com.ganesh.sharer;

import android.os.Bundle;
import android.provider.ContactsContract;
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
 * {@link EditFriendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditFriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFriendFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_USER_ID= "user_id";

    // TODO: Rename and change types of parameters
    private int mUserId;
    private EditText mEditTextFirstname;
    private EditText mEditTextLastname;
    private EditText mEditTextEmail;
    private DatabaseContext mDbContext;

    public EditFriendFragment() {
        // Required empty public constructor
    }

    public static EditFriendFragment newInstance(int userId) {
        EditFriendFragment fragment = new EditFriendFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(ARG_USER_ID);
        }
        setHasOptionsMenu(true);
        mDbContext = new DatabaseContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_friend, container, false);
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
                if (saveFriend(mUserId, mEditTextFirstname.getText().toString(), mEditTextLastname.getText().toString(), mEditTextEmail.getText().toString())){
                    DatabaseContext context = new DatabaseContext();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, FriendsFragment.newInstance(mDbContext.getUsers())).commitAllowingStateLoss();
                }
                break;
        }
        return true;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mEditTextFirstname = (EditText) view.findViewById(R.id.editTextFirstname);
        this.mEditTextLastname = (EditText) view.findViewById(R.id.editTextLastname);
        this.mEditTextEmail = (EditText) view.findViewById(R.id.editTextEmail);

        User user = mDbContext.getUser(mUserId);

        if (user != null){
            this.mEditTextFirstname.setText(user.getFirstname());
            this.mEditTextLastname.setText(user.getLastname());
            this.mEditTextEmail.setText(user.getEmail());
        }

    }

    private boolean saveFriend(int userId, String firstname, String lastname, String email) {
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
            User user = new User(userId, firstname, lastname, email);
            DatabaseContext context = new DatabaseContext();
            context.editUser(user);
        }

        return !isError;
    }
}
