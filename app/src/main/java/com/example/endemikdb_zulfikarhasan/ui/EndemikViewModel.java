package com.example.endemikdb_zulfikarhasan.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.endemikdb_zulfikarhasan.data.model.Endemik;
import com.example.endemikdb_zulfikarhasan.repository.EndemikRepository;

import java.util.List;

public class EndemikViewModel extends AndroidViewModel {
    private final EndemikRepository repository;

    public EndemikViewModel(@NonNull Application application) {
        super(application);
        repository = new EndemikRepository(application);
    }

    public LiveData<List<Endemik>> getEndemikByTipe(String tipe) {
        return repository.getEndemikByTipe(tipe);
    }

    public LiveData<List<Endemik>> getEndemikByTipeAndRegion(String tipe, String region) {
        return repository.getEndemikByTipeAndRegion(tipe, region);
    }

    public LiveData<List<String>> getAllRegions() {
        return repository.getAllRegions();
    }

    public LiveData<List<Endemik>> searchEndemik(String query) {
        return repository.searchEndemik(query);
    }

    public LiveData<List<Endemik>> getFavoriteEndemik() {
        return repository.getFavoriteEndemik();
    }

    public LiveData<Endemik> getEndemikById(String id) {
        return repository.getEndemikById(id);
    }

    public void addFavorite(String id) {
        repository.addFavorite(id);
    }

    public void removeFavorite(String id) {
        repository.removeFavorite(id);
    }

    public LiveData<Boolean> isFavorite(String id) {
        return repository.isFavorite(id);
    }

    public void fetchDataFromApi(EndemikRepository.OnDataLoadedListener listener) {
        repository.fetchDataFromApi(listener);
    }
}
