package com.example.studentmanagement041123.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.studentmanagement041123.R;
import com.google.android.material.button.MaterialButton;

public class ConfirmDialog extends Dialog {

    public interface Listener {
        void onConfirmClick();
    }

    public ConfirmDialog(@NonNull Context context, Listener listener) {
        super(context);
        this.setContentView(R.layout.dialog_custom);
        this.getWindow()
                .setLayout(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        this.getWindow().setBackgroundDrawable(
                context.getDrawable(R.drawable.bottom_sheet_dialog_shape));
        this.setCancelable(false);

        MaterialButton confirmButton = this.findViewById(R.id.confirm_dialog_confirm_button);
        MaterialButton cancelButton = this.findViewById(R.id.confirm_dialog_cancel_button);

        ConfirmDialog _this = this;
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _this.dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onConfirmClick();
                _this.dismiss();
            }
        });
    }

}
