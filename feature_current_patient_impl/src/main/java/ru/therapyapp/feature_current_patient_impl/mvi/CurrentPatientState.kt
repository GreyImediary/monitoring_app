package ru.therapyapp.feature_current_patient_impl.mvi

import com.example.data_asdas.model.AsdasIndex
import com.example.data_bvas.model.BvasIndex
import ru.therapyapp.data_basdai.model.BasdaiIndex
import ru.therapyapp.data_patient.api.entity.Patient

data class CurrentPatientState(
    val patient: Patient?,
    val comments: List<String> = emptyList(),
    val basdaiIndexes: List<BasdaiIndex> = emptyList(),
    val selectedBasdaiIndex: BasdaiIndex? = null,
    val bvasIndexes: List<BvasIndex> = emptyList(),
    val selectedBvasIndex: BvasIndex? = null,
    val asdasIndexes: List<AsdasIndex> = emptyList(),
    val selectedAsdasIndex: AsdasIndex? = null,
    val currentIndex: IndexType = IndexType.BVAS,
)

enum class IndexType {
    BVAS, BASDAI, ASDAS
}