package com.example.avtotestapp

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeJsonDeserializer  : JsonDeserializer<LocalDateTime> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): LocalDateTime {
        val dateTimeString = json.asString
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_ZONED_DATE_TIME)
    }
}