package com.example.studentmanagement041123.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentmanagement041123.R;
import com.example.studentmanagement041123.activity.StudentDetailActivity;
import com.example.studentmanagement041123.dialog.ConfirmDialog;
import com.example.studentmanagement041123.dialog.UpdateCertificateDialog;
import com.example.studentmanagement041123.model.Certificate;
import com.example.studentmanagement041123.model.Student;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class CertificateAdapter extends FirebaseRecyclerAdapter<Certificate, CertificateAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView deleteImageView;
        ImageView openUpdateImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.certificate_item_text_view);
            deleteImageView = itemView.findViewById(R.id.certificate_delete_image_view);
            openUpdateImageView = itemView.findViewById(R.id.certificate_open_update_image_view);
        }
    }

    private String studentId;
    public CertificateAdapter(@NonNull FirebaseRecyclerOptions<Certificate> options, String studentId) {
        super(options);
        this.studentId = studentId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.certificate_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder,
                                    @SuppressLint("RecyclerView") int position,
                                    @NonNull Certificate model) {

        holder.nameTextView.setText(model.getName());

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmDialog.Listener listener = new ConfirmDialog.Listener() {
                    @Override
                    public void onConfirmClick() {
                        FirebaseDatabase.getInstance().getReference()
                            .child("students")
                            .child(studentId)
                            .child("certificates")
                            .child(getRef(position).getKey()).removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(holder.nameTextView.getContext(),
                                            "Delete certificate successfully.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                    }
                };

                ConfirmDialog dialog = new ConfirmDialog(view.getContext(), listener);
                dialog.show();
            }
        });

        holder.openUpdateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateCertificateDialog dialog =
                        new UpdateCertificateDialog(
                                view.getContext(),
                                studentId,
                                getRef(position).getKey());

                dialog.show();
            }
        });
    }

}
