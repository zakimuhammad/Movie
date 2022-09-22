package com.zaki.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.zaki.movieapp.data.local.entitiy.AuthEntity

@Dao
interface AuthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(authEntity: AuthEntity)
}