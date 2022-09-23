package com.example.q_rier.room

import androidx.room.*

@Dao
interface PelangganDao {
    @Insert
    suspend fun addNote(pelanggan: Pelanggan)
    @Update
    suspend fun updateNote(pelanggan: Pelanggan)
    @Delete
    suspend fun deleteNote(pelanggan: Pelanggan)
    @Query("SELECT * FROM pelanggan")
    suspend fun getNotes() : List<Pelanggan>
    @Query("SELECT * FROM pelanggan WHERE id =:note_id")
    suspend fun getNote(note_id: Int) : List<Pelanggan>
}