package com.example.studentmanagement041123;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.studentmanagement041123.adapter.StudentAdapter;
import com.example.studentmanagement041123.model.Student;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class StudentFragment extends Fragment {

    private RecyclerView recyclerView;
    ImageView addStudentImageView;

    StudentAdapter studentAdapter;

    @Override
    public void onStart() {
        super.onStart();
        studentAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        studentAdapter.stopListening();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        recyclerView = view.findViewById(R.id.student_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        addStudentImageView = view.findViewById(R.id.open_add_student_button);

        addStudentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddActivity.class);
                startActivity(intent);
            }
        });

        FirebaseRecyclerOptions<Student> options =
                new FirebaseRecyclerOptions.Builder<Student>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students"), Student.class)
                        .build();

        studentAdapter = new StudentAdapter(options);
        recyclerView.setAdapter(studentAdapter);
        return view;
    }

//    private void textSearch(String str) {
//
//        FirebaseRecyclerOptions<Student> options =
//                new FirebaseRecyclerOptions.Builder<Student>()
//                        .setQuery(
//                                FirebaseDatabase.getInstance().getReference()
//                                        .child("students")
//                                        .orderByChild("name")
//                                        .startAt(str)
//                                        .endAt(str + "~")
//                                , Student.class)
//                        .build();
//
//        studentAdapter = new StudentAdapter(options);
//        studentAdapter.startListening();
//        recyclerView.setAdapter(studentAdapter);
//    }

}