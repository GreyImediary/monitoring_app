package com.example.feature_asdas_impl.mvi

import com.example.data_asdas.model.SrbSoeType

sealed class AsdasEvent {
    data class OnSendAnswer(val questionNumber: QuestionNumber, val value: Int) : AsdasEvent()
    data class OnChangeSrbSoeType(val currentType: SrbSoeType) : AsdasEvent()
    data class OnSendSrbSoeValue(val value: Double) : AsdasEvent()
    object OnSendIndex : AsdasEvent()
}