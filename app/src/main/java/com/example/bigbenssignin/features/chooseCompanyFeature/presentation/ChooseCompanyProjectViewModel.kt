package com.example.bigbenssignin.ChooseCompanyFeature

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.choseCompanyInterface
import com.example.bigbenssignin.features.chooseCompanyFeature.presentation.ChooseCompanyProjectEvent
import com.example.bigbenssignin.DependencyInjection.IoDispatcher
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

    val companysList = mutableStateOf<List<ListOfCompaniesItem>>(emptyList())

    init {
        scope.launch(dispatcher) {
            companysList.value = choseCompanyProjectRepository.getcompanys()
        }
    }

    fun choseCompanyProject(event: ChooseCompanyProjectEvent){
        when(event){
            is ChooseCompanyProjectEvent.choseCompany -> {}
            is ChooseCompanyProjectEvent.choseProject -> {}
        }
    }
}
@kotlinx.serialization.Serializable
data class ListOfCompaniesItem(
    val id: Int,
    val is_active: Boolean,
    val logo_url: String,
    val name: String,
    val pcn_business_experience: Boolean?
)