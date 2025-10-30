// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/data/database/entities/Converters.kt
package com.bahaicommunity.data.database.entities

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
