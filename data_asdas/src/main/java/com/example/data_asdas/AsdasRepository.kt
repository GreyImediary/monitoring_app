package com.example.data_asdas

import com.example.data_asdas.model.AsdasIndex
import com.example.data_asdas.model.AsdasIndexRequestBody
import ru.therapyapp.core_network.entity.RequestResult

interface AsdasRepository {
    suspend fun sendIndex(asdasIndexCreationBody: AsdasIndexRequestBody): RequestResult<AsdasIndex>
    suspend fun getIndexes(): RequestResult<List<AsdasIndex>>
    suspend fun getPatientIndexes(patientId: Int): RequestResult<List<AsdasIndex>>
}