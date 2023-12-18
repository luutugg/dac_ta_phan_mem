package com.example.dactaphanmem.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dactaphanmem.data.entity.Violate

@Dao
interface ViolateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertViolate(violate: Violate)

    @Query("DELETE FROM violate WHERE violateId = :id")
    fun deleteViolate(id: String)

    @Query("DELETE FROM violate")
    fun deleteAllViolate()
}
