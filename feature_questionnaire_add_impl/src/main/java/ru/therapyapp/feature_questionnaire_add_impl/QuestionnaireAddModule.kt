package ru.therapyapp.feature_questionnaire_add_impl

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.feature_questionnaire_add_api.QuestionnaireAddScreenRouter
import ru.therapyapp.feature_questionnaire_add_impl.mvi.QuestionnaireAddViewModel

class QuestionnaireAddModule {
    val module = module {
        single<QuestionnaireAddScreenRouter>{
            QuestionnaireAddScreenRouterImpl()
        }

        viewModel { (doctorId: Int, patients: List<Patient>) -> QuestionnaireAddViewModel(
            doctorId,
            patients,
            questionnaireRepository = get(),
        ) }
    }
}