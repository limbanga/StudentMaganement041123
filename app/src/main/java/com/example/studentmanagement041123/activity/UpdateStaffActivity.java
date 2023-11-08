package com.example.studentmanagement041123.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.studentmanagement041123.R;
import com.example.studentmanagement041123.helper.StringHelper;
import com.example.studentmanagement041123.model.Staff;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateStaffActivity extends AppCompatActivity {

    public static String STAFF_ID_KEY_NAME = "STAFF_ID_KEY_NAME";
    public static int SELECT_IMAGE_REQUEST_CODE = 7034212;


    public Staff staff;
    private Uri imageUrl;
    private String fileName;
    private String staffId;
    private StorageReference storageReference;

    EditText nameEditText;
    EditText ageEditText;
    CircleImageView imageView;
    Button updateButton;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        imageView = findViewById(R.id.update_student_circle_image_view);
        nameEditText = findViewById(R.id.update_student_name_edit_text);
        ageEditText = findViewById(R.id.update_student_age_edit_text);
        updateButton = findViewById(R.id.update_student_update_button);

        Intent intent = getIntent();

        if(!intent.hasExtra(UpdateStaffActivity.STAFF_ID_KEY_NAME)) {
            return;
        }

        staffId = intent.getStringExtra(UpdateStaffActivity.STAFF_ID_KEY_NAME);
        loadStaffData();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStaff();
                if (!fileName.isEmpty()) {
                    uploadFile(fileName);
                    fileName = "";
                }
            }
        });

    }

    private void updateStaff() {

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("name", nameEditText.getText().toString());
        map.put("age", Integer.valueOf(ageEditText.getText().toString()));
        map.put("image", staff.getImage());

        fileName = "";
        if (imageUrl != null) {
            fileName = StringHelper.generateGuidFileNameWithExtension(imageUrl, this);
            map.put("image", fileName);
        }

        FirebaseDatabase.getInstance().getReference().child("appUsers")
            .child(staffId).updateChildren(map)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(UpdateStaffActivity.this,
                            "Update staff successfully.",
                            Toast.LENGTH_LONG).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdateStaffActivity.this,
                            "Something went wrong.",
                            Toast.LENGTH_LONG).show();
                }
            });
    }

    private void loadStaffData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appUsers");

        databaseReference.child(staffId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String age = String.valueOf(dataSnapshot.child("age").getValue());
                        String image = String.valueOf(dataSnapshot.child("image").getValue());

                        int ageInteger = Integer.parseInt(age);
                        staff = new Staff(name, ageInteger, image);

                        nameEditText.setText(name);
                        ageEditText.setText(age);
                        Glide.with(UpdateStaffActivity.this)
                                .load(staff.getImageUrl())
                                .into(imageView);
                    } else {
                        Toast.makeText(UpdateStaffActivity.this,
                                " Staff doesn't exist.",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    Toast.makeText(UpdateStaffActivity.this,
                            "Failed to read.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void uploadFile(String newFileName) {
        storageReference = FirebaseStorage.getInstance().getReference("images/"+newFileName);

        storageReference.putFile(imageUrl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(UpdateStaffActivity.this,
                                "Upload image success.", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateStaffActivity.this,
                                "Upload image failed.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, UpdateStaffActivity.SELECT_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UpdateStaffActivity.SELECT_IMAGE_REQUEST_CODE &&
                data != null &&
                data.getData() != null
        ) {
            imageUrl = data.getData();
            imageView.setImageURI(imageUrl);
        }
    }
}