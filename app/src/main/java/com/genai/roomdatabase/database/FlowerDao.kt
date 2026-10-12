package com.genai.roomdatabase.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FlowerDao {

    /**
     * CREATE: Adds a new flower to the database.
     * 'suspend' ensures this runs in a background thread so it doesn't freeze the UI.
     */
    @Insert
    suspend fun insertFlower(flower: Flower)

    /**
     * READ: Fetches all flowers from the database.
     * Returning 'Flow' is powerful: whenever the database changes, this Flow will
     * automatically emit the new list, allowing our UI to update in real-time.
     */
    @Query("SELECT * FROM Flower")
    fun getAllFlowers(): Flow<List<Flower>>

    /**
     * READ (Single): Finds a specific flower by its unique ID.
     */
    @Query("SELECT * FROM Flower WHERE id = :id")
    suspend fun getFlowerById(id: Int): Flower?
    /**
     * UPDATE: Modifies an existing flower record.
     * Room matches the 'id' of the flower object passed in to find which row to update.
     */
    @Update
    suspend fun updateFlower(flower: Flower)

    /**
     * UPDATE (Custom): Updates a flower's details using specific values.
     */
    @Query("UPDATE Flower SET name = :name, color = :color WHERE id = :id")
    suspend fun updateFlowerById(id: Int, name: String, color: String)

    /**
     * DELETE (by ID): Removes a flower record from the table using its ID.
     */
    @Query("DELETE FROM Flower WHERE id = :id")
    suspend fun deleteFlowerById(id: Int)

    /**
     * DELETE (Object): Removes a specific flower object from the database.
     */
    @Delete
    suspend fun deleteFlower(flower: Flower)
}