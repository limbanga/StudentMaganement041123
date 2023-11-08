package com.example.studentmanagement041123.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentmanagement041123.R;
import com.example.studentmanagement041123.activity.StudentDetailActivity;
import com.example.studentmanagement041123.model.Student;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentAdapter extends FirebaseRecyclerAdapter<Student, StudentAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView ageTextView;
        CardView cardView;
        CircleImageView circleImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name);
            ageTextView = itemView.findViewById(R.id.age);
            cardView = itemView.findViewById(R.id.item_student_recycle_view);
            circleImageView = itemView.findViewById(R.id.image);

        }
    }

    public StudentAdapter(@NonNull FirebaseRecyclerOptions<Student> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Student model) {
        holder.nameTextView.setText(model.getName());
        holder.ageTextView.setText(model.getAge().toString());

        Glide.with(holder.nameTextView.getContext())
                .load(model.getImageUrl())
                .into(holder.circleImageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentId = getRef(position).getKey();
                Intent intent = new Intent(holder.ageTextView.getContext(), StudentDetailActivity.class);
                intent.putExtra(StudentDetailActivity.STUDENT_ID_KEY_NAME, studentId);
                holder.nameTextView.getContext().startActivity(intent);
            }
        });

    }
}
