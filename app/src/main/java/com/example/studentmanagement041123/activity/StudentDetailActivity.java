package com.example.studentmanagement041123.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.studentmanagement041123.R;
import com.example.studentmanagement041123.adapter.CertificateAdapter;
import com.example.studentmanagement041123.dialog.CreateCertificateDialog;
import com.example.studentmanagement041123.dialog.ConfirmDialog;
import com.example.studentmanagement041123.model.Certificate;
import com.example.studentmanagement041123.model.Student;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentDetailActivity extends AppCompatActivity {

    public static final String STUDENT_ID_KEY_NAME = "STUDENT_ID_KEY_NAME";
    String studentId;
    CertificateAdapter adapter;

    private Student student;
    TextView nameTextView;
    TextView ageTextView;
    ImageView imageView;
    ImageView openUpdateImageView;
    ImageView deleteImageView;

    ImageView openAddCertificate;
    RecyclerView recyclerView;

    @Override
    protected void onStart() {
        super.onStart();

    }

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
        imageView = findViewById(R.id.student_detail_image_view);
        openUpdateImageView = findViewById(R.id.open_update_image_view);
        deleteImageView = findViewById(R.id.delete_image_view);
        recyclerView = findViewById(R.id.certificate_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(StudentDetailActivity.this));
        openAddCertificate = findViewById(R.id.add_certificate_image_view);

        studentId = intent.getStringExtra(StudentDetailActivity.STUDENT_ID_KEY_NAME);

        loadStudentData();
        connectAndListen();

        openUpdateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentDetailActivity.this,
                        UpdateStudentActivity.class);
                intent.putExtra(UpdateStudentActivity.STUDENT_ID_KEY_NAME, studentId);
                startActivity(intent);
            }
        });

        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmDialog.Listener listener = new ConfirmDialog.Listener() {
                    @Override
                    public void onConfirmClick() {
                        deleteStudent();

                    }
                };

                Dialog dialog = new ConfirmDialog(StudentDetailActivity.this, listener);
                dialog.show();
            }
        });

        openAddCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateCertificateDialog dialog = new CreateCertificateDialog(StudentDetailActivity.this, studentId);
                dialog.show();
            }
        });
    }

    private void deleteStudent() {
        FirebaseDatabase.getInstance()
                .getReference()
                .child("students")
                .child(studentId).removeValue();

        Toast.makeText(StudentDetailActivity.this ,
                " Student was deleted successfully.",
                Toast.LENGTH_LONG).show();
        finish();
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
                        String image = String.valueOf(dataSnapshot.child("image").getValue());

                        int ageInteger = Integer.parseInt(age);
                        student = new Student(name, ageInteger, image);

                        nameTextView.setText(name);
                        ageTextView.setText(age);
                        Glide.with(StudentDetailActivity.this)
                                .load(student.getImageUrl())
                                .into(imageView);
                    } else {
                        Toast.makeText(StudentDetailActivity.this ,
                                " Student doesn't exist.",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    Toast.makeText(StudentDetailActivity.this ,
                            "Failed to read.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void connectAndListen() {
        FirebaseRecyclerOptions<Certificate> options =
                new FirebaseRecyclerOptions.Builder<Certificate>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("students")
                                .child(studentId)
                                .child("certificates"), Certificate.class)
                        .build();

        adapter = new CertificateAdapter(options, studentId);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
