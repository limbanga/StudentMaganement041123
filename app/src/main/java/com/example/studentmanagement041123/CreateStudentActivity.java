package com.example.studentmanagement041123;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagement041123.helper.StringHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateStudentActivity extends AppCompatActivity {

    private final static int SELECT_IMAGE_REQUEST_CODE = 2412;
    private Uri imageUrl;
    private String fileName;
    StorageReference storageReference;
    EditText name;
    EditText age;

    CircleImageView circleImageView;
    Button add;
    Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        add = findViewById(R.id.add);
        back = findViewById(R.id.back);

        circleImageView = findViewById(R.id.create_student_circle_image_view);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createStudent();
                if (!fileName.isEmpty()) {
                    uploadFile(fileName);
                    fileName = "";
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateStudentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void uploadFile(String newFileName) {
        storageReference = FirebaseStorage.getInstance().getReference("images/"+newFileName);

        storageReference.putFile(imageUrl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(CreateStudentActivity.this,
                                "Upload image success.", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateStudentActivity.this,
                                "Upload image failed.", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,CreateStudentActivity.SELECT_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CreateStudentActivity.SELECT_IMAGE_REQUEST_CODE &&
                data != null &&
                data.getData() != null
        ) {
            imageUrl = data.getData();
            circleImageView.setImageURI(imageUrl);
        }
    }

    private void createStudent() {
        Map<String, Object> map = new HashMap<String, Object>();
        fileName = StringHelper.generateGuidFileNameWithExtension(imageUrl, this);

        map.put("name", name.getText().toString());
        map.put("age", Integer.parseInt(age.getText().toString()));
        map.put("image", fileName);

        FirebaseDatabase.getInstance().getReference()
                .child("students")
                .push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateStudentActivity.this,
                                "Add new student successfully.",
                                Toast.LENGTH_LONG).show();
                        clearForm();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateStudentActivity.this,
                                "Something went wrong.",
                                Toast.LENGTH_LONG).show();
                        fileName = "";
                    }
                });
    }

    private void clearForm() {
        name.setText("");
        age.setText("");
    }
}