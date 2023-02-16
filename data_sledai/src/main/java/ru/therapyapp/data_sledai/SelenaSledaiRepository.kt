package ru.therapyapp.data_sledai

import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_sledai.model.SelenaSledaiIndex
import ru.therapyapp.data_sledai.model.SelenaSledaiIndexBody

interface SelenaSledaiRepository {
    suspend fun sendIndex(selenaSledaiBody: SelenaSledaiIndexBody): RequestResult<SelenaSledaiIndex>
    suspend fun getIndexes(): RequestResult<List<SelenaSledaiIndex>>
    suspend fun getPatientIndexes(patientId: Int): RequestResult<List<SelenaSledaiIndex>>
}