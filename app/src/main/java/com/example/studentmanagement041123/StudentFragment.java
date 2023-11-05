package com.example.studentmanagement041123;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentmanagement041123.adapter.StudentAdapter;
import com.example.studentmanagement041123.model.Student;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class StudentFragment extends Fragment {

    private RecyclerView recyclerView;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        recyclerView = view.findViewById(R.id.student_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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