package com.example.bigbenssignin.common.data.dataStore

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class LoggedInProfileKeyIdentifiers(
    val token:String = "",
    val refreshToken: String = "",
    val company: String = "",
    val project: String = ""
)

object TokenSerialiser: Serializer<LoggedInProfileKeyIdentifiers> {

    override val defaultValue: LoggedInProfileKeyIdentifiers
        get() = LoggedInProfileKeyIdentifiers()

    override suspend fun readFrom(input: InputStream): LoggedInProfileKeyIdentifiers {
        return try {
            Json.decodeFromString(input.readBytes().decodeToString())
        }catch (e: Exception){
            defaultValue
        }
    }

    override suspend fun writeTo(t: LoggedInProfileKeyIdentifiers, output: OutputStream) { //this is named t as that is what it is named in the superclass
        withContext(Dispatchers.IO) {// TODO: hardcoded dispatcher
            output.write(
                Json.encodeToString(t).encodeToByteArray()
            )
        }
    }
}