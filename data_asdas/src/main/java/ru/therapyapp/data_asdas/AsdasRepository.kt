package ru.therapyapp.data_asdas

import ru.therapyapp.data_asdas.model.AsdasIndex
import ru.therapyapp.data_asdas.model.AsdasIndexRequestBody
import ru.therapyapp.core_network.entity.RequestResult

interface AsdasRepository {
    suspend fun sendIndex(asdasIndexCreationBody: AsdasIndexRequestBody): RequestResult<AsdasIndex>
    suspend fun getIndexes(): RequestResult<List<AsdasIndex>>
    suspend fun getPatientIndexes(patientId: Int): RequestResult<List<AsdasIndex>>
}