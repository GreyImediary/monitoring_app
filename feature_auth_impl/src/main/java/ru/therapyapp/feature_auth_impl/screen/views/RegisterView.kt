package ru.therapyapp.feature_auth_impl.screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.core_ui.R
import ru.therapyapp.core_ui.textFieldColors
import ru.therapyapp.data_core.entity.UserType
import ru.therapyapp.feature_auth_impl.mvi.AuthEvent
import ru.therapyapp.feature_auth_impl.screen.configs.getButtonsHorizontalPadding
import ru.therapyapp.feature_auth_impl.screen.configs.getScreenHorizontalPadding

@Composable
fun RegisterView(
    onEvent: (AuthEvent) -> Unit,
) {
    val login = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val confirmPassword = rememberSaveable { mutableStateOf("") }

    val isLoginError = rememberSaveable { mutableStateOf(false) }
    val isPasswordError = rememberSaveable { mutableStateOf(false) }
    val isConfirmPasswordError = rememberSaveable { mutableStateOf(false) }

    val isPasswordVisible = rememberSaveable { mutableStateOf(false) }
    val isConfirmPasswordVisible = rememberSaveable { mutableStateOf(false) }

    val focus = LocalFocusManager.current

    val selectedRole = rememberSaveable { mutableStateOf(UserType.DOCTOR) }
    val isSelectedItem: (UserType) -> Boolean = { selectedRole.value == it }
    val onChangeState: (UserType) -> Unit = { selectedRole.value = it }

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
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Регистрация",
                style = TextStyle(
                    color = colorResource(id = R.color.font_color),
                    fontWeight = FontWeight.W700,
                    fontSize = 36.sp
                )
            )
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
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focus.moveFocus(FocusDirection.Down)
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

            //verifyPassword
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = confirmPassword.value,
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
                                    isPasswordError,
                                    confirmPassword.value,
                                    isConfirmPasswordError
                                )
                            ) {
                                onEvent(
                                    AuthEvent.OnRegister(
                                        login.value.trim(),
                                        password.value.trim(),
                                        selectedRole.value,
                                    )
                                )
                            }
                        }
                    ),
                    visualTransformation = if (isConfirmPasswordVisible.value) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    label = { Text(text = "Подтвердите пароль") },
                    onValueChange = { confirmPassword.value = it },
                    colors = textFieldColors(),
                    isError = isConfirmPasswordError.value,
                    trailingIcon = {
                        Row {
                            IconButton(
                                onClick = {
                                    isConfirmPasswordVisible.value = !isConfirmPasswordVisible.value
                                }
                            ) {
                                Icon(
                                    if (isConfirmPasswordVisible.value) {
                                        Icons.Outlined.Visibility
                                    } else {
                                        Icons.Outlined.VisibilityOff
                                    },
                                    contentDescription = ""
                                )
                            }

                            IconButton(
                                onClick = {
                                    confirmPassword.value = ""
                                }
                            ) {
                                Icon(Icons.Outlined.Cancel, contentDescription = "")
                            }
                        }
                    },
                )

                if (isConfirmPasswordError.value) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        text = "Пароли не совпадают",
                    )
                }
            }

            Row {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = isSelectedItem(UserType.DOCTOR),
                            onClick = { onChangeState(UserType.DOCTOR) },
                            role = Role.RadioButton,
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple()
                        )
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = isSelectedItem(UserType.DOCTOR),
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.secondary)
                        )
                    )
                    Text(
                        text = "Врач",
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = isSelectedItem(UserType.PATIENT),
                            onClick = { onChangeState(UserType.PATIENT) },
                            role = Role.RadioButton,
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple()
                        )
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = isSelectedItem(UserType.PATIENT),
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.secondary)
                        )
                    )
                    Text(
                        text = "Пациент",
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
                            isPasswordError,
                            confirmPassword.value,
                            isConfirmPasswordError,
                        )
                    ) {
                        onEvent(
                            AuthEvent.OnRegister(
                                login.value.trim(),
                                password.value.trim(),
                                selectedRole.value,
                            )
                        )
                    }
                }
            ) {
                Text(
                    text = "Зарегистрироваться",
                    style = TextStyle(color = colorResource(id = R.color.color_white))
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
    confirmPasswordText: String,
    confirmPasswordErrorState: MutableState<Boolean>,
): Boolean {
    loginErrorState.value = loginText.isEmpty()

    passwordErrorState.value = passwordText.isEmpty()

    confirmPasswordErrorState.value = confirmPasswordText.isEmpty() ||
            confirmPasswordText != passwordText

    return !loginErrorState.value && !passwordErrorState.value && !confirmPasswordErrorState.value
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterPhonePreview() {
    RegisterView(onEvent = {})
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.TABLET)
@Composable
fun RegisterTabletPreview() {
    RegisterView(onEvent = {})
}