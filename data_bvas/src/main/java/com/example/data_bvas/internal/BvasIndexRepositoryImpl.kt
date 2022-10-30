package com.example.data_bvas.internal

import com.example.data_bvas.BvasRepository
import com.example.data_bvas.model.BvasIndex
import com.example.data_bvas.model.BvasRequestBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage

internal class BvasIndexRepositoryImpl(
    private val bvasService: BvasService,
    private val dispatcher: CoroutineDispatcher,
) : BvasRepository {
    override suspend fun sendIndex(bvasIndexCreationBody: BvasRequestBody): RequestResult<BvasIndex> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(bvasService.sendIndex(bvasIndexCreationBody))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getIndexes(): RequestResult<List<BvasIndex>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(bvasService.getIndexes())
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getPatientIndexes(patientId: Int): RequestResult<List<BvasIndex>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(bvasService.getIndexesByPatient(patientId))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }
}