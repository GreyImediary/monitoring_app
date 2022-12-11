package ru.therapyapp.feature_answered_questionnaire_impl

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.therapyapp.feature_answered_questionnaire_api.QuestionnaireAnsweredScreenRouter
import ru.therapyapp.feature_answered_questionnaire_impl.mvi.QuestionnaireAnsweredViewModel

class QuestionnaireAnsweredScreenModule {
    val module = module {
        single<QuestionnaireAnsweredScreenRouter>{
            QuestionnaireAnsweredScreenRouterImpl()
        }

        viewModel { (questionnaireId: Int) -> QuestionnaireAnsweredViewModel(
            questionnaireId,
            questionnaireAnsweredRepository = get()
        ) }
    }
}