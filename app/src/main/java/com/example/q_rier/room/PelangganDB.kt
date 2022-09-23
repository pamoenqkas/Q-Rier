package com.example.q_rier.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Pelanggan::class],
    version = 1
)

abstract class PelangganDB: RoomDatabase() {
    abstract fun PelanganDao() : PelangganDao
    companion object {
        @Volatile private var instance : PelangganDB? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PelangganDB::class.java,
                "pelanggan12345.db"
            ).build()
    }
}
