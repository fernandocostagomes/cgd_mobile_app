package br.com.fgomes.cgd.room.converter

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.Date

/**
 * Criado por fernando.gomes em 26/06/2024.
 */
class DateConverter {
    @TypeConverter
    fun deDouble(valor: Double?) : BigDecimal {
        return valor?.let { BigDecimal(valor.toString()) } ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun bigDecimalParaDouble(valor: BigDecimal?) : Double? {
        return valor?.let { valor.toDouble() }
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return if (date == null) null else date.time
    }

}