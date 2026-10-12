package com.genai.roomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Flower::class], version = 1, exportSchema = false)
abstract class FlowerDataBase : RoomDatabase() {
    // Abstract method that returns our interface for database operations
    abstract fun flowerDao(): FlowerDao

    companion object {
        /**
         * SINGLETON PATTERN: Creating a database instance is expensive.
         * We use this pattern to ensure only ONE instance of the database exists
         * across the entire application lifecycle.
         */

        // @Volatile ensures the instance is always visible to all threads
        @Volatile
        private var INSTANCE: FlowerDataBase? = null

        fun getDatabase(context: Context): FlowerDataBase {
            // If INSTANCE is not null, return it; otherwise, create it
            return INSTANCE ?: synchronized(this) {
                // synchronized(this) prevents multiple threads from creating the DB at the same time
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlowerDataBase::class.java,
                    "flower_db" // The name of the database file
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}