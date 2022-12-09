package ru.therapyapp.data_questionnaire

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.therapyapp.data_questionnaire.internal.QuestionnaireRepositoryImpl
import ru.therapyapp.data_questionnaire.internal.QuestionnaireService

class QuestionnaireModule {
    val module = module {
        single<QuestionnaireService>{
            get<Retrofit>().create(QuestionnaireService::class.java)
        }

        single<QuestionnaireRepository> {
            QuestionnaireRepositoryImpl(
                questionnaireService = get(),
                dispatcher = Dispatchers.IO
            )
        }
    }
}