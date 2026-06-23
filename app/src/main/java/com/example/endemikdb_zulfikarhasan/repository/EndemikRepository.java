package com.example.endemikdb_zulfikarhasan.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.endemikdb_zulfikarhasan.data.local.room.EndemikDao;
import com.example.endemikdb_zulfikarhasan.data.local.room.EndemikDatabase;
import com.example.endemikdb_zulfikarhasan.data.model.Endemik;
import com.example.endemikdb_zulfikarhasan.data.model.Favorite;
import com.example.endemikdb_zulfikarhasan.data.remote.api.ApiService;
import com.example.endemikdb_zulfikarhasan.data.remote.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndemikRepository {
    private final EndemikDao endemikDao;
    private final ApiService apiService;

    public EndemikRepository(Application application) {
        EndemikDatabase db = EndemikDatabase.getDatabase(application);
        endemikDao = db.endemikDao();
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<List<Endemik>> getEndemikByTipe(String tipe) {
        return endemikDao.getEndemikByTipe(tipe);
    }

    public LiveData<List<Endemik>> getEndemikByTipeAndRegion(String tipe, String region) {
        if (region == null || region.isEmpty() || region.equals("Semua")) {
            return endemikDao.getEndemikByTipe(tipe);
        }
        return endemikDao.getEndemikByTipeAndRegion(tipe, region);
    }

    public LiveData<List<String>> getAllRegions() {
        return endemikDao.getAllRegions();
    }

    public LiveData<List<Endemik>> searchEndemik(String query) {
        return endemikDao.searchEndemik(query);
    }

    public LiveData<List<Endemik>> getFavoriteEndemik() {
        return endemikDao.getFavoriteEndemik();
    }

    public LiveData<Endemik> getEndemikById(String id) {
        return endemikDao.getEndemikById(id);
    }

    public void addFavorite(String id) {
        EndemikDatabase.databaseWriteExecutor.execute(() -> endemikDao.addFavorite(new Favorite(id)));
    }

    public void removeFavorite(String id) {
        EndemikDatabase.databaseWriteExecutor.execute(() -> endemikDao.removeFavorite(id));
    }

    public LiveData<Boolean> isFavorite(String id) {
        return endemikDao.isFavorite(id);
    }

    public void fetchDataFromApi(OnDataLoadedListener listener) {
        EndemikDatabase.databaseWriteExecutor.execute(() -> {
            if (endemikDao.getCount() == 0) {
                apiService.getAllEndemik().enqueue(new Callback<List<Endemik>>() {
                    @Override
                    public void onResponse(Call<List<Endemik>> call, Response<List<Endemik>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            EndemikDatabase.databaseWriteExecutor.execute(() -> {
                                endemikDao.insertAll(response.body());
                                listener.onSuccess();
                            });
                        } else {
                            listener.onError("Failed to fetch data");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Endemik>> call, Throwable t) {
                        listener.onError(t.getMessage());
                    }
                });
            } else {
                listener.onSuccess();
            }
        });
    }

    public interface OnDataLoadedListener {
        void onSuccess();
        void onError(String message);
    }
}
