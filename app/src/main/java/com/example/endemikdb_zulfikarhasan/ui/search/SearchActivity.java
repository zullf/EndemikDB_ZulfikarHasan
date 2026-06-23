package com.example.endemikdb_zulfikarhasan.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.endemikdb_zulfikarhasan.R;
import com.example.endemikdb_zulfikarhasan.adapter.EndemikAdapter;
import com.example.endemikdb_zulfikarhasan.ui.EndemikViewModel;
import com.example.endemikdb_zulfikarhasan.ui.detail.DetailActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class SearchActivity extends AppCompatActivity {
    private EndemikViewModel viewModel;
    private EndemikAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.rv_search);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        
        adapter = new EndemikAdapter(endemik -> {
            Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
            intent.putExtra("ENDEMIK_ID", endemik.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(EndemikViewModel.class);

        TextInputEditText etSearch = findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void search(String query) {
        viewModel.searchEndemik(query).observe(this, list -> {
            adapter.setEndemikList(list);
        });
    }
}
