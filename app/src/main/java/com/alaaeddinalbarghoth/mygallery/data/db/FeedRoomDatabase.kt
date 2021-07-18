package com.alaaeddinalbarghoth.mygallery.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alaaeddinalbarghoth.mygallery.data.dao.FeedDao
import com.alaaeddinalbarghoth.mygallery.data.model.FeedItem

@Database(entities = [FeedItem::class], version = 1, exportSchema = false)
abstract class FeedRoomDatabase : RoomDatabase() {

    abstract fun feedDao(): FeedDao

    companion object {
        @Volatile
        private var INSTANCE: FeedRoomDatabase? = null

        fun getDatabase(context: Context): FeedRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FeedRoomDatabase::class.java,
                    "feed_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}