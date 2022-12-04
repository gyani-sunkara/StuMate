package com.stumate.main.tabLayout.messaging.search.tabLayout;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stumate.main.R;
import com.stumate.main.tabLayout.TabLayoutActivity;
import com.stumate.main.tabLayout.personal.PersonalFragment;
import com.stumate.main.utils.dataTypes.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyMatesNClubsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SearchRecyclerAdapter searchAdapter;
    private List<User> mUsers;
    private EditText mEditText;

    public MyMatesNClubsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_my_mates_n_clubs, container, false);
        mEditText = Objects.requireNonNull(getActivity()).findViewById(R.id.editText);

        mRecyclerView = mView.findViewById(R.id.matesSearchRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // TODO: high priority --> needs to be updated
        mUsers = new ArrayList<>();
        List<String> clubs = PersonalFragment.getClubs();
        List<String> mates = PersonalFragment.getMates();

        if (mates != null) {
            for (String mate :
                    mates) {
                User u = TabLayoutActivity.getUser(mate);
                if (u != null) {
                    mUsers.add(u);
                }
            }
        }

        if (clubs != null) {
            for (String club :
                    clubs) {
                User c = TabLayoutActivity.getClub(club);
                if (c != null) {
                    mUsers.add(c);
                }
            }
        }

        searchAdapter = new SearchRecyclerAdapter(getContext(), mUsers, true);
        mRecyclerView.setAdapter(searchAdapter);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return mView;
    }

    private void searchUsers(String s) {
        List<User> sUsers = new ArrayList<>();
        for (User u :
                mUsers) {
            String ss = "";
            if (u.getDisplayName().startsWith("#")) {
                ss = u.getDisplayName().split(" ")[1];
            }
            if(u.getDisplayName().toLowerCase().startsWith(s) || ss.toLowerCase().startsWith(s)) {
                sUsers.add(u);
            }
        }
        searchAdapter = new SearchRecyclerAdapter(getContext(), sUsers, true);
        mRecyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        String s = mEditText.getText().toString().toLowerCase();
        if(!s.equals("")){
            searchUsers(s);
        }
        else {
            if (mUsers != null) {
                searchAdapter = new SearchRecyclerAdapter(getContext(), mUsers, true);
                mRecyclerView.setAdapter(searchAdapter);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        String s = mEditText.getText().toString().toLowerCase();
        if(!s.equals("")){
            searchUsers(s);
        }
        else {
            if (mUsers != null) {
                searchAdapter = new SearchRecyclerAdapter(getContext(), mUsers, true);
                mRecyclerView.setAdapter(searchAdapter);
            }
        }
    }
}
