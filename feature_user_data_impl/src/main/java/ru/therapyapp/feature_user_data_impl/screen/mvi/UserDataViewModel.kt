package ru.therapyapp.feature_user_data_impl.screen.mvi

import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_auth.api.entity.User
import ru.therapyapp.data_doctor.api.DoctorRepository
import ru.therapyapp.data_doctor.api.entity.DoctorRequestBody
import ru.therapyapp.data_patient.api.PatientRepository
import ru.therapyapp.data_patient.api.entity.PatientRequestBody

class UserDataViewModel(
    private val user: User,
    private val patientRepository: PatientRepository,
    private val doctorRepository: DoctorRepository
) : MviViewModel<UserDataEvent, UserDataState, UserDataSideEffect>(initialState = UserDataState()) {

    override fun dispatch(event: UserDataEvent) {
        when (event) {
            UserDataEvent.FetchData -> fetchData()
            is UserDataEvent.OnDoctorDone -> onDoctorDone(event.doctorRequestBody)
            is UserDataEvent.OnPatientDone -> onPatientDone(event.patientRequestBody)
        }
    }

    private fun fetchData() {
        intent {
            reduce {
                state.copy(user = user)
            }
        }
    }

    private fun onDoctorDone(doctorRequestBody: DoctorRequestBody) {

        intent {
            when (val doctorResult = doctorRepository.createDoctor(doctorRequestBody)) {
                is RequestResult.Success -> {
                    postSideEffect(UserDataSideEffect.OpenDoctorScreen(doctorResult.data))
                }
                is RequestResult.Error -> {
                    postSideEffect(UserDataSideEffect.ShowToastMessage(doctorResult.message ?: ""))
                }
            }
        }
    }

    private fun onPatientDone(patientRequestBody: PatientRequestBody) {

        intent {
            when (val patientResult = patientRepository.createPatient(patientRequestBody)) {
                is RequestResult.Success -> {
                    postSideEffect(UserDataSideEffect.OpenPatientScreen(patientResult.data))
                }
                is RequestResult.Error -> {
                    postSideEffect(UserDataSideEffect.ShowToastMessage(patientResult.message ?: ""))
                }
            }
        }
    }
}