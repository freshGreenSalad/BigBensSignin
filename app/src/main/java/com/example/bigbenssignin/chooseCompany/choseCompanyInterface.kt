package com.example.bigbenssignin.chooseCompany

interface choseCompanyInterface {
    suspend fun getcompanys():ListOfCompaniesItem

    suspend fun getprojects():String
}