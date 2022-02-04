package com.example.currencyapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.currencyapp.pojo.Valute;

import java.util.List;

@Dao
public interface ValuteDao {

    @Query("SELECT * FROM valutes")
    LiveData<List<Valute>> getAllValutes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertValute(Valute valute);

    @Query("DELETE FROM valutes")
    void deleteAllValutes();
}
