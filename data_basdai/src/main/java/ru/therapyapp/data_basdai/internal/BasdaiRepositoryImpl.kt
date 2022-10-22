package ru.therapyapp.data_basdai.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage
import ru.therapyapp.data_basdai.BasdaiRepository
import ru.therapyapp.data_basdai.model.BasdaiIndex
import ru.therapyapp.data_basdai.model.BasdaiIndexCreationBody

internal class BasdaiRepositoryImpl(
    private val basdaiService: BasdaiService,
    private val dispatcher: CoroutineDispatcher,
) : BasdaiRepository {
    override suspend fun sendIndex(basdaiIndexCreationBody: BasdaiIndexCreationBody): RequestResult<BasdaiIndex> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(basdaiService.sendIndex(basdaiIndexCreationBody))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getIndexes(): RequestResult<List<BasdaiIndex>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(basdaiService.getIndexes())
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getPatientIndexes(patientId: Int): RequestResult<List<BasdaiIndex>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(basdaiService.getIndexesByPatient(patientId))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }
}