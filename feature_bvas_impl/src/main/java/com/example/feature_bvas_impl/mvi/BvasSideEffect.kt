package com.example.feature_bvas_impl.mvi

import com.example.data_bvas.model.BvasIndex

sealed class BvasSideEffect {
    data class ShowToastMessage(val text: String) : BvasSideEffect()
    object Finish : BvasSideEffect()
}