package com.azamovhudstc.newsappwithdagger2.data.models.news_by_category

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class StringListConverter {
    @TypeConverter
    fun fromStringList(list: List<String?>?): String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun toStringList(companyString: String?): List<String>? {
        if (companyString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson<List<String>>(companyString, type)
    }
}