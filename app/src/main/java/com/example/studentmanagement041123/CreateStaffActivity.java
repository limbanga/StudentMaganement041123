package com.example.studentmanagement041123;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateStaffActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText ageEditText;
    EditText emailEditText;
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

        mAuth = FirebaseAuth.getInstance();

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

                                addStaffToFireBase();
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
    }

    private boolean addStaffToFireBase() {
        final boolean[] isSuccess = {false};
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("email", emailEditText.getText().toString());
        map.put("name", nameEditText.getText().toString());
        map.put("age", Integer.parseInt(ageEditText.getText().toString()));

        FirebaseDatabase.getInstance().getReference()
                .child("appUsers")
                .push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateStaffActivity.this,
                                "Add new student successfully.",
                                Toast.LENGTH_LONG).show();
                        isSuccess[0] = true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateStaffActivity.this,
                                "Something went wrong.",
                                Toast.LENGTH_LONG).show();
                        isSuccess[0] = false;
                    }
                });
        return  isSuccess[0];
    }
}