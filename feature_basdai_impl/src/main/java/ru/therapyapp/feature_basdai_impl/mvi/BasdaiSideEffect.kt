package ru.therapyapp.feature_basdai_impl.mvi

sealed class BasdaiSideEffect {
    data class ShowToastMessage(val text: String) : BasdaiSideEffect()
    object Finish : BasdaiSideEffect()
}