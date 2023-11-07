package com.example.studentmanagement041123.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement041123.R;
import com.example.studentmanagement041123.model.Staff;
import com.example.studentmanagement041123.model.Student;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.HashMap;
import java.util.Map;

public class StaffAdapter extends FirebaseRecyclerAdapter<Staff, StaffAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView ageTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name);
            ageTextView = itemView.findViewById(R.id.age);

        }
    }

    public StaffAdapter(@NonNull FirebaseRecyclerOptions<Staff> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull Staff model) {
        holder.nameTextView.setText(model.getName());
        holder.ageTextView.setText(model.getAge().toString());

//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.ageTextView.getContext())
//                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_popup))
//                        .setExpanded(true)
//                        .create();
//
//                View v = dialogPlus.getHolderView();
//                EditText name = v.findViewById(R.id.name);
//                EditText age = v.findViewById(R.id.age);
//                Button update = v.findViewById(R.id.update);
//
//                name.setText(model.getName());
//                age.setText(model.getAge().toString());
//
//                update.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Map<String, Object> map = new HashMap<String, Object>();
//
//                        map.put("name", name.getText().toString());
//                        map.put("age", Integer.valueOf(age.getText().toString()));
//
//                        FirebaseDatabase.getInstance().getReference().child("students")
//                                .child(getRef(position).getKey()).updateChildren(map)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(holder.nameTextView.getContext(),
//                                                "Update student successfully.",
//                                                Toast.LENGTH_LONG).show();
//                                        dialogPlus.dismiss();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(holder.nameTextView.getContext(),
//                                                "Something went wrong.",
//                                                Toast.LENGTH_LONG).show();
//                                        dialogPlus.dismiss();
//                                    }
//                                });
//                    }
//                });
//
//                dialogPlus.show();
//            }
//        });
//
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nameTextView.getContext());
//                builder.setTitle("Confirm delete.");
//                builder.setMessage("Are you sure to delete this student? This action can't undo.");
//
//                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        FirebaseDatabase.getInstance().getReference().child("students")
//                                    .child(getRef(position).getKey()).removeValue();
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(holder.nameTextView.getContext(),
//                                "Canceled delete.",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                builder.show();
//            }
//        });
    }
}
