package ru.therapyapp.feature_sledai_impl.mvi

sealed class SelenaSledaiEvent {
    data class OnAddSledai(val answer: String, val point: Int) : SelenaSledaiEvent()
    data class OnRemoveSledai(val answer: String, val point: Int) : SelenaSledaiEvent()
    object OnSendIndex : SelenaSledaiEvent()
}