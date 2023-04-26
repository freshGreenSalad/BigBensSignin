package com.example.bigbenssignin

import androidx.datastore.core.Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class tokenRefreshToken(
    val token:String = "",
    val refreshToken: String = ""
)

object TokenSerialiser: Serializer<tokenRefreshToken> {

    override val defaultValue: tokenRefreshToken
        get() = tokenRefreshToken()

    override suspend fun readFrom(input: InputStream): tokenRefreshToken {
        return try {
            Json.decodeFromString<tokenRefreshToken>(input.readBytes().decodeToString())
        }catch (e: Exception){
            defaultValue
        }
    }

    override suspend fun writeTo(t: tokenRefreshToken, output: OutputStream) {
        output.write(
            Json.encodeToString<tokenRefreshToken>(t).encodeToByteArray()
        )
    }
}