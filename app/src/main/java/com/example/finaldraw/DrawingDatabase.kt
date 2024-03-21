package com.example.finaldraw

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Database(entities = [Drawing::class], version = 1, exportSchema = false)
abstract class FactDatabase : RoomDatabase() {
    abstract fun DrawingDao(): DrawingDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FactDatabase? = null
    }
}
@Dao
interface DrawingDao {
    @Insert
    suspend fun insert(drawing: Drawing)

    @Delete
    suspend fun delete(drawing: Drawing)

    @Query("SELECT * FROM drawing")
    fun getAllDrawings(): Flow<List<Drawing>>
}