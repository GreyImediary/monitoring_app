package com.example.data_bvas

import com.example.data_bvas.model.BvasIndex
import com.example.data_bvas.model.BvasRequestBody
import ru.therapyapp.core_network.entity.RequestResult

interface BvasRepository {
    suspend fun sendIndex(bvasIndexCreationBody: BvasRequestBody): RequestResult<BvasIndex>
    suspend fun getIndexes(): RequestResult<List<BvasIndex>>
    suspend fun getPatientIndexes(patientId: Int): RequestResult<List<BvasIndex>>
}