package com.example.bigbenssignin.DependencyInjection

import com.example.bigbenssignin.features.chooseCompanyFeature.data.ChooseCompanyImplementation
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.choseCompanyInterface
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.SigninInterface
import com.example.bigbenssignin.features.loginToProcoreFeature.data.SigninRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryDI {

    @Binds
    fun provideSignInRepository(signinRepo: SigninRepository): SigninInterface

    @Binds
    fun provideChooseCompanyProjectRepository(signinRepo: ChooseCompanyImplementation): choseCompanyInterface

}