package com.example.studentmanagement041123.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.studentmanagement041123.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateCertificateDialog extends Dialog {


    String studentId;
    EditText nameEditText;

    public CreateCertificateDialog(@NonNull Context context, String studentId) {
        super(context);
        this.setContentView(R.layout.dialog_create_certificate);
        this.getWindow()
                .setLayout(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        this.getWindow().setBackgroundDrawable(
                context.getDrawable(R.drawable.bottom_sheet_dialog_shape));
        this.setCancelable(false);

        this.studentId = studentId;
        MaterialButton confirmButton = this.findViewById(R.id.confirm_dialog_confirm_button);
        MaterialButton cancelButton = this.findViewById(R.id.confirm_dialog_cancel_button);
        nameEditText = this.findViewById(R.id.create_certificate_name_edit_text_name);

        CreateCertificateDialog _this = this;
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _this.dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createStudent();
                _this.dismiss();
            }
        });
    }

    public String getNameEditText() {
        return nameEditText.getText().toString();
    }

    private void createStudent() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", nameEditText.getText().toString());

        FirebaseDatabase.getInstance().getReference()
            .child("students")
            .child(studentId)
            .child("certificates")
            .push()
            .setValue(map)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getContext(),
                            "Add new certificate successfully.",
                            Toast.LENGTH_LONG).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),
                            "Something went wrong.",
                            Toast.LENGTH_LONG).show();
                }
            });
    }
}
