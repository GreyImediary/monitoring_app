package ru.therapyapp.data_comments

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.therapyapp.data_comments.internal.CommentRepositoryImpl
import ru.therapyapp.data_comments.internal.CommentService

class CommentModule {
    val module = module {
        single<CommentService>{
            get<Retrofit>().create(CommentService::class.java)
        }

        single<CommentRepository>{ CommentRepositoryImpl(
            commentService = get(),
            dispatcher = Dispatchers.IO,
        ) }
    }
}