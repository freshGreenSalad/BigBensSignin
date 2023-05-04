package com.example.bigbenssignin.common.domain

import com.example.bigbenssignin.common.data.dataStore.LoggedInProfileKeyIdentifiers

interface CommonHttpClientFunctions {

    suspend fun tokenTimeoutCallBack(procoreApiFunction: suspend (newToken:String)-> String):String

    suspend fun getTokenFromRefreshToken(loggedInProfileKeyIdentifiers: LoggedInProfileKeyIdentifiers)

}