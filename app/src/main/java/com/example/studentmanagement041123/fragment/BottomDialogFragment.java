package com.example.studentmanagement041123.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentmanagement041123.R;
import com.example.studentmanagement041123.activity.CreateStaffActivity;
import com.example.studentmanagement041123.activity.CreateStudentActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomDialogFragment extends BottomSheetDialogFragment {

    TextView openAddStudentButton;
    TextView openAddStaffButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_dialog, container, false);

        openAddStudentButton = view.findViewById(R.id.open_add_student_button);
        openAddStaffButton = view.findViewById(R.id.open_add_staff_button);

        openAddStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), CreateStudentActivity.class);
                requireContext().startActivity(intent);
            }
        });

        openAddStaffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), CreateStaffActivity.class);
                requireContext().startActivity(intent);
            }
        });

        return view;
    }
}