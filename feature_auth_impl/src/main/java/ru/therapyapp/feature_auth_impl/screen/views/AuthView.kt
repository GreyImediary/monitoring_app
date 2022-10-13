package ru.therapyapp.feature_auth_impl.screen.views

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.get
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.core_ui.AppOutlinedButton
import ru.therapyapp.core_ui.R
import ru.therapyapp.core_ui.textFieldColors
import ru.therapyapp.feature_auth_impl.mvi.AuthEvent
import ru.therapyapp.feature_auth_impl.mvi.AuthSideEffect
import ru.therapyapp.feature_auth_impl.mvi.AuthViewModel
import ru.therapyapp.feature_auth_impl.screen.configs.getButtonsHorizontalPadding
import ru.therapyapp.feature_auth_impl.screen.configs.getScreenHorizontalPadding
import ru.therapyapp.feature_doctor_screen_api.DoctorScreenRouter
import ru.therapyapp.feature_patient_screen_api.PatientScreenRouter
import ru.therapyapp.feature_user_data_api.UserDataRouter


@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onEvent: (AuthEvent) -> Unit,
    userDataRouter: UserDataRouter = get(),
    doctorScreenRouter: DoctorScreenRouter = get(),
    patientScreenRouter: PatientScreenRouter = get(),
) {
    val navController = rememberNavController()
    val activity = LocalContext.current as AppCompatActivity

    NavHost(navController = navController, startDestination = "auth") {
        composable("auth") { AuthView(onEvent) }
        composable("register") { RegisterView(onEvent) }
    }

    viewModel.collectSideEffect(sideEffect = {
        handleSideEffect(
            it,
            navController,
            userDataRouter,
            doctorScreenRouter,
            patientScreenRouter,
            activity
        )
    })
}

private fun handleSideEffect(
    sideEffect: AuthSideEffect,
    navController: NavController,
    userDataRouter: UserDataRouter,
    doctorScreenRouter: DoctorScreenRouter,
    patientScreenRouter: PatientScreenRouter,
    activity: AppCompatActivity,
) {
    when (sideEffect) {
        AuthSideEffect.ShowRegisterView -> {
            navController.navigate("register")
        }
        is AuthSideEffect.ShowMessage -> {
            Toast.makeText(activity, sideEffect.text, Toast.LENGTH_SHORT).show()
        }
        is AuthSideEffect.OpenUserDataScreen -> {
            userDataRouter.openUserDataScreen(activity, sideEffect.user)
        }
        is AuthSideEffect.OpenDoctorScreen -> {
            doctorScreenRouter.openDoctorScreen(activity, sideEffect.doctor)
        }
        is AuthSideEffect.OpenPatientScreen -> {
            patientScreenRouter.openPatientScreen(activity, sideEffect.patient)
        }
    }
}

@Composable
fun AuthView(
    onEvent: (AuthEvent) -> Unit
) {
    val login = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    val isLoginError = rememberSaveable { mutableStateOf(false) }
    val isPasswordError = rememberSaveable { mutableStateOf(false) }

    val isPasswordVisible = rememberSaveable { mutableStateOf(false) }

    val focus = LocalFocusManager.current

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.main_background_color))
    ) {
        val horizontalPadding = getScreenHorizontalPadding(maxWidth)
        val buttonPadding = getButtonsHorizontalPadding(maxWidth)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //login
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = login.value,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focus.moveFocus(FocusDirection.Down)
                        }
                    ),
                    isError = isLoginError.value,
                    label = { Text(text = stringResource(id = R.string.auth_screen_login)) },
                    onValueChange = { login.value = it },
                    colors = textFieldColors(),
                    trailingIcon = {
                        IconButton(onClick = { login.value = "" }) {
                            Icon(Icons.Outlined.Cancel, contentDescription = "")
                        }
                    }
                )

                if (isLoginError.value) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        text = "Введите логин",
                    )
                }
            }

            //password
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = password.value,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (isFieldsValid(
                                    login.value,
                                    isLoginError,
                                    password.value,
                                    isPasswordError
                                )
                            ) {
                                onEvent(AuthEvent.OnLoginClick(login.value, password.value))
                            }
                        }
                    ),
                    visualTransformation = if (isPasswordVisible.value) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    label = { Text(text = stringResource(id = R.string.auth_screen_password)) },
                    onValueChange = { password.value = it },
                    colors = textFieldColors(),
                    isError = isPasswordError.value,
                    trailingIcon = {
                        Row {
                            IconButton(
                                onClick = {
                                    isPasswordVisible.value = !isPasswordVisible.value
                                }
                            ) {
                                Icon(
                                    if (isPasswordVisible.value) {
                                        Icons.Outlined.Visibility
                                    } else {
                                        Icons.Outlined.VisibilityOff
                                    },
                                    contentDescription = ""
                                )
                            }

                            IconButton(
                                onClick = {
                                    password.value = ""
                                }
                            ) {
                                Icon(Icons.Outlined.Cancel, contentDescription = "")
                            }
                        }
                    },
                )

                if (isPasswordError.value) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        text = "Введите пароль",
                    )
                }
            }

            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = buttonPadding, end = buttonPadding),
                onClick = {
                    if (isFieldsValid(
                            login.value,
                            isLoginError,
                            password.value,
                            isPasswordError
                        )
                    ) {
                        onEvent(AuthEvent.OnLoginClick(login.value, password.value))
                    }
                }
            ) {
                Text(
                    text = "Войти",
                    style = TextStyle(color = colorResource(id = R.color.color_white))
                )
            }


            AppOutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = buttonPadding, end = buttonPadding),
                onClick = { onEvent(AuthEvent.OnRegisterButtonClick)}
            ) {
                Text(
                    text = "Регистрация",
                    style = TextStyle(color = colorResource(id = R.color.secondary))
                )
            }
        }
    }
}

private fun isFieldsValid(
    loginText: String,
    loginErrorState: MutableState<Boolean>,
    passwordText: String,
    passwordErrorState: MutableState<Boolean>,
): Boolean {
    loginErrorState.value = loginText.isEmpty()

    passwordErrorState.value = passwordText.isEmpty()

    return !loginErrorState.value && !passwordErrorState.value
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PhonePreview() {
    AuthView(onEvent = {})
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.TABLET)
@Composable
private fun TabletPreview() {
    AuthView(onEvent = {})
}