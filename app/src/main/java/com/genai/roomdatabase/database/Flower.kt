package com.genai.roomdatabase.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Flower(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val color:String
)
