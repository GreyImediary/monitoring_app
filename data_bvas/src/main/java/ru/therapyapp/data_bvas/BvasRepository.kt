package ru.therapyapp.data_bvas

import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_bvas.model.BvasIndex
import ru.therapyapp.data_bvas.model.BvasRequestBody

interface BvasRepository {
    suspend fun sendIndex(bvasIndexCreationBody: BvasRequestBody): RequestResult<BvasIndex>
    suspend fun getIndexes(): RequestResult<List<BvasIndex>>
    suspend fun getPatientIndexes(patientId: Int): RequestResult<List<BvasIndex>>
}