package com.example.bigbenssignin.features.chooseCompanyFeature.domain

import com.example.bigbenssignin.ChooseCompanyFeature.ListOfCompaniesItem

interface choseCompanyInterface {
    suspend fun getcompanys():List<ListOfCompaniesItem>

    suspend fun getprojects():String
}