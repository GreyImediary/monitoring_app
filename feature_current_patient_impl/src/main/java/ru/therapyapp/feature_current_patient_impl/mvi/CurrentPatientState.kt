package ru.therapyapp.feature_current_patient_impl.mvi

import ru.therapyapp.data_asdas.model.AsdasIndex
import ru.therapyapp.data_bvas.model.BvasIndex
import ru.therapyapp.data_basdai.model.BasdaiIndex
import ru.therapyapp.data_comments.model.Comment
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_sledai.model.SelenaSledaiIndex

data class CurrentPatientState(
    val patient: Patient?,
    val comments: List<Comment> = emptyList(),
    val basdaiIndexes: List<BasdaiIndex> = emptyList(),
    val selectedBasdaiIndex: BasdaiIndex? = null,
    val bvasIndexes: List<BvasIndex> = emptyList(),
    val sledaiIndexes: List<SelenaSledaiIndex> = emptyList(),
    val selectedBvasIndex: BvasIndex? = null,
    val asdasIndexes: List<AsdasIndex> = emptyList(),
    val selectedAsdasIndex: AsdasIndex? = null,
    val selectedSledaiIndex: SelenaSledaiIndex? = null,
    val currentIndex: IndexType = IndexType.BVAS,
    val isRefreshing: Boolean = false,
    )

enum class IndexType {
    BVAS, BASDAI, ASDAS, SELENA_SLEDAI
}