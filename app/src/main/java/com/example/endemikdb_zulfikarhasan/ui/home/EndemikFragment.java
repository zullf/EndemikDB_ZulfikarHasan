package com.example.endemikdb_zulfikarhasan.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.endemikdb_zulfikarhasan.R;
import com.example.endemikdb_zulfikarhasan.adapter.EndemikAdapter;
import com.example.endemikdb_zulfikarhasan.ui.EndemikViewModel;
import com.example.endemikdb_zulfikarhasan.ui.detail.DetailActivity;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.List;

public class EndemikFragment extends Fragment {
    private static final String ARG_TIPE = "tipe";
    private static final String ARG_IS_FAVORITE = "is_favorite";
    private String tipe;
    private boolean isFavoriteMode;
    private EndemikViewModel viewModel;
    private EndemikAdapter adapter;
    private Spinner spinnerRegion;
    private String currentRegion = "Semua";

    public static EndemikFragment newInstance(String tipe) {
        EndemikFragment fragment = new EndemikFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TIPE, tipe);
        args.putBoolean(ARG_IS_FAVORITE, false);
        fragment.setArguments(args);
        return fragment;
    }

    public static EndemikFragment newFavoriteInstance() {
        EndemikFragment fragment = new EndemikFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_FAVORITE, true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tipe = getArguments().getString(ARG_TIPE);
            isFavoriteMode = getArguments().getBoolean(ARG_IS_FAVORITE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_endemik, container, false);
        
        spinnerRegion = view.findViewById(R.id.spinner_region);
        View filterCard = view.findViewById(R.id.filter_card);
        
        RecyclerView recyclerView = view.findViewById(R.id.rv_endemik);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        
        adapter = new EndemikAdapter(endemik -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("ENDEMIK_ID", endemik.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(EndemikViewModel.class);
        
        if (isFavoriteMode) {
            filterCard.setVisibility(View.GONE);
            viewModel.getFavoriteEndemik().observe(getViewLifecycleOwner(), list -> adapter.setEndemikList(list));
        } else {
            setupFilter();
            observeData();
        }

        return view;
    }

    private void setupFilter() {
        viewModel.getAllRegions().observe(getViewLifecycleOwner(), regions -> {
            List<String> filterList = new ArrayList<>();
            filterList.add("Semua");
            if (regions != null) {
                filterList.addAll(regions);
            }
            
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, filterList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRegion.setAdapter(spinnerAdapter);
        });

        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentRegion = parent.getItemAtPosition(position).toString();
                observeData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void observeData() {
        viewModel.getEndemikByTipeAndRegion(tipe, currentRegion).observe(getViewLifecycleOwner(), list -> {
            adapter.setEndemikList(list);
        });
    }
}
