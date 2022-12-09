package ru.therapyapp.data_questionnaire_answered

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.therapyapp.data_questionnaire_answered.internal.QuestionnaireAnsweredRepositoryImpl
import ru.therapyapp.data_questionnaire_answered.internal.QuestionnaireAnsweredService


class QuestionnaireAnsweredModule {
    val module = module {
        single<QuestionnaireAnsweredService> {
            get<Retrofit>().create(QuestionnaireAnsweredService::class.java)
        }

        single<QuestionnaireAnsweredRepository> {
            QuestionnaireAnsweredRepositoryImpl(
                questionnaireAnsweredService = get(),
                dispatcher = Dispatchers.IO
            )
        }
    }
}