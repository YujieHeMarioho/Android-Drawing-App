package com.example.finaldraw

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

//JSON Serialization and make this a RoomDB entity
@Entity
@Serializable
data class Drawing(
    @Transient @PrimaryKey (autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "file_name") val fileName: String
)