package com.example.studentmanagement041123.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.studentmanagement041123.R;
import com.example.studentmanagement041123.activity.StudentDetailActivity;
import com.example.studentmanagement041123.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateCertificateDialog extends Dialog {


    String studentId;
    String certificateId;
    EditText nameEditText;

    public UpdateCertificateDialog(@NonNull Context context, String studentId, String certificateId) {
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
        this.certificateId = certificateId;
        loadData();

        MaterialButton confirmButton = this.findViewById(R.id.confirm_dialog_confirm_button);
        MaterialButton cancelButton = this.findViewById(R.id.confirm_dialog_cancel_button);
        nameEditText = this.findViewById(R.id.create_certificate_name_edit_text_name);

        UpdateCertificateDialog _this = this;
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _this.dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStudent();
                _this.dismiss();
            }
        });
    }


    private void updateStudent() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", nameEditText.getText().toString());

        FirebaseDatabase.getInstance().getReference()
            .child("students")
            .child(studentId)
            .child("certificates")
            .child(certificateId)
            .updateChildren(map)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getContext(),
                            "Update certificate successfully.",
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

    private void loadData() {
        FirebaseDatabase.getInstance().getReference()
            .child("students")
            .child(studentId)
            .child("certificates")
            .child(certificateId)
            .get()
            .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        if (dataSnapshot.exists()){
                            String name = String.valueOf(dataSnapshot.child("name").getValue());
                            nameEditText.setText(name);
                        } else {
                            Toast.makeText(getContext() ,
                                    " Certificate doesn't exist.",
                                    Toast.LENGTH_LONG).show();
                            dismiss();
                        }
                    } else {
                        Toast.makeText(getContext() ,
                                "Failed to read.",
                                Toast.LENGTH_LONG).show();
                        dismiss();
                    }
                }
            });
    }
}
