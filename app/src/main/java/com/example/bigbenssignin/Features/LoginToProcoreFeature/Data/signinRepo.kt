package com.example.bigbenssignin.Features.LoginToProcoreFeature.Data

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.bigbenssignin.SuccessState
import com.example.bigbenssignin.keys
import com.example.bigbenssignin.Features.LoginToProcoreFeature.Domain.signinInterface
import com.example.bigbenssignin.tokenRefreshToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class signinRepo @Inject constructor(
    private val client: HttpClient,
    val datastore :DataStore<tokenRefreshToken>
): signinInterface {
    override suspend fun getTokenFromAuthorisationCode(authorisationCode:String):SuccessState<String> {
        val requestForTokenFromProcore = RequestForTokenFromProcore(
            client_id = keys().client_id,
            client_secret = keys().client_secret,
            code = authorisationCode,
            grant_type = "authorization_code",
            redirect_uri = "urn:ietf:wg:oauth:2.0:oob"
        )
        val jsonQuery = Json.encodeToString(requestForTokenFromProcore)
        Log.d("json", jsonQuery)
        return try {
            val token = client.post("https://sandbox.procore.com/oauth/token"){
                contentType(ContentType.Application.Json)
                setBody(jsonQuery)
            }.body<returnFromRequestoForTokin>()
            datastore.updateData { data ->
                data.copy(token = token.access_token, refreshToken = token.refresh_token )
            }
            Log.d("json", token.access_token)
            SuccessState.Success(token.access_token)
        }catch (e:Exception){
            Log.d("in fail block","")

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