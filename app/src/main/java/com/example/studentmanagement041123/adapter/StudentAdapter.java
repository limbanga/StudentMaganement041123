package com.example.studentmanagement041123.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement041123.R;
import com.example.studentmanagement041123.model.Student;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class StudentAdapter extends FirebaseRecyclerAdapter<Student, StudentAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView ageTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name);
            ageTextView = itemView.findViewById(R.id.age);

        }
    }

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public StudentAdapter(@NonNull FirebaseRecyclerOptions<Student> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Student model) {
        holder.nameTextView.setText(model.getName());
        holder.ageTextView.setText(model.getAge().toString());
    }
}
