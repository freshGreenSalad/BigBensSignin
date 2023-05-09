package com.example.bigbenssignin.dependencyInjection

import com.example.bigbenssignin.features.chooseCompanyFeature.data.ChooseCompanyImplementation
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.ChooseCompanyRepositoryInterface
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.SigninInterface
import com.example.bigbenssignin.features.loginToProcoreFeature.data.SigninRepository
import com.example.bigbenssignin.features.scaffoldDrawer.data.ScaffoldDrawerRepoImp
import com.example.bigbenssignin.features.scaffoldDrawer.domain.ScaffoldDrawerRepo
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

    @Binds
    fun provideScaffoldDrawerRepository(scaffoldDrawerRepoImp: ScaffoldDrawerRepoImp): ScaffoldDrawerRepo

}