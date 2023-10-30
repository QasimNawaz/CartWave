package com.qasimnawaz019.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qasimnawaz019.data.database.entities.RatingEntity

class RatingTypeConverter {
//    @TypeConverter
//    inline fun <reified T> toJson(obj: T?): String? {
//        return Gson().toJson(
//            obj, object : TypeToken<T?>() {}.type
//        ) ?: ""
//    }
//
//    @TypeConverter
//    inline fun <reified T> fromJson(json: String?): T? {
//        return Gson().fromJson(
//            json, object : TypeToken<T?>() {}.type
//        )
//    }

    @TypeConverter
    fun toJson(obj: RatingEntity?): String? {
        return Gson().toJson(
            obj, object : TypeToken<RatingEntity?>() {}.type
        )
    }

    @TypeConverter
    fun fromJson(json: String?): RatingEntity? {
        return Gson().fromJson(
            json, object : TypeToken<RatingEntity?>() {}.type
        )
    }
}

//@ProvidedTypeConverter
//class GenericTypeConverter<T>(
//    private val jsonParser: JsonParser
//) {
//    @TypeConverter
//    fun toJson(obj: T?): String {
//        return jsonParser.toJson(
//            obj,
//            object : TypeToken<T?>() {}.type
//        ) ?: ""
//    }
//
//    @TypeConverter
//    fun fromJson(json: String): T? {
//        return jsonParser.fromJson(
//            json,
//            object : TypeToken<T?>() {}.type
//        )
//    }
//}