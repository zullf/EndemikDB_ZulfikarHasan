package com.example.endemikdb_zulfikarhasan.ui.detail;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.endemikdb_zulfikarhasan.R;
import com.example.endemikdb_zulfikarhasan.data.model.Endemik;
import com.example.endemikdb_zulfikarhasan.ui.EndemikViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class DetailActivity extends AppCompatActivity {
    private EndemikViewModel viewModel;
    private Endemik currentEndemik;
    private ImageView ivFoto;
    private TextView tvDeskripsi;
    private ImageButton btnFavorite;
    private MaterialToolbar toolbar;

    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.toolbar);
        ivFoto = findViewById(R.id.iv_foto);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        btnFavorite = findViewById(R.id.btn_favorite);

        toolbar.setNavigationOnClickListener(v -> finish());

        String id = getIntent().getStringExtra("ENDEMIK_ID");

        viewModel = new ViewModelProvider(this).get(EndemikViewModel.class);
        viewModel.getEndemikById(id).observe(this, endemik -> {
            if (endemik != null) {
                currentEndemik = endemik;
                toolbar.setTitle(endemik.getNama());
                tvDeskripsi.setText(endemik.getDeskripsi());
                Glide.with(this).load(endemik.getFoto()).into(ivFoto);
            }
        });

        viewModel.isFavorite(id).observe(this, favorite -> {
            isFavorite = favorite != null && favorite;
            updateFavoriteIcon();
        });

        btnFavorite.setOnClickListener(v -> {
            if (currentEndemik != null) {
                if (isFavorite) {
                    viewModel.removeFavorite(currentEndemik.getId());
                } else {
                    viewModel.addFavorite(currentEndemik.getId());
                }
            }
        });
    }

    private void updateFavoriteIcon() {
        if (isFavorite) {
            btnFavorite.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            btnFavorite.setImageResource(R.drawable.ic_favorite);
        }
    }
}
