package com.example.endemikdb_zulfikarhasan.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorit",
        foreignKeys = @ForeignKey(entity = Endemik.class,
                parentColumns = "id",
                childColumns = "endemikId",
                onDelete = ForeignKey.CASCADE))
public class Favorite {
    @PrimaryKey
    @NonNull
    private String endemikId;

    public Favorite(@NonNull String endemikId) {
        this.endemikId = endemikId;
    }

    @NonNull
    public String getEndemikId() {
        return endemikId;
    }

    public void setEndemikId(@NonNull String endemikId) {
        this.endemikId = endemikId;
    }
}
