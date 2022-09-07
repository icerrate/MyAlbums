package com.icerrate.vama.myalbums.data.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.icerrate.vama.myalbums.data.model.Genre

class GenresListConverter {

    @TypeConverter
    fun fromString(value: String): ArrayList<Genre> {
        val listType = object : TypeToken<ArrayList<Genre>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: ArrayList<Genre>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}