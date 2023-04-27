package com.example.bigbenssignin.sharedPreferances

interface DatastoreInterface {
    suspend fun deleteAccount()

    suspend fun read(key: String): String?

    suspend fun edit(key:String, value:String)

    suspend fun save(key: String, value: String)
}