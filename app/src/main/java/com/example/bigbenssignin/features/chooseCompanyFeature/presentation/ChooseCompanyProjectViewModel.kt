package com.example.bigbenssignin.features.chooseCompanyFeature.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.ChooseCompanyRepositoryInterface
import com.example.bigbenssignin.dependencyInjection.IoDispatcher
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Companies
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Project
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
    val projectsList = mutableStateOf<List<Project>>(emptyList())
    val selectedProject = mutableStateOf("")

    init {
        choseCompanyProject(ChooseCompanyProjectEvent.getCompanies)
    }

    fun choseCompanyProject(event: ChooseCompanyProjectEvent){
        when(event){
            is ChooseCompanyProjectEvent.ChooseCompany -> {}
            is ChooseCompanyProjectEvent.GetListOfProjects -> {
                Log.d("","getlist of projects ")
                scope.launch(dispatcher) {
                    selectedProject.value = event.company
                    when(val companiesResult = choseCompanyProjectRepository.getListOfProjects(selectedProject.value)){
                        is SuccessState.Failure -> {
                            Log.d("", "view model fail block")
                            projectsList.value = emptyList()
                        }
                        is SuccessState.Success -> {
                            projectsList.value = companiesResult.data ?:  emptyList()
                        }
                        else -> {}
                    }
                }
            }
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
                        else -> {}
                    }
                }
            }
        }
    }
}
