package com.example.bigbenssignin

sealed class SuccessState<T> (val data: T? = null){
    class Failure<T>: SuccessState<T>()
    class Success<T>(data: T? = null): SuccessState<T>(data)
}

