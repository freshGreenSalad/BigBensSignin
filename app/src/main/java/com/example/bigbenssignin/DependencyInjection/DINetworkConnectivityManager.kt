package com.example.bigbenssignin.DependencyInjection

import android.content.Context
import com.example.bigbenssignin.app.internetConnection.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DINetworkConnectivityManager {

    @Provides
    fun providenetworkConnectivityManager(@ApplicationContext context: Context): NetworkConnectivityObserver = NetworkConnectivityObserver(context)
}