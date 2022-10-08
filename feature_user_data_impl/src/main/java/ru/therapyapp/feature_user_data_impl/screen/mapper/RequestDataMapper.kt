package ru.therapyapp.feature_user_data_impl.screen.mapper

import ru.therapyapp.data_core.entity.Sex
import ru.therapyapp.data_patient.api.entity.PatientRequestBody

object RequestDataMapper {
    fun mapSexToSexEnum(sex: String): Sex = when (sex) {
        "Мужской" -> Sex.MALE
        "Женский" -> Sex.FEMALE
        else -> Sex.MALE
    }
}