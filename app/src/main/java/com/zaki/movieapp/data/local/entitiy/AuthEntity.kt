package com.zaki.movieapp.data.local.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth") data class AuthEntity(
  @PrimaryKey @ColumnInfo(name = "user_name") val userName: String,

  @ColumnInfo(name = "password") val password: String,

  @ColumnInfo(name = "name") val name: String,
)
