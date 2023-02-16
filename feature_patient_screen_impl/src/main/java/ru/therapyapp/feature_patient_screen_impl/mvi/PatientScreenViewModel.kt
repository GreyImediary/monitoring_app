package ru.therapyapp.feature_patient_screen_impl.mvi

import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_prefs.SharedPrefsRepository
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_questionnaire.QuestionnaireRepository
import ru.therapyapp.data_request.api.RequestRepository
import ru.therapyapp.data_request.api.entity.RequestUpdateBody

class PatientScreenViewModel(
    private val patient: Patient?,
    private val requestRepository: RequestRepository,
    private val questionnaireRepository: QuestionnaireRepository,
    private val sharedPrefRepository: SharedPrefsRepository,
) : MviViewModel<PatientScreenEvent, PatientScreenState, PatientScreenSideEffect>
    (initialState = PatientScreenState(patient = patient)) {

    override fun dispatch(event: PatientScreenEvent) {
        when (event) {
            is PatientScreenEvent.OnUpdateRequest -> updateRequest(event.updateRequestBody)
            PatientScreenEvent.FetchData -> fetchData()
            PatientScreenEvent.OnBasdaiClick -> openBasdai()
            PatientScreenEvent.OnAsdasClick -> openAsdas()
            PatientScreenEvent.OnBvasClick -> openBvas()
            is PatientScreenEvent.OnQuestionnaireClick -> openQuestionnaire(event.questionnaireId)
            PatientScreenEvent.Logout -> logout()
            PatientScreenEvent.OnSledaiClick -> openSledai()
        }
    }

    private fun logout() {
        intent {
            postSideEffect(PatientScreenSideEffect.Finish)
        }
    }

    private fun openQuestionnaire(questionnaireId: Int) {
        intent {
            postSideEffect(PatientScreenSideEffect.OpenQuestionnaire(questionnaireId, state.patient?.id ?: -1))
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
                    fetchQuestionnaires()
                }
            }
        }
    }

    private fun fetchQuestionnaires() {
        intent {
            reduce { state.copy(isRefreshing = true) }
            when (val result = questionnaireRepository.getQuestionnairesByPatient(state.patient?.id ?: -1)) {
                is RequestResult.Error -> {
                    postSideEffect(PatientScreenSideEffect.ShowToast(result.message ?: "Не удалось получить анкеты"))
                    reduce { state.copy(isRefreshing = false) }
                }
                is RequestResult.Success -> {
                    reduce { state.copy(questionnaires = result.data, isRefreshing = false) }
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

    private fun openSledai() {
        intent {
            postSideEffect(PatientScreenSideEffect.OpenSelenaSledaiScreen)
        }
    }

    private fun openBasdai() {
        intent {
            postSideEffect(PatientScreenSideEffect.OpenBasdaiScreen)
        }
    }

    private fun openAsdas() {
        intent {
            postSideEffect(PatientScreenSideEffect.OpenAsdasScreen)
        }
    }

    private fun openBvas() {
        intent {
            postSideEffect(PatientScreenSideEffect.OpenBvasScreen)
        }
    }
}