package ru.therapyapp.feature_auth_impl.mvi

import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_auth.api.AuthRepository
import ru.therapyapp.data_auth.api.entity.User
import ru.therapyapp.data_auth.api.entity.UserRequestBody
import ru.therapyapp.data_auth.api.entity.UserType
import ru.therapyapp.data_doctor.api.DoctorRepository
import ru.therapyapp.data_patient.api.PatientRepository

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val doctorRepository: DoctorRepository,
    private val patientRepository: PatientRepository,
) : MviViewModel<AuthEvent, AuthState, AuthSideEffect>(initialState = AuthState()) {

    override fun dispatch(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnLoginClick -> onLogin(event.login, event.password)
            AuthEvent.OnRegisterButtonClick -> onRegisterButtonClick()
            is AuthEvent.OnRegister -> onRegister(event.login, event.password, event.userType)
        }
    }

    private fun onLogin(login: String, password: String) {
        intent {
            when (val loginResult = authRepository.auth(login, PasswordMapper.md5(password))) {
                is RequestResult.Success -> {
                    val user = loginResult.data
                    if (user.userType == UserType.PATIENT) {
                        getDoctor(user)
                    } else if (user.userType == UserType.DOCTOR) {
                        getPatient(user)
                    }
                }
                is RequestResult.Error -> {
                    postSideEffect(AuthSideEffect.ShowMessage(loginResult.message ?: ""))
                }
            }
        }
    }

    private fun getDoctor(user: User) {
        intent {
            when (val doctorResult = doctorRepository.getDoctorByUserId(user.id)) {
                is RequestResult.Success -> {
                    postSideEffect(AuthSideEffect.OpenDoctorScreen(doctorResult.data))
                }
                is RequestResult.Error -> {
                    postSideEffect(AuthSideEffect.ShowMessage(doctorResult.message ?: ""))
                    postSideEffect(AuthSideEffect.OpenUserDataScreen(user))
                }
            }
        }
    }

    private fun getPatient(user: User) {
        intent {
            when (val patientResult = patientRepository.getPatientByUserId(user.id)) {
                is RequestResult.Success -> {
                    postSideEffect(AuthSideEffect.OpenPatientScreen(patientResult.data))
                }
                is RequestResult.Error -> {
                    postSideEffect(AuthSideEffect.ShowMessage(patientResult.message ?: ""))
                    postSideEffect(AuthSideEffect.OpenUserDataScreen(user))
                }
            }
        }
    }

    private fun onRegister(login: String, password: String, userType: UserType) {
        intent {
            val userResult = authRepository.register(
                UserRequestBody(login, PasswordMapper.md5(password), userType)
            )

            when (userResult) {
                is RequestResult.Error -> {
                    postSideEffect(AuthSideEffect.ShowMessage(userResult.message ?: ""))
                }
                is RequestResult.Success -> {
                    postSideEffect(AuthSideEffect.OpenUserDataScreen(userResult.data))
                }
            }

        }
    }

    private fun onRegisterButtonClick() {
        intent {
            postSideEffect(AuthSideEffect.ShowRegisterView)
        }
    }
}