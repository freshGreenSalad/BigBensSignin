package com.example.bigbenssignin.features.scaffoldDrawer.presentation.viewModel

sealed interface OnEventScaffoldViewModel {

    object DeleteEverythingDatastore:OnEventScaffoldViewModel
    object SubmitAllTimeSheets:OnEventScaffoldViewModel

}