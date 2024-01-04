package com.licoding.instagramapp.domain.services

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeSerializer: KSerializer<LocalDateTime>{
    @RequiresApi(Build.VERSION_CODES.O)
    private val formater: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.format(formater))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(decoder: Decoder): LocalDateTime {
        return try {
            LocalDateTime.parse(decoder.decodeString(), formater)
        } catch (e: Exception) {
            throw SerializationException("Error deserializing LocalDateTime", e)
        }
    }
}


