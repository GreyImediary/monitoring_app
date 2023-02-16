package ru.therapyapp.data_sledai.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage
import ru.therapyapp.data_sledai.SelenaSledaiRepository
import ru.therapyapp.data_sledai.model.SelenaSledaiIndex
import ru.therapyapp.data_sledai.model.SelenaSledaiIndexBody

internal class SelenaSledaiRepositoryImpl(
    private val selenaSledaiService: SelenaSledaiService,
    private val dispatcher: CoroutineDispatcher,
) : SelenaSledaiRepository {
    override suspend fun sendIndex(selenaSledaiBody: SelenaSledaiIndexBody): RequestResult<SelenaSledaiIndex> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(selenaSledaiService.sendIndex(selenaSledaiBody))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getIndexes(): RequestResult<List<SelenaSledaiIndex>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(selenaSledaiService.getIndexes())
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getPatientIndexes(patientId: Int): RequestResult<List<SelenaSledaiIndex>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(selenaSledaiService.getIndexesByPatient(patientId))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }
}