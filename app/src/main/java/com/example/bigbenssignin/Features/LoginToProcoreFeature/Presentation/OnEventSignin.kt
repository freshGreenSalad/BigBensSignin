package com.example.bigbenssignin.loginui

import java.io.IOException

sealed interface onEvent{
    object gettokin: onEvent
    data class updateAuthorisationCode(val AuthorisationCode: String): onEvent
}

