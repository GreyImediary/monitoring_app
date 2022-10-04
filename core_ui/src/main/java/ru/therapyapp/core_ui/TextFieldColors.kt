package ru.therapyapp.core_ui

import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

@Composable
fun textFieldColors(
    focusedBorderColor: Color = colorResource(id = R.color.main),
    cursorColor: Color = colorResource(id = R.color.main),
    trailingIconColor: Color = colorResource(id = R.color.icon_color),
    unfocusedBorderColor: Color = colorResource(id = R.color.textfield_unfocused_color),
    focusedLabelColor: Color = colorResource(id = R.color.main),
    unfocusedLabelColor: Color = colorResource(id = R.color.textfield_unfocused_color),
) = TextFieldDefaults.outlinedTextFieldColors(
    focusedBorderColor = focusedBorderColor,
    cursorColor = cursorColor,
    trailingIconColor = trailingIconColor,
    unfocusedBorderColor = unfocusedBorderColor,
    focusedLabelColor = focusedLabelColor,
    unfocusedLabelColor = unfocusedLabelColor,

)