package com.example.data_asdas.internal

import com.example.data_asdas.AsdasRepository
import com.example.data_asdas.model.AsdasIndex
import com.example.data_asdas.model.AsdasIndexRequestBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage

internal class AsdasIndexRepositoryImpl(
    private val asdasService: AsdasService,
    private val dispatcher: CoroutineDispatcher,
) : AsdasRepository {
    override suspend fun sendIndex(asdasIndexCreationBody: AsdasIndexRequestBody): RequestResult<AsdasIndex> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(asdasService.sendIndex(asdasIndexCreationBody))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getIndexes(): RequestResult<List<AsdasIndex>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(asdasService.getIndexes())
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getPatientIndexes(patientId: Int): RequestResult<List<AsdasIndex>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(asdasService.getIndexesByPatient(patientId))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }
}