package com.example.bigbenssignin.di

import com.example.bigbenssignin.chooseCompany.ChoseCompanyImplementation
import com.example.bigbenssignin.chooseCompany.choseCompanyInterface
import com.example.bigbenssignin.sharedUi.signinRepo.signinInterface
import com.example.bigbenssignin.sharedUi.signinRepo.signinRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryDI {

    @Binds
    fun provideSignInRepository(signinRepo: signinRepo): signinInterface

    @Binds
    fun provideSignInRepository(signinRepo: ChoseCompanyImplementation): choseCompanyInterface

}