package com.example.studentmanagement041123.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagement041123.R;
import com.example.studentmanagement041123.helper.StringHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateStaffActivity extends AppCompatActivity {

    private static int SELECT_IMAGE_REQUEST_CODE = 4373;
    private Uri imageUrl;
    private String fileName;
    StorageReference storageReference;

    EditText nameEditText;
    EditText ageEditText;
    EditText emailEditText;
    CircleImageView circleImageView;
    EditText passwordEditText;

    Button createAccountButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_staff);

        nameEditText = findViewById(R.id.add_staff_name_edit_text_name);
        ageEditText = findViewById(R.id.add_staff_age_edit_text);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        createAccountButton = findViewById(R.id.create_account);
        circleImageView = findViewById(R.id.create_staff_image_view);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(email.trim())) {
                    Toast.makeText(CreateStaffActivity.this,
                            "Email is empty.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (password.trim().length() < 6) {
                    Toast.makeText(CreateStaffActivity.this,
                            "Password at least 6 characters.",
                            Toast.LENGTH_LONG).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CreateStaffActivity.this,
                                        "Account created.",
                                        Toast.LENGTH_SHORT).show();

                                createStaff();
                                if (!fileName.isEmpty()) {
                                    uploadFile(fileName);
                                    fileName = "";
                                }
                                Intent intent = new Intent(CreateStaffActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(CreateStaffActivity.this,
                                        "Create account failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void createStaff() {
        fileName = "";
        if (imageUrl != null) {
            fileName = StringHelper.generateGuidFileNameWithExtension(imageUrl, this);
        }

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("email", emailEditText.getText().toString());
        map.put("name", nameEditText.getText().toString());
        map.put("age", Integer.parseInt(ageEditText.getText().toString()));
        map.put("image", fileName);

        FirebaseDatabase.getInstance().getReference()
            .child("appUsers")
            .push()
            .setValue(map)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(CreateStaffActivity.this,
                            "Add new staff successfully.",
                            Toast.LENGTH_LONG).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateStaffActivity.this,
                            "Something went wrong.",
                            Toast.LENGTH_LONG).show();
                }
            });
    }

    private void uploadFile(String newFileName) {
        storageReference = FirebaseStorage.getInstance().getReference("images/"+newFileName);

        storageReference.putFile(imageUrl)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CreateStaffActivity.this,
                            "Upload image success.", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateStaffActivity.this,
                            "Upload image failed.", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,CreateStaffActivity.SELECT_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CreateStaffActivity.SELECT_IMAGE_REQUEST_CODE &&
                data != null &&
                data.getData() != null
        ) {
            imageUrl = data.getData();
            circleImageView.setImageURI(imageUrl);
        }
    }
}