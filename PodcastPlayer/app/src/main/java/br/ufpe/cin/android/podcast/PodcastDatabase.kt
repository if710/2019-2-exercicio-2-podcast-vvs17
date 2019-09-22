package br.ufpe.cin.android.podcast

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities= arrayOf(ItemFeed::class), version=1)
abstract class PodcastDatabase : RoomDatabase() {
    abstract fun podcastDAO() : PodcastDAO
    companion object {
        private var INSTANCE : PodcastDatabase? = null
        fun getDatabase(ctx : Context) : PodcastDatabase {
            if (INSTANCE == null) {
                synchronized(PodcastDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        ctx.applicationContext,
                        PodcastDatabase::class.java,
                        "podcast.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}