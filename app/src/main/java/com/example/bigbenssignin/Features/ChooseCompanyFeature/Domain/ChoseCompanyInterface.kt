package com.example.bigbenssignin.Features.ChooseCompanyFeature.Domain

import com.example.bigbenssignin.ChooseCompanyFeature.ListOfCompaniesItem

interface choseCompanyInterface {
    suspend fun getcompanys():List<ListOfCompaniesItem>

    suspend fun getprojects():String
}