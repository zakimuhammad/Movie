package com.zaki.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaki.movieapp.data.local.entitiy.AuthEntity
import kotlinx.coroutines.flow.Flow

@Dao interface AuthDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertUser(authEntity: AuthEntity)

  @Query("SELECT * FROM auth WHERE user_name = :username")
  fun getUser(username: String): Flow<AuthEntity?>
}