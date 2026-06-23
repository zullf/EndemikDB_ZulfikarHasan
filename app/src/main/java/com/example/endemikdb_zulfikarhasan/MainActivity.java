package com.example.endemikdb_zulfikarhasan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.endemikdb_zulfikarhasan.repository.EndemikRepository;
import com.example.endemikdb_zulfikarhasan.ui.EndemikViewModel;
import com.example.endemikdb_zulfikarhasan.ui.home.HomeActivity;
import com.example.endemikdb_zulfikarhasan.utils.ThemeManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Terapkan tema sebelum setContentView
        ThemeManager themeManager = new ThemeManager(this);
        themeManager.applyTheme();

        setContentView(R.layout.activity_main);

        Button btnContinue = findViewById(R.id.btn_continue);
        View progressBar = findViewById(R.id.progress_bar);
        
        btnContinue.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        EndemikViewModel viewModel = new ViewModelProvider(this).get(EndemikViewModel.class);
        
        // Memulai proses fetch data jika database kosong
        viewModel.fetchDataFromApi(new EndemikRepository.OnDataLoadedListener() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Gagal memuat data: " + message, Toast.LENGTH_LONG).show();
                    btnContinue.setText("Gagal, coba lagi");
                    btnContinue.setVisibility(View.VISIBLE);
                    btnContinue.setOnClickListener(v -> {
                        // Coba lagi proses fetch
                        recreate();
                    });
                });
            }
        });
    }
}
