package ru.therapyapp.feature_patient_screen_impl.mvi

import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_request.api.RequestRepository
import ru.therapyapp.data_request.api.entity.RequestUpdateBody

class PatientScreenViewModel(
    private val patient: Patient?,
    private val requestRepository: RequestRepository,
) : MviViewModel<PatientScreenEvent, PatientScreenState, PatientScreenSideEffect>
    (initialState = PatientScreenState(patient = patient)) {

    override fun dispatch(event: PatientScreenEvent) {
        when (event) {
            PatientScreenEvent.FetchData -> fetchData()
            is PatientScreenEvent.OnUpdateRequest -> updateRequest(event.updateRequestBody)
            PatientScreenEvent.OnBasdaiClick -> openBasdai()
        }
    }

    private fun fetchData() {
        intent {
            reduce { state.copy(isRefreshing = true) }
            when (val result  = requestRepository.getPatientRequests(patient?.id ?: - 1)) {
                is RequestResult.Error -> {
                    postSideEffect(PatientScreenSideEffect.ShowToast(result.message ?: ""))
                    reduce { state.copy(isRefreshing = false) }
                }
                is RequestResult.Success -> {
                    reduce { state.copy(requests = result.data, isRefreshing = false) }
                }
            }
        }
    }

    private fun updateRequest(requestUpdateBody: RequestUpdateBody) {
        intent {
            reduce { state.copy(isRefreshing = true) }
            when (val result = requestRepository.updateRequest(requestUpdateBody)) {
                is RequestResult.Error -> {
                    postSideEffect(PatientScreenSideEffect.ShowToast(result.message ?: ""))
                    reduce { state.copy(isRefreshing = false) }
                }
                is RequestResult.Success -> {
                    reduce { state.copy(requests = result.data.filter {
                        it.patient.id == (patient?.id ?: -1)
                    }, isRefreshing = false) }
                }
            }
        }
    }

    private fun openBasdai() {
        intent {
            postSideEffect(PatientScreenSideEffect.OpenBasdaiScreen)
        }
    }
}