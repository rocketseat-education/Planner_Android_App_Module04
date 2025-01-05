package com.rocketseat.planner.data.model

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object ProfileSerializer: Serializer<Profile> {
    override val defaultValue: Profile
        get() = Profile()

    override suspend fun readFrom(input: InputStream): Profile {
        return try {
            Json.decodeFromString(
                deserializer = Profile.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch(_: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(
        t: Profile,
        output: OutputStream
    ) = output.write(
        Json.encodeToString(
            serializer =  Profile.serializer(),
            value = t
        ).encodeToByteArray()
    )
}