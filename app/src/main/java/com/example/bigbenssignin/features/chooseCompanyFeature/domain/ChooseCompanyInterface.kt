package com.example.bigbenssignin.features.chooseCompanyFeature.domain

import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Companies
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Project

interface ChooseCompanyRepositoryInterface {
    suspend fun getListOfCompanies(): SuccessState<List<Companies>>

    suspend fun getListOfProjects(company:String): SuccessState<List<Project>>
}