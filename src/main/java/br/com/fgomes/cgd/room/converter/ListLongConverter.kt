package br.com.fgomes.cgd.room.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Criado por fernando.gomes em 26/06/2024.
 */
class ListLongConverter {
    @TypeConverter
    fun fromList(list: List<Long>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(value: String?): List<Long>? {
        val listType = object : TypeToken<List<Long>>() {}.type
        return Gson().fromJson(value, listType)
    }
}