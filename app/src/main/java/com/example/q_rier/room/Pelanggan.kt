package com.example.q_rier.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pelanggan(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val note: String
)
