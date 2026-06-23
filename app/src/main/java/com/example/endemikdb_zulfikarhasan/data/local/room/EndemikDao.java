package com.example.endemikdb_zulfikarhasan.data.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.endemikdb_zulfikarhasan.data.model.Endemik;
import com.example.endemikdb_zulfikarhasan.data.model.Favorite;
import com.example.endemikdb_zulfikarhasan.data.model.Favorite;
import com.example.endemikdb_zulfikarhasan.data.model.Favorite;
import com.example.endemikdb_zulfikarhasan.data.model.Favorite;
import com.example.endemikdb_zulfikarhasan.data.model.Favorite;
import com.example.endemikdb_zulfikarhasan.data.model.Favorite;
import com.example.endemikdb_zulfikarhasan.data.model.Favorite;

import java.util.List;

@Dao
public interface EndemikDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Endemik> endemikList);

    @Query("SELECT * FROM endemik_table WHERE tipe = :tipe")
    LiveData<List<Endemik>> getEndemikByTipe(String tipe);

    @Query("SELECT * FROM endemik_table WHERE tipe = :tipe AND asal LIKE '%' || :region || '%'")
    LiveData<List<Endemik>> getEndemikByTipeAndRegion(String tipe, String region);

    @Query("SELECT * FROM endemik_table WHERE nama LIKE '%' || :query || '%'")
    LiveData<List<Endemik>> searchEndemik(String query);

    @Query("SELECT * FROM endemik_table INNER JOIN favorit ON endemik_table.id = favorit.endemikId")
    LiveData<List<Endemik>> getFavoriteEndemik();

    @Query("SELECT COUNT(*) FROM endemik_table")
    int getCount();

    @Update
    void update(Endemik endemik);

    @Query("SELECT * FROM endemik_table WHERE id = :id")
    LiveData<Endemik> getEndemikById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavorite(Favorite favorite);

    @Query("DELETE FROM favorit WHERE endemikId = :id")
    void removeFavorite(String id);

    @Query("SELECT EXISTS(SELECT 1 FROM favorit WHERE endemikId = :id)")
    LiveData<Boolean> isFavorite(String id);

    @Query("SELECT DISTINCT asal FROM endemik_table")
    LiveData<List<String>> getAllRegions();
}
