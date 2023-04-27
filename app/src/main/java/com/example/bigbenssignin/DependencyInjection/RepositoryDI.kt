package com.example.bigbenssignin.DependencyInjection

import com.example.bigbenssignin.Features.ChooseCompanyFeature.data.ChoseCompanyImplementation
import com.example.bigbenssignin.Features.ChooseCompanyFeature.Domain.choseCompanyInterface
import com.example.bigbenssignin.Features.LoginToProcoreFeature.Domain.signinInterface
import com.example.bigbenssignin.Features.LoginToProcoreFeature.Data.signinRepo
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
    fun provideChooseCompanyProjectRepository(signinRepo: ChoseCompanyImplementation): choseCompanyInterface

}