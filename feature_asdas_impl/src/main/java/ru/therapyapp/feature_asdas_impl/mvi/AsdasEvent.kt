package ru.therapyapp.feature_asdas_impl.mvi

import ru.therapyapp.data_asdas.model.SrbSoeType

sealed class AsdasEvent {
    data class OnSendAnswer(val questionNumber: QuestionNumber, val value: Int) : AsdasEvent()
    data class OnChangeSrbSoeType(val currentType: SrbSoeType) : AsdasEvent()
    data class OnSendSrbSoeValue(val value: Double) : AsdasEvent()
    object OnSendIndex : AsdasEvent()
}