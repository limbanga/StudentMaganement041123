package com.example.studentmanagement041123.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmanagement041123.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentDetailActivity extends AppCompatActivity {

    public static final String STUDENT_ID_KEY_NAME = "STUDENT_ID_KEY_NAME";
    String studentId;

    TextView nameTextView;
    TextView ageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        Intent intent = getIntent();

        if(!intent.hasExtra(StudentDetailActivity.STUDENT_ID_KEY_NAME)) {
            return;
        }
        nameTextView = findViewById(R.id.student_detail_name_text_view);
        ageTextView = findViewById(R.id.student_detail_age_text_view);

        studentId = intent.getStringExtra(StudentDetailActivity.STUDENT_ID_KEY_NAME);

        loadStudentData();
    }

    private void loadStudentData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("students");

        databaseReference.child(studentId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()){
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String age = String.valueOf(dataSnapshot.child("age").getValue());

                        nameTextView.setText(name);
                        ageTextView.setText(age);

                        Toast.makeText(StudentDetailActivity.this ,
                                "Success to read." + name,
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(StudentDetailActivity.this ,
                                " Student doesn't exist.",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(StudentDetailActivity.this ,
                            "Failed to read.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}