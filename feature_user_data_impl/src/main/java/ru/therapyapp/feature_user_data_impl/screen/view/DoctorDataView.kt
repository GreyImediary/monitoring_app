package ru.therapyapp.feature_user_data_impl.screen.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.core_ui.MaskVisualTransformation
import ru.therapyapp.core_ui.R
import ru.therapyapp.core_ui.textFieldColors
import ru.therapyapp.data_doctor.api.entity.DoctorRequestBody
import ru.therapyapp.feature_user_data_impl.screen.config.NumberMask.INPUT_LENGTH
import ru.therapyapp.feature_user_data_impl.screen.config.NumberMask.MASK
import ru.therapyapp.feature_user_data_impl.screen.config.getButtonsHorizontalPadding
import ru.therapyapp.feature_user_data_impl.screen.config.getScreenHorizontalPadding
import ru.therapyapp.feature_user_data_impl.screen.mapper.RequestDataMapper
import ru.therapyapp.feature_user_data_impl.screen.mvi.UserDataEvent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DoctorDataView(
    userId: Int,
    onEvent: (UserDataEvent) -> Unit,
) {
    val name = rememberSaveable { mutableStateOf("") }
    val surname = rememberSaveable { mutableStateOf("") }
    val patronymic = rememberSaveable { mutableStateOf("") }
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }

    val isNameError = rememberSaveable { mutableStateOf(false) }
    val isSurnameError = rememberSaveable { mutableStateOf(false) }
    val isPhoneNumberError = rememberSaveable { mutableStateOf(false) }

    val options = listOf("Мужской", "Женский")
    val expanded = remember { mutableStateOf(false) }
    val selectedOptionText = remember { mutableStateOf(options[0]) }

    val focus = LocalFocusManager.current
    val scroll = rememberScrollState()
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.main_background_color))
    ) {
        val horizontalPadding = getScreenHorizontalPadding(maxWidth)
        val buttonPadding = getButtonsHorizontalPadding(maxWidth)

        if (maxWidth < 600.dp) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .verticalScroll(scroll)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = "Заполните данные",
                    style = TextStyle(
                        color = colorResource(id = R.color.font_color),
                        fontWeight = FontWeight.W700,
                        fontSize = 36.sp
                    )
                )

                //name
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = name.value,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focus.moveFocus(FocusDirection.Down)
                            }
                        ),
                        isError = isNameError.value,
                        label = { Text(text = "Имя*") },
                        onValueChange = { name.value = it },
                        colors = textFieldColors(),
                        trailingIcon = {
                            IconButton(onClick = { name.value = "" }) {
                                Icon(Icons.Outlined.Cancel, contentDescription = "")
                            }
                        }
                    )

                    if (isNameError.value) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            text = "Введите имя",
                        )
                    }
                }

                //surname
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = surname.value,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focus.moveFocus(FocusDirection.Down)
                            }
                        ),
                        isError = isSurnameError.value,
                        label = { Text(text = "Фамилия*") },
                        onValueChange = { surname.value = it },
                        colors = textFieldColors(),
                        trailingIcon = {
                            IconButton(onClick = { surname.value = "" }) {
                                Icon(Icons.Outlined.Cancel, contentDescription = "")
                            }
                        }
                    )

                    if (isSurnameError.value) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            text = "Введите фамилию",
                        )
                    }
                }

                //patronymic
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = patronymic.value,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focus.moveFocus(FocusDirection.Down)
                            }
                        ),
                        label = { Text(text = "Отчество") },
                        onValueChange = { patronymic.value = it },
                        colors = textFieldColors(),
                        trailingIcon = {
                            IconButton(onClick = { patronymic.value = "" }) {
                                Icon(Icons.Outlined.Cancel, contentDescription = "")
                            }
                        }
                    )
                }

                //Sex
                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    expanded = expanded.value,
                    onExpandedChange = {
                        expanded.value = !expanded.value
                    }
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        readOnly = true,
                        value = selectedOptionText.value,
                        onValueChange = { },
                        label = { Text("Пол*") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded.value
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            backgroundColor = colorResource(id = R.color.main_50),
                            trailingIconColor = colorResource(id = R.color.icon_color),
                            focusedLabelColor = colorResource(id = R.color.font_color),
                            unfocusedLabelColor = colorResource(id = R.color.font_color),
                            focusedTrailingIconColor = colorResource(id = R.color.icon_color),
                            focusedIndicatorColor = colorResource(id = R.color.main),
                            unfocusedIndicatorColor = colorResource(id = R.color.main)

                        ),
                    )
                    ExposedDropdownMenu(
                        modifier = Modifier
                            .background(color = colorResource(id = R.color.main_50))
                            .fillMaxWidth(),
                        expanded = expanded.value,
                        onDismissRequest = {
                            expanded.value = false
                        }
                    ) {
                        options.forEach { selectionOption ->
                            DropdownMenuItem(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onClick = {
                                    selectedOptionText.value = selectionOption
                                    expanded.value = false
                                }
                            ) {
                                Text(text = selectionOption)
                            }
                        }
                    }
                }

                //phone
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = phoneNumber.value,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Phone
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focus.moveFocus(FocusDirection.Down)
                            }
                        ),
                        isError = isPhoneNumberError.value,
                        label = { Text(text = "Номер телефона*") },
                        onValueChange = { phone ->
                            if (phone.length <= INPUT_LENGTH) {
                                phoneNumber.value = phone.filter { it.isDigit() }
                            }
                        },
                        visualTransformation = MaskVisualTransformation(MASK),
                        colors = textFieldColors(),
                        trailingIcon = {
                            IconButton(onClick = { phoneNumber.value = "" }) {
                                Icon(Icons.Outlined.Cancel, contentDescription = "")
                            }
                        }
                    )

                    if (isPhoneNumberError.value) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            text = "Введите номер телефона",
                        )
                    }
                }

                //email
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = email.value,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Email
                        ),
                        label = { Text(text = "Почта") },
                        onValueChange = { email.value = it },
                        colors = textFieldColors(),
                        trailingIcon = {
                            IconButton(onClick = { email.value = "" }) {
                                Icon(Icons.Outlined.Cancel, contentDescription = "")
                            }
                        }
                    )
                }

                AppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = buttonPadding, end = buttonPadding),
                    onClick = {

                        if (isFieldValid(
                                name = name.value,
                                isNameError = isNameError,
                                surname = surname.value,
                                isSurnameError = isSurnameError,
                                phone = phoneNumber.value,
                                isPhoneNumberError = isPhoneNumberError
                            )
                        ) {
                            onEvent(
                                UserDataEvent.OnDoctorDone(
                                    DoctorRequestBody(
                                        userId = userId,
                                        name = name.value.trim(),
                                        surname = surname.value.trim(),
                                        patronymic = patronymic.value.trim(),
                                        sex = RequestDataMapper.mapSexToSexEnum(selectedOptionText.value),
                                        phoneNumber = phoneNumber.value.trim(),
                                        email = email.value.trim()
                                    )
                                )
                            )
                        }
                    }
                ) {
                    Text(
                        text = "Завершить",
                        style = TextStyle(color = colorResource(id = R.color.color_white))
                    )
                }
            }
        } else {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .verticalScroll(scroll)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {

                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = "Заполните данные",
                        style = TextStyle(
                            color = colorResource(id = R.color.font_color),
                            fontWeight = FontWeight.W700,
                            fontSize = 36.sp
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    //name
                    Column(
                        modifier = Modifier
                            .padding(bottom = 16.dp, end = 16.dp)
                            .weight(0.4f)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = name.value,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focus.moveFocus(FocusDirection.Down)
                                }
                            ),
                            isError = isNameError.value,
                            label = { Text(text = "Имя*") },
                            onValueChange = { name.value = it },
                            colors = textFieldColors(),
                            trailingIcon = {
                                IconButton(onClick = { name.value = "" }) {
                                    Icon(Icons.Outlined.Cancel, contentDescription = "")
                                }
                            }
                        )

                        if (isNameError.value) {
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                color = MaterialTheme.colors.error,
                                style = MaterialTheme.typography.caption,
                                text = "Введите имя",
                            )
                        }
                    }

                    //surname
                    Column(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .weight(0.4f)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = surname.value,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focus.moveFocus(FocusDirection.Down)
                                }
                            ),
                            isError = isSurnameError.value,
                            label = { Text(text = "Фамилия*") },
                            onValueChange = { surname.value = it },
                            colors = textFieldColors(),
                            trailingIcon = {
                                IconButton(onClick = { surname.value = "" }) {
                                    Icon(Icons.Outlined.Cancel, contentDescription = "")
                                }
                            }
                        )

                        if (isSurnameError.value) {
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                color = MaterialTheme.colors.error,
                                style = MaterialTheme.typography.caption,
                                text = "Введите фамилию",
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    //patronymic
                    Column(
                        modifier = Modifier
                            .padding(bottom = 16.dp, end = 16.dp)
                            .weight(0.4f)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = patronymic.value,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focus.moveFocus(FocusDirection.Down)
                                }
                            ),
                            label = { Text(text = "Отчество") },
                            onValueChange = { patronymic.value = it },
                            colors = textFieldColors(),
                            trailingIcon = {
                                IconButton(onClick = { patronymic.value = "" }) {
                                    Icon(Icons.Outlined.Cancel, contentDescription = "")
                                }
                            }
                        )
                    }

                    //Sex
                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .weight(0.4f),
                        expanded = expanded.value,
                        onExpandedChange = {
                            expanded.value = !expanded.value
                        }
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            readOnly = true,
                            value = selectedOptionText.value,
                            onValueChange = { },
                            label = { Text("Пол*") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded.value
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                backgroundColor = colorResource(id = R.color.main_50),
                                trailingIconColor = colorResource(id = R.color.icon_color),
                                focusedLabelColor = colorResource(id = R.color.font_color),
                                unfocusedLabelColor = colorResource(id = R.color.font_color),
                                focusedTrailingIconColor = colorResource(id = R.color.icon_color),
                                focusedIndicatorColor = colorResource(id = R.color.main),
                                unfocusedIndicatorColor = colorResource(id = R.color.main)

                            ),
                        )
                        ExposedDropdownMenu(
                            modifier = Modifier
                                .background(color = colorResource(id = R.color.main_50))
                                .fillMaxWidth(),
                            expanded = expanded.value,
                            onDismissRequest = {
                                expanded.value = false
                            }
                        ) {
                            options.forEach { selectionOption ->
                                DropdownMenuItem(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    onClick = {
                                        selectedOptionText.value = selectionOption
                                        expanded.value = false
                                    }
                                ) {
                                    Text(text = selectionOption)
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    //phone
                    Column(
                        modifier = Modifier
                            .padding(bottom = 16.dp, end = 16.dp)
                            .weight(0.4f)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = phoneNumber.value,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Phone
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focus.moveFocus(FocusDirection.Down)
                                }
                            ),
                            isError = isPhoneNumberError.value,
                            label = { Text(text = "Номер телефона*") },
                            onValueChange = { phone ->
                                if (phone.length <= INPUT_LENGTH) {
                                    phoneNumber.value = phone.filter { it.isDigit() }
                                }
                            },
                            visualTransformation = MaskVisualTransformation(MASK),
                            colors = textFieldColors(),
                            trailingIcon = {
                                IconButton(onClick = { phoneNumber.value = "" }) {
                                    Icon(Icons.Outlined.Cancel, contentDescription = "")
                                }
                            }
                        )

                        if (isPhoneNumberError.value) {
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                color = MaterialTheme.colors.error,
                                style = MaterialTheme.typography.caption,
                                text = "Введите номер телефона",
                            )
                        }
                    }

                    //email
                    Column(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .weight(0.4f)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = email.value,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Email
                            ),
                            label = { Text(text = "Почта") },
                            onValueChange = { email.value = it },
                            colors = textFieldColors(),
                            trailingIcon = {
                                IconButton(onClick = { email.value = "" }) {
                                    Icon(Icons.Outlined.Cancel, contentDescription = "")
                                }
                            }
                        )
                    }
                }

                AppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = buttonPadding, end = buttonPadding),
                    onClick = {

                        if (isFieldValid(
                                name = name.value,
                                isNameError = isNameError,
                                surname = surname.value,
                                isSurnameError = isSurnameError,
                                phone = phoneNumber.value,
                                isPhoneNumberError = isPhoneNumberError
                            )
                        ) {
                            onEvent(
                                UserDataEvent.OnDoctorDone(
                                    DoctorRequestBody(
                                        userId = userId,
                                        name = name.value.trim(),
                                        surname = surname.value.trim(),
                                        patronymic = patronymic.value.trim(),
                                        sex = RequestDataMapper.mapSexToSexEnum(selectedOptionText.value),
                                        phoneNumber = phoneNumber.value.trim(),
                                        email = email.value.trim()
                                    )
                                )
                            )
                        }
                    }
                ) {
                    Text(
                        text = "Завершить",
                        style = TextStyle(color = colorResource(id = R.color.color_white))
                    )
                }
            }

        }
    }
}

private fun isFieldValid(
    name: String,
    isNameError: MutableState<Boolean>,
    surname: String,
    isSurnameError: MutableState<Boolean>,
    phone: String,
    isPhoneNumberError: MutableState<Boolean>,
): Boolean {
    isNameError.value = name.isEmpty()
    isSurnameError.value = surname.isEmpty()
    isPhoneNumberError.value = phone.isEmpty()

    return !isNameError.value && !isSurnameError.value && !isSurnameError.value
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PhonePreview() {
    DoctorDataView(userId = -1, onEvent = {})
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.TABLET)
@Composable
private fun TabletPreview() {
    DoctorDataView(userId = -1, onEvent = {})
}