package ru.therapyapp.feature_sledai_impl.mvi

sealed class SelenaSledaiSideEffect {
    data class ShowToastMessage(val text: String) : SelenaSledaiSideEffect()
    object Finish : SelenaSledaiSideEffect()
}