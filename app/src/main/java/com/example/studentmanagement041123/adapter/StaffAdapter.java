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
import com.example.studentmanagement041123.activity.StaffDetailActivity;
import com.example.studentmanagement041123.activity.StudentDetailActivity;
import com.example.studentmanagement041123.model.Staff;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class StaffAdapter extends FirebaseRecyclerAdapter<Staff, StaffAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView ageTextView;
        CircleImageView circleImageView;

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name);
            ageTextView = itemView.findViewById(R.id.age);
            circleImageView = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.staff_item_card_view);
        }
    }

    public StaffAdapter(@NonNull FirebaseRecyclerOptions<Staff> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull Staff model) {
        holder.nameTextView.setText(model.getName());
        holder.ageTextView.setText(model.getAge().toString());
        Glide.with(holder.nameTextView.getContext())
                .load(model.getImageUrl())
                .into(holder.circleImageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String staffId = getRef(position).getKey();
                Intent intent = new Intent(holder.nameTextView.getContext(), StaffDetailActivity.class);
                intent.putExtra(StaffDetailActivity.STAFF_ID_KEY_NAME, staffId);
                holder.nameTextView.getContext().startActivity(intent);
            }
        });
    }
}
