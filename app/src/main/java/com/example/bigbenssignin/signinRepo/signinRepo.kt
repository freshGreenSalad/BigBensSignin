package com.example.bigbenssignin.signinRepo

import com.example.bigbenssignin.SuccessState
import com.example.bigbenssignin.keys
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class signinRepo @Inject constructor(
    private val client: HttpClient
): signinInterface {
    override suspend fun getTokenFromAuthorisationCode(authorisationCode:String):SuccessState<String> {
        val requestForTokenFromProcore = RequestForTokenFromProcore(
            client_id = keys().client_id,
            client_secret = keys().client_secret,
            code = authorisationCode,
            grant_type = "authorization_code",
            redirect_uri = "urn:ietf:wg:oauth:2.0:oob"
        )
        return try {
            val token = client.get("https://sandbox.procore.com/oauth/token"){
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(requestForTokenFromProcore))
            }.body<returnFromRequestoForTokin>()
            SuccessState.Success(token.access_token)
        }catch (e:Exception){
            SuccessState.Failure()
        }
    }
}

@kotlinx.serialization.Serializable
data class RequestForTokenFromProcore(
    val client_id: String,
    val client_secret: String,
    val code: String,
    val grant_type: String,
    val redirect_uri: String
)

@kotlinx.serialization.Serializable
data class returnFromRequestoForTokin(
    val access_token: String,
    val created_at: Int,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String
)