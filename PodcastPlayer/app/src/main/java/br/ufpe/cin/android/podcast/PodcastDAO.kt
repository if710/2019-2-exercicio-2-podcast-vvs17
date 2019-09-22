package br.ufpe.cin.android.podcast

import androidx.room.*

@Dao
interface PodcastDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemFeed(vararg itemfeeds:ItemFeed)

    @Delete
    fun deleteItemFeed(vararg itemfeeds:ItemFeed)

    @Query("SELECT * FROM ItemFeed")
    fun getAll() : List<ItemFeed>

    @Query("SELECT * FROM itemFeed WHERE title LIKE :selected")
    fun getItem(selected : String) : ItemFeed
}