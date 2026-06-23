package com.example.endemikdb_zulfikarhasan.data.remote.api;

import com.example.endemikdb_zulfikarhasan.data.model.Endemik;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("endemik.json")
    Call<List<Endemik>> getAllEndemik();
}
