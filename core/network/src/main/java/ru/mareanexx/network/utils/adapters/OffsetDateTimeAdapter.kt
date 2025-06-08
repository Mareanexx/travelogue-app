package ru.mareanexx.network.utils.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class OffsetDateTimeAdapter : JsonDeserializer<OffsetDateTime>, JsonSerializer<OffsetDateTime> {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): OffsetDateTime {
        return OffsetDateTime.parse(json.asString, formatter)
    }

    override fun serialize(src: OffsetDateTime, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.format(formatter))
    }
}