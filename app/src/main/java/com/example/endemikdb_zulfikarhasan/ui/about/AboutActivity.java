package com.example.endemikdb_zulfikarhasan.ui.about;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.endemikdb_zulfikarhasan.R;
import com.example.endemikdb_zulfikarhasan.utils.ThemeManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.materialswitch.MaterialSwitch;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        ThemeManager themeManager = new ThemeManager(this);
        MaterialSwitch switchTheme = findViewById(R.id.switch_theme);
        
        switchTheme.setChecked(themeManager.isDarkMode());
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            themeManager.setDarkMode(isChecked);
        });
    }
}
