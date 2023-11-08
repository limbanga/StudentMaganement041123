package com.example.studentmanagement041123.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.studentmanagement041123.R;
import com.example.studentmanagement041123.dialog.ConfirmDialog;
import com.example.studentmanagement041123.model.Staff;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StaffDetailActivity extends AppCompatActivity {

    public static final String STAFF_ID_KEY_NAME = "STAFF_ID_KEY_NAME";
    String staffId;

    private Staff staff;
    TextView nameTextView;
    TextView ageTextView;
    ImageView imageView;
    ImageView openUpdateImageView;
    ImageView deleteImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_detail);

        Intent intent = getIntent();

        if(!intent.hasExtra(StaffDetailActivity.STAFF_ID_KEY_NAME)) {
            return;
        }

        nameTextView = findViewById(R.id.student_detail_name_text_view);
        ageTextView = findViewById(R.id.student_detail_age_text_view);
        imageView = findViewById(R.id.student_detail_image_view);
        openUpdateImageView = findViewById(R.id.open_update_image_view);
        deleteImageView = findViewById(R.id.delete_image_view);

        staffId = intent.getStringExtra(StaffDetailActivity.STAFF_ID_KEY_NAME);

        loadStaffData();

        openUpdateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StaffDetailActivity.this,
                        UpdateStaffActivity.class);
                intent.putExtra(UpdateStaffActivity.STAFF_ID_KEY_NAME, staffId);
                startActivity(intent);
            }
        });

        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmDialog.Listener listener = new ConfirmDialog.Listener() {
                    @Override
                    public void onConfirmClick() {
                        deleteStaff();
                    }
                };

                Dialog dialog = new ConfirmDialog(StaffDetailActivity.this, listener);
                dialog.show();
            }
        });
    }

    private void deleteStaff() {
        FirebaseDatabase.getInstance()
                .getReference()
                .child("appUsers")
                .child(staffId).removeValue();

        Toast.makeText(StaffDetailActivity.this ,
                "Staff was deleted successfully.",
                Toast.LENGTH_LONG).show();
        finish();
    }

    private void loadStaffData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appUsers");

        databaseReference.child(staffId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()){
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String age = String.valueOf(dataSnapshot.child("age").getValue());
                        String image = String.valueOf(dataSnapshot.child("image").getValue());

                        int ageInteger = Integer.parseInt(age);
                        staff = new Staff(name, ageInteger, image);

                        nameTextView.setText(name);
                        ageTextView.setText(age);
                        Glide.with(StaffDetailActivity.this)
                                .load(staff.getImageUrl())
                                .into(imageView);
                    } else {
                        Toast.makeText(StaffDetailActivity.this ,
                                " Staff doesn't exist.",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    Toast.makeText(StaffDetailActivity.this ,
                            "Failed to read.",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

}