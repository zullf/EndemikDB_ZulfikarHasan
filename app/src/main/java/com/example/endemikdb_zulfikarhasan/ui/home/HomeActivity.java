package com.example.endemikdb_zulfikarhasan.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.endemikdb_zulfikarhasan.R;
import com.example.endemikdb_zulfikarhasan.ui.about.AboutActivity;
import com.example.endemikdb_zulfikarhasan.ui.search.SearchActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        
        bottomNav.setOnItemSelectedListener(item -> {
            toolbar.setTitle("EndemikDB");
            toolbar.setTitleCentered(false);
            toolbar.setNavigationIcon(null);
            toolbar.setNavigationOnClickListener(null);
            findViewById(R.id.btn_search).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_favorite).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_about).setVisibility(View.VISIBLE);

            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.nav_hewan) {
                selectedFragment = EndemikFragment.newInstance("Hewan");
            } else if (item.getItemId() == R.id.nav_tumbuhan) {
                selectedFragment = EndemikFragment.newInstance("Tumbuhan");
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, EndemikFragment.newInstance("Hewan"))
                    .commit();
        }

        findViewById(R.id.btn_search).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, SearchActivity.class));
        });

        findViewById(R.id.btn_favorite).setOnClickListener(v -> {
            toolbar.setTitle("Favorite");
            toolbar.setTitleCentered(true);
            toolbar.setNavigationIcon(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(back -> {
                // Return to home state (Hewan)
                bottomNav.setSelectedItemId(R.id.nav_hewan);
            });
            
            // Hide other icons for a cleaner look in Favorite page
            findViewById(R.id.btn_search).setVisibility(View.GONE);
            findViewById(R.id.btn_favorite).setVisibility(View.GONE);
            findViewById(R.id.btn_about).setVisibility(View.GONE);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, EndemikFragment.newFavoriteInstance())
                    .commit();
        });

        findViewById(R.id.btn_about).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
        });
    }
}
