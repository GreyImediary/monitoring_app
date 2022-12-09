package ru.therapyapp.feature_bvas_impl.mvi

sealed class BvasSideEffect {
    data class ShowToastMessage(val text: String) : BvasSideEffect()
    object Finish : BvasSideEffect()
}