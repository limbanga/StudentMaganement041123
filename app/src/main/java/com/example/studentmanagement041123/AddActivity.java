package com.example.studentmanagement041123;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText name;
    EditText age;

    Button add;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);

        add = findViewById(R.id.add);
        back = findViewById(R.id.back);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatStudent();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void creatStudent() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("name", name.getText().toString());
        map.put("age", Integer.parseInt(age.getText().toString()));

        FirebaseDatabase.getInstance().getReference()
                .child("students")
                .push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this,
                                "Add new student successfully.",
                                Toast.LENGTH_LONG).show();
                        clearForm();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this,
                                "Something went wrong.",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void clearForm() {
        name.setText("");
        age.setText("");
    }
}