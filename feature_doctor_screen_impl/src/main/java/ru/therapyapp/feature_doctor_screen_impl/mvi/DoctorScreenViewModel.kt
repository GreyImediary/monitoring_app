package ru.therapyapp.feature_doctor_screen_impl.mvi

import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_prefs.SharedPrefsRepository
import ru.therapyapp.data_doctor.api.DoctorRepository
import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.data_patient.api.PatientRepository
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_questionnaire.QuestionnaireRepository
import ru.therapyapp.data_request.api.RequestRepository
import ru.therapyapp.data_request.api.entity.RequestCreationBody

class DoctorScreenViewModel(
    private val doctor: Doctor?,
    private val requestRepository: RequestRepository,
    private val patientRepository: PatientRepository,
    private val doctorRepository: DoctorRepository,
    private val questionnaireRepository: QuestionnaireRepository,
    private val sharedPrefsRepository: SharedPrefsRepository,
) : MviViewModel<DoctorScreenEvent, DoctorScreenState, DoctorScreenSideEffect>
    (initialState = DoctorScreenState(doctor)) {

    override fun dispatch(event: DoctorScreenEvent) {
        when (event) {
            DoctorScreenEvent.FetchData -> fetchData()
            is DoctorScreenEvent.CreateRequest -> createRequest(event.requestCreationBody)
            is DoctorScreenEvent.DeleteRequest -> deleteRequest(event.requestId)
            is DoctorScreenEvent.OnPatientClick -> openPatientScreen((event.patient))
            DoctorScreenEvent.OnRequestAddClick -> openCreateRequestDialog()
            DoctorScreenEvent.OnRequestDialogDismissClick -> dismissCreateRequestDialog()
            DoctorScreenEvent.OnQuestionnaireAddClick -> openQuestionnaireAddScreen()
            is DoctorScreenEvent.OnQuestionnaireClick -> openQuestionnaireAnsweredScreen(event.questionnaireId)
            DoctorScreenEvent.Logout -> logout()
            is DoctorScreenEvent.OpenPatientCreateScreen -> openCreatePatientScreen(event.doctorId)
            is DoctorScreenEvent.OnPatientAppScreenClick -> openPatientAppScreen(event.patient)
        }
    }

    private fun openPatientAppScreen(patient: Patient) {
        intent {
            postSideEffect(DoctorScreenSideEffect.OpenPatientAppScreen(patient))
        }
    }

    private fun openCreatePatientScreen(doctorId: Int) {
        intent {
            postSideEffect(DoctorScreenSideEffect.OpenPatientCreateScreen(doctorId))
        }
    }

    private fun logout() {
        intent {
            sharedPrefsRepository.clearAll()
            postSideEffect(DoctorScreenSideEffect.ShowAuthScreen)
        }
    }

    private fun openQuestionnaireAnsweredScreen(questionnaireId: Int) {
        intent {
            postSideEffect(DoctorScreenSideEffect.OpenAnsweredQuestionnaireScreen(questionnaireId))
        }
    }

    private fun openQuestionnaireAddScreen() {
        intent {
            postSideEffect(
                DoctorScreenSideEffect.OpenQuestionnaireAddScreen(
                    patients = state.doctor?.patients ?: emptyList(),
                    doctorId = state.doctor?.id ?: -1
                )
            )
        }
    }

    private fun fetchData() {
        intent {
            reduce { state.copy(isRefreshing = true) }
            val requestsResult = requestRepository.getDoctorRequests(state.doctor?.id ?: -1)
            val patientsResult = patientRepository.getPatients()
            val questionnairesResult = questionnaireRepository.getQuestionnairesByDoctor(doctor?.id ?: -1)

            when {
                requestsResult is RequestResult.Error -> {
                    reduce { state.copy(isRefreshing = false) }
                    postSideEffect(DoctorScreenSideEffect.ShowToast(requestsResult.message ?: ""))
                }
                patientsResult is RequestResult.Error -> {
                    reduce { state.copy(isRefreshing = false) }
                    postSideEffect(DoctorScreenSideEffect.ShowToast(patientsResult.message ?: ""))
                }

                questionnairesResult is RequestResult.Error -> {
                    reduce { state.copy(isRefreshing = false) }
                    postSideEffect(DoctorScreenSideEffect.ShowToast(questionnairesResult.message ?: ""))
                }
                requestsResult is RequestResult.Success &&
                        patientsResult is RequestResult.Success &&
                        questionnairesResult is RequestResult.Success -> {
                    reduce {
                        state.copy(
                            requests = requestsResult.data,
                            allPatients = patientsResult.data,
                            questionnaires = questionnairesResult.data,
                            isRefreshing = false
                        )
                    }
                }
            }

            updateDoctor()
        }
    }

    private fun updateDoctor() {
        intent {
            when (val result = doctorRepository.getDoctorByUserId(doctor?.userId ?: -1)) {
                is RequestResult.Error -> {
                    postSideEffect(DoctorScreenSideEffect.ShowToast(result.message ?: "Не получилось обновить данные доктора"))
                }
                is RequestResult.Success -> {
                    reduce { state.copy(doctor = result.data) }
                }
            }
        }
    }

    private fun createRequest(requestBody: RequestCreationBody) {
        intent {
            when (val result = requestRepository.createRequest(requestBody)) {
                is RequestResult.Error -> {
                    postSideEffect(DoctorScreenSideEffect.ShowToast(result.message ?: ""))
                }
                is RequestResult.Success -> {
                    reduce {
                        state.copy(requests = result.data)
                    }
                    postSideEffect(DoctorScreenSideEffect.ShowToast("Заявка создана и отправлена пациенту"))
                }
            }
        }
    }

    private fun deleteRequest(requestId: Int) {
        intent {
            reduce { state.copy(isRefreshing = true) }
            when (val result = requestRepository.deleteRequest(requestId)) {
                is RequestResult.Error -> {
                    postSideEffect(DoctorScreenSideEffect.ShowToast(result.message ?: ""))
                    reduce { state.copy(isRefreshing = false) }
                }
                is RequestResult.Success -> {
                    reduce { state.copy(requests = result.data.filter {
                        it.doctor.id == (doctor?.id ?: -1)
                    }, isRefreshing = false) }
                }
            }
        }
    }

    private fun openPatientScreen(patient: Patient) {
        intent {
            postSideEffect(DoctorScreenSideEffect.OpenPatientDataScreen(patient, doctor?.id ?: -1))
        }
    }

    private fun openCreateRequestDialog() {
        intent {
            reduce { state.copy(isRequestCreationDialogOpened = true) }
        }
    }

    private fun dismissCreateRequestDialog() {
        intent {
            reduce { state.copy(isRequestCreationDialogOpened = false) }
        }
    }
}