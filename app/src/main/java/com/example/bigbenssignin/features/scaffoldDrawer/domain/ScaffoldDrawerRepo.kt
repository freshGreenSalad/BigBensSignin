package com.example.bigbenssignin.features.scaffoldDrawer.domain

import com.example.bigbenssignin.common.domain.SuccessState

interface ScaffoldDrawerRepo {
    suspend fun submitAllTimeSheets(): SuccessState<Unit>
}