package com.example.q_rier.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pelanggan(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val tanggalLahir: String,
    val phone: String
    //test
)
