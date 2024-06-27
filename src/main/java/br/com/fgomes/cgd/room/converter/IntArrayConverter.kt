package br.com.fgomes.cgd.room.converter

import androidx.room.TypeConverter

/**
 * Criado por fernando.gomes em 26/06/2024.
 */
class IntArrayConverter {
        @TypeConverter
        fun fromString(value: String?): IntArray? {
            return value?.split(",")?.map { it.toInt() }?.toIntArray()
        }

        @TypeConverter
        fun toString(array: IntArray?): String? {
            return array?.joinToString(",")
        }
    }