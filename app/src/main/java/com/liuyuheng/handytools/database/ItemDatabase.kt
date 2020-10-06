package com.liuyuheng.handytools.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Item::class],
    version = 1,
    exportSchema = false
)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDAO(): ItemDAO

    companion object {
        private var instance: ItemDatabase? = null
        @Synchronized
        fun getInstance(context: Context): ItemDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java, "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}