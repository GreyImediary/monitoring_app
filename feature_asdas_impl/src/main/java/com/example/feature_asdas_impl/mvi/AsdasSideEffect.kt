package com.example.feature_asdas_impl.mvi

sealed class AsdasSideEffect {
    data class ShowToastMessage(val text: String) : AsdasSideEffect()
    object Finish : AsdasSideEffect()
}