package ru.therapyapp.data_mkb

import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_mkb.model.Mkb

interface MkbRepository {
    suspend fun getMkbs(): RequestResult<List<Mkb>>
    suspend fun createMkb(mkb: Mkb): RequestResult<List<Mkb>>
}