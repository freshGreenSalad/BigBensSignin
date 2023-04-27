package com.example.bigbenssignin.DependencyInjection

import com.example.bigbenssignin.features.chooseCompanyFeature.data.ChooseCompanyImplementation
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.choseCompanyInterface
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.SigninInterface
import com.example.bigbenssignin.features.loginToProcoreFeature.data.signinRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryDI {

    @Binds
    fun provideSignInRepository(signinRepo: signinRepo): SigninInterface

    @Binds
    fun provideChooseCompanyProjectRepository(signinRepo: ChooseCompanyImplementation): choseCompanyInterface

}