package com.example.studentmanagement041123;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.studentmanagement041123.adapter.StudentAdapter;
import com.example.studentmanagement041123.model.Student;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

//    FloatingActionButton floatingActionButton;
//    FloatingActionButton logoutButton;
    ViewPager2 viewPager;
    BottomNavigationView bottomNavigationView;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
//        floatingActionButton = findViewById(R.id.floatingActionButton);
//        logoutButton = findViewById(R.id.logout_button);

        mAuth = FirebaseAuth.getInstance();


        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.item_student).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.item_profile).setChecked(true);
                    default:
                        break;
                }
            }
        });

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_student:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.item_profile:
                        viewPager.setCurrentItem(3);
                        break;
                    default:
                        break;
                }
            }
        });

//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AddActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
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

//        FirebaseRecyclerOptions<Student> options =
//                new FirebaseRecyclerOptions.Builder<Student>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students"), Student.class)
//                        .build();

//        studentAdapter = new StudentAdapter(options);
//        recyclerView.setAdapter(studentAdapter);
//
//        studentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        studentAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                textSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                textSearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}