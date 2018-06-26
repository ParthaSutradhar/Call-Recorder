package com.xemosoft.callrecorder.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CallDao {

    @Query("Select * from calls")
    List<Call> getCallList();

    @Insert
    void insert(Call... calls);

    @Delete
    void delete(Call call);

}
