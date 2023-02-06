package ru.therapyapp.data_mkb.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage
import ru.therapyapp.data_mkb.MkbRepository
import ru.therapyapp.data_mkb.model.Mkb

internal class MkbRepositoryImpl(
    private val mkbService: MkbService,
    private val dispatcher: CoroutineDispatcher
) : MkbRepository {
    override suspend fun getMkbs(): RequestResult<List<Mkb>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(mkbService.getMkbs())
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun createMkb(mkb: Mkb): RequestResult<List<Mkb>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(mkbService.createMkb(mkb))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }
}