package com.example.bigbenssignin.common.Domain.models

sealed class SuccessState<T> (val data: T? = null, val error: String? = null){
    class Failure<T>(error: String? = null): SuccessState<T>(error = error)
    class Success<T>(data: T? = null): SuccessState<T>(data = data)
}

