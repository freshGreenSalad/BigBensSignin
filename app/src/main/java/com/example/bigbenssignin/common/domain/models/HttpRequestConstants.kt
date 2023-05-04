package com.example.bigbenssignin.common.domain.models

object HttpRequestConstants {
    const val procoreBaseUri = "https://sandbox.procore.com"
    const val returnUri = "urn:ietf:wg:oauth:2.0:oob"
    const val tokenRequest = "$procoreBaseUri/oauth/token"
    const val companyRequest = "$procoreBaseUri/rest/v1.0/companies"
    const val tokenRequestType_requestToken = "refresh_token"
    const val tokenRequestType_authorization_code = "authorization_code"
    fun getTimeCardEntriesUri(project:String):String{
        return procoreBaseUri + "/rest/v1.0/projects/${project}/timecard_entries"
    }
    fun getProjectPeopleUri(project:String):String{
        return procoreBaseUri + "/rest/v1.0/projects/${project}/people"
    }
    fun getCompanyUri(company:String):String{
        return procoreBaseUri + "/rest/v1.0/companies/${company}/projects"
    }
}