package com.example.studentmanagement041123;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentmanagement041123.adapter.StaffAdapter;
import com.example.studentmanagement041123.model.Staff;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class StaffFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton openAddStaffButton;

    StaffAdapter staffAdapter;

    @Override
    public void onStart() {
        super.onStart();
        staffAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        staffAdapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_staff, container, false);

        recyclerView = view.findViewById(R.id.staff_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        openAddStaffButton = view.findViewById(R.id.open_add_staff_button);
        openAddStaffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        FirebaseRecyclerOptions<Staff> options =
                new FirebaseRecyclerOptions.Builder<Staff>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("appUsers"), Staff.class)
                        .build();

        staffAdapter = new StaffAdapter(options);
        recyclerView.setAdapter(staffAdapter);

        return view;
    }
}