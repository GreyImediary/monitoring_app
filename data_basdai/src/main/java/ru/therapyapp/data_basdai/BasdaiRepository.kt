package ru.therapyapp.data_basdai

import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_basdai.model.BasdaiIndex
import ru.therapyapp.data_basdai.model.BasdaiIndexCreationBody

interface BasdaiRepository {
    suspend fun sendIndex(basdaiIndexCreationBody: BasdaiIndexCreationBody): RequestResult<BasdaiIndex>
    suspend fun getIndexes(): RequestResult<List<BasdaiIndex>>
    suspend fun getPatientIndexes(patientId: Int): RequestResult<List<BasdaiIndex>>
}