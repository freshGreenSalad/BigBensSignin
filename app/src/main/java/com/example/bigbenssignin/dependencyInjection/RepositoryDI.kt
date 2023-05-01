package com.example.bigbenssignin.dependencyInjection

import com.example.bigbenssignin.features.chooseCompanyFeature.data.ChooseCompanyImplementation
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.ChooseCompanyRepositoryInterface
import com.example.bigbenssignin.features.chooseCompanyFeature.loginToProcoreFeature.domain.SigninInterface
import com.example.bigbenssignin.features.chooseCompanyFeature.loginToProcoreFeature.data.SigninRepository
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
    fun provideChooseCompanyProjectRepository(signinRepo: ChooseCompanyImplementation): ChooseCompanyRepositoryInterface

}