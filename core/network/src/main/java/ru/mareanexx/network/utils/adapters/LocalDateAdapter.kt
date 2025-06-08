package ru.mareanexx.network.utils.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDate

class LocalDateAdapter : JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
    override fun deserialize(
        json: JsonElement, typeOfT: Type, context: JsonDeserializationContext
    ): LocalDate {
        return LocalDate.parse(json.asString)
    }

    override fun serialize(
        src: LocalDate, typeOfSrc: Type, context: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}
