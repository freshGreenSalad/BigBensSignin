package com.example.bigbenssignin.features.chooseCompanyFeature.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.common.data.DataStoreFunctions
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.ChooseCompanyRepositoryInterface
import com.example.bigbenssignin.dependencyInjection.IoDispatcher
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Companies
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseCompanyProjectViewModel @Inject constructor(
    private val choseCompanyProjectRepository: ChooseCompanyRepositoryInterface,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val dataStoreImp: DataStoreFunctions,
):ViewModel(

) {
    private val scope = viewModelScope

    private val _uiChannel = Channel<SuccessState<Unit>>()
    val uiChannel = _uiChannel.receiveAsFlow()

    val companiesList = mutableStateOf<List<Companies>>(emptyList())
    val projectsList = mutableStateOf<List<Project>>(emptyList())
    private val selectedProject = mutableStateOf("")

    init {
        choseCompanyProject(ChooseCompanyProjectEvent.GetCompanies)
    }

    fun choseCompanyProject(event: ChooseCompanyProjectEvent){
        when(event){
            is ChooseCompanyProjectEvent.ChooseProject -> {
                scope.launch(dispatcher) {
                    dataStoreImp.addProjectToDataStore(event.project)
                    _uiChannel.send(SuccessState.Success())
                }
            }
            is ChooseCompanyProjectEvent.GetListOfProjects -> {
                Log.d("","get list of projects ")
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
                    }
                }
            }
            is ChooseCompanyProjectEvent.GetCompanies -> {
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
