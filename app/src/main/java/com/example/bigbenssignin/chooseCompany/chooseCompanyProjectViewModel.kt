package com.example.bigbenssignin.chooseCompany

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class chooseCompanyProjectViewModel @Inject constructor(
    private val choseCompanyProjectRepository: choseCompanyInterface,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
):ViewModel(

) {
    val scope = viewModelScope

    val companysList = mutableStateListOf<ListOfCompaniesItem>()

    init {
        scope.launch(dispatcher) {
            companysList.add(choseCompanyProjectRepository.getcompanys())
        }
    }

    fun choseCompanyProject(event: ChoseCompanyProjectEvent){
        when(event){
            is ChoseCompanyProjectEvent.choseCompany -> {}
            is ChoseCompanyProjectEvent.choseProject -> {}
        }
    }
}

data class ListOfCompaniesItem(
    val id: Int,
    val is_active: Boolean,
    val logo_url: String,
    val name: String,
    val pcn_business_experience: Any
)