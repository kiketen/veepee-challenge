package com.vp.core.data

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class Converters {
    @TypeConverter
    fun fromList(value: List<String>?) = value?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toList(value: String?) = value?.let { Json.decodeFromString<List<String>>(it) }
}