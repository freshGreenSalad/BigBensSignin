package com.example.bigbenssignin.common.Domain

import com.example.bigbenssignin.common.data.dataStore.LoggedInProfileKeyIdentifiers

interface CommonHttpClientFunctions {

    suspend fun tokenTimeoutCallBack(procoreApiFunction: ()-> String):String

    suspend fun getTokenFromRefreshToken(loggedInProfileKeyIdentifiers: LoggedInProfileKeyIdentifiers)

}