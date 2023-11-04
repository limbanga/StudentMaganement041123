package com.example.studentmanagement041123;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.studentmanagement041123.adapter.StudentAdapter;
import com.example.studentmanagement041123.model.Student;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Student> options =
                new FirebaseRecyclerOptions.Builder<Student>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students"), Student.class)
                        .build();

        studentAdapter = new StudentAdapter(options);

        recyclerView.setAdapter(studentAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        studentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        studentAdapter.stopListening();
    }
}