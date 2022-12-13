package ru.therapyapp.feature_questionnaire_impl

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.therapyapp.feature_questionnaire_api.QuestionnaireScreenRouter
import ru.therapyapp.feature_questionnaire_impl.mvi.QuestionnaireViewModel

class QuestionnaireScreenModule {
    val module = module {
        single<QuestionnaireScreenRouter> {
            QuestionnaireScreenRouterImpl()
        }

        viewModel { (patientId: Int, questionnaireId: Int) ->
            QuestionnaireViewModel(
                patientId = patientId,
                questionnaireId = questionnaireId,
                questionnaireRepository = get(),
                questionnaireAnsweredRepository = get()
            )
        }
    }
}