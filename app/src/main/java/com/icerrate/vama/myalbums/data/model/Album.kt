package com.icerrate.vama.myalbums.data.model

import android.os.Parcelable
import androidx.room.*
import com.icerrate.vama.myalbums.data.model.converter.GenresListConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "album")
data class Album(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "artist_name")
    val artistName: String,
    val name: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "artist_url")
    val artistUrl: String?,
    @ColumnInfo(name = "artwork_url_100")
    val artworkUrl100: String,
    val url: String,
    @ColumnInfo(name = "genres_list")
    val genres: ArrayList<Genre>
) : Parcelable {

    fun getCustomResArtworkUrl(size: Int): String {
        return artworkUrl100.replace("100x100", "${size}x${size}")
    }
}
