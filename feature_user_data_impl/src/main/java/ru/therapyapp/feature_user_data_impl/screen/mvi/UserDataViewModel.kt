package ru.therapyapp.feature_user_data_impl.screen.mvi

import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_prefs.SharedPrefsRepository
import ru.therapyapp.data_core.entity.User
import ru.therapyapp.data_doctor.api.DoctorRepository
import ru.therapyapp.data_doctor.api.entity.DoctorRequestBody
import ru.therapyapp.data_mkb.MkbRepository
import ru.therapyapp.data_mkb.model.Mkb
import ru.therapyapp.data_patient.api.PatientRepository
import ru.therapyapp.data_patient.api.entity.PatientRequestBody

class UserDataViewModel(
    private val user: User?,
    private val doctorId: Int?,
    private val patientRepository: PatientRepository,
    private val doctorRepository: DoctorRepository,
    private val prefsRepository: SharedPrefsRepository,
    private val mkbRepository: MkbRepository,
) : MviViewModel<UserDataEvent, UserDataState, UserDataSideEffect>(initialState = UserDataState()) {

    override fun dispatch(event: UserDataEvent) {
        when (event) {
            UserDataEvent.FetchData -> fetchData()
            is UserDataEvent.OnDoctorDone -> onDoctorDone(event.doctorRequestBody)
            is UserDataEvent.OnPatientDone -> onPatientDone(event.patientRequestBody)
            is UserDataEvent.OnAddMkb -> addMkb(event.code, event.name)
            is UserDataEvent.OnMessageShow -> showMessage(event.message)
        }
    }

    private fun showMessage(message: String) {
        intent {
            postSideEffect(UserDataSideEffect.ShowToastMessage(message))
        }
    }

    private fun fetchData() {
        intent {
            when (val mkbResult = mkbRepository.getMkbs()) {
                is RequestResult.Error -> {
                    postSideEffect(UserDataSideEffect.ShowToastMessage(mkbResult.message ?: "Ошибка при получении МКБ-кодов"))
                    reduce {
                        state.copy(user = user, doctorId = doctorId)
                    }
                }
                is RequestResult.Success -> {
                    reduce {
                        state.copy(user = user, doctorId = doctorId, mkbs = mkbResult.data)
                    }
                }
            }
        }
    }

    private fun addMkb(mkbCode: String, mkbName: String) {
        intent {
            when (val mkbResult = mkbRepository.createMkb(Mkb(mkbName, mkbCode))) {
                is RequestResult.Error -> {
                    postSideEffect(UserDataSideEffect.ShowToastMessage(mkbResult.message ?: "Ошибка при добавлении МКБ-кода"))
                }
                is RequestResult.Success -> {
                    postSideEffect(UserDataSideEffect.ShowToastMessage("МКБ-код успешно добавлен в базу"))
                    reduce { state.copy(mkbs = mkbResult.data) }
                }
            }
        }
    }

    private fun onDoctorDone(doctorRequestBody: DoctorRequestBody) {

        intent {
            when (val doctorResult = doctorRepository.createDoctor(doctorRequestBody)) {
                is RequestResult.Success -> {
                    prefsRepository.apply {
                        userId = user?.id ?: -1
                        userType = user?.userType?.name ?: ""
                        isLoggedIn = true
                    }
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
            when (val patientResult = patientRepository.createPatientWithDoctor(doctorId ?: - 1, patientRequestBody)) {
                is RequestResult.Success -> {
                    postSideEffect(UserDataSideEffect.Finish)
                }
                is RequestResult.Error -> {
                    postSideEffect(UserDataSideEffect.ShowToastMessage(patientResult.message ?: ""))
                }
            }
        }
    }
}