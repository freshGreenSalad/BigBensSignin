package com.example.bigbenssignin.di

import com.example.bigbenssignin.signinRepo.signinInterface
import com.example.bigbenssignin.signinRepo.signinRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryDI {

    @Binds
    fun provideSignInRepository(signinRepo: signinRepo): signinInterface

}