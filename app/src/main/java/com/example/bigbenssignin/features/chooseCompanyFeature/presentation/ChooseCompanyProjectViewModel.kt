package com.example.bigbenssignin.features.chooseCompanyFeature.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.common.Domain.models.SuccessState
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.ChooseCompanyRepositoryInterface
import com.example.bigbenssignin.dependencyInjection.IoDispatcher
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Companies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseCompanyProjectViewModel @Inject constructor(
    private val choseCompanyProjectRepository: ChooseCompanyRepositoryInterface,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
):ViewModel(

) {
    private val scope = viewModelScope

    val companiesList = mutableStateOf<List<Companies>>(emptyList())

    fun choseCompanyProject(event: ChooseCompanyProjectEvent){
        when(event){
            is ChooseCompanyProjectEvent.ChooseCompany -> {}
            is ChooseCompanyProjectEvent.ChooseProject -> {}
            is ChooseCompanyProjectEvent.getCompanies -> {
                scope.launch(dispatcher) {
                    when(val companiesResult = choseCompanyProjectRepository.getListOfCompanies()){
                            is SuccessState.Failure -> {
                                Log.d("", "view model fail block")
                                companiesList.value = emptyList()
                            }
                            is SuccessState.Success -> {
                                companiesList.value = companiesResult.data ?:  emptyList()
                            }
                        }
                }
            }
        }
    }
}
