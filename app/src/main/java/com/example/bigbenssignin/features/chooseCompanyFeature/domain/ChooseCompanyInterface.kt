package com.example.bigbenssignin.features.chooseCompanyFeature.domain

import com.example.bigbenssignin.common.Domain.models.SuccessState
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Companies

interface ChooseCompanyRepositoryInterface {
    suspend fun getListOfCompanies(): SuccessState<List<Companies>>

    suspend fun getListOfProjects():SuccessState<String>
}