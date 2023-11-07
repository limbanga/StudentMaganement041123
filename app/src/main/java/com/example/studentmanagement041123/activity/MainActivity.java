package com.example.studentmanagement041123.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.studentmanagement041123.fragment.BottomDialogFragment;
import com.example.studentmanagement041123.fragment.ProfileFragment;
import com.example.studentmanagement041123.R;
import com.example.studentmanagement041123.fragment.StaffFragment;
import com.example.studentmanagement041123.fragment.StudentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton addButton;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frame_layout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        mAuth = FirebaseAuth.getInstance();

        replaceFragment(new StudentFragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_student:
                        replaceFragment(new StudentFragment());
                        break;
                    case R.id.item_search:
                        Toast.makeText(MainActivity.this, "Chua lam search", Toast.LENGTH_LONG).show();
                        break;
                    case  R.id.item_add:
                        Toast.makeText(MainActivity.this, "Chua lam add", Toast.LENGTH_LONG).show();
                        openDialog();
                        return false;
                    case R.id.item_staff:
                        replaceFragment(new StaffFragment());
                        break;
                    case R.id.item_profile:
                        replaceFragment(new ProfileFragment());
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void openDialog() {
        BottomSheetDialogFragment dialogFragment = new BottomDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "dialog add");
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            return;
        }
    }

}