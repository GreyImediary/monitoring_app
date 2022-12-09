package ru.therapyapp.feature_current_patient_impl.mvi

import ru.therapyapp.data_asdas.model.AsdasIndex
import ru.therapyapp.data_bvas.BvasRepository
import ru.therapyapp.data_bvas.model.BvasIndex
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_basdai.BasdaiRepository
import ru.therapyapp.data_basdai.model.BasdaiIndex
import ru.therapyapp.data_comments.CommentRepository
import ru.therapyapp.data_core.utils.getStringDateWithHour
import ru.therapyapp.data_patient.api.entity.Patient
import java.util.*

class CurrentPatientViewModel(
    patient: Patient?,
    private val bvasRepository: BvasRepository,
    private val basdaiRepository: BasdaiRepository,
    private val asdasRepository: ru.therapyapp.data_asdas.AsdasRepository,
    private val commentRepository: CommentRepository,
) : MviViewModel<CurrentPatientEvent, CurrentPatientState, CurrentPatientSideEffect>(
    initialState = CurrentPatientState(patient = patient)
) {
    private var basdaiIndexes: List<BasdaiIndex> = emptyList()
    private var bvasIndexes: List<BvasIndex> = emptyList()
    private var asdasIndexes: List<AsdasIndex> = emptyList()

    override fun dispatch(event: CurrentPatientEvent) {
        when (event) {
            CurrentPatientEvent.FetchData -> fetchData()
            is CurrentPatientEvent.AddComment -> addComment(event.comment)
            is CurrentPatientEvent.ChangeDataPeriod -> changeDataPeriod(event.startDate, event.endDate)
            CurrentPatientEvent.OnDeleteDateRange -> deleteDateRange()
            is CurrentPatientEvent.ChangeIndex -> changeIndex(event.chosenIndex)
            CurrentPatientEvent.OnBackClick -> backClick()
            is CurrentPatientEvent.OnMessageShow -> showToastMessage(event.message)
            is CurrentPatientEvent.SelectIndexDataByDate -> selectIndexDataByDate(event.date)
        }
    }

    private fun selectIndexDataByDate(date: Date?) {
        intent {
            when (state.currentIndex) {
                IndexType.BVAS -> {
                    val indexData = bvasIndexes.find {
                        it.date.getStringDateWithHour() == date?.getStringDateWithHour()
                    }

                    indexData?.let {
                        reduce { state.copy(selectedBvasIndex = it) }
                    } ?: reduce { state.copy(selectedBvasIndex = bvasIndexes.last()) }
                }
                IndexType.BASDAI -> {
                    val indexData = basdaiIndexes.find { it.date == date }

                    indexData?.let {
                        reduce { state.copy(selectedBasdaiIndex = it) }
                    } ?: reduce { state.copy(selectedBasdaiIndex = basdaiIndexes.last()) }
                }
                IndexType.ASDAS -> {
                    val indexData = asdasIndexes.find { it.date == date }

                    indexData?.let {
                        reduce { state.copy(selectedAsdasIndex = it) }
                    } ?: reduce { state.copy(selectedAsdasIndex = asdasIndexes.last()) }

                }
            }
        }
    }

    private fun addComment(comment: String) {
        intent {
            when (val result = commentRepository.createComment(state.patient?.id ?: -1, comment)) {
                is RequestResult.Error -> {
                    postSideEffect(CurrentPatientSideEffect.ShowMessage(result.message ?: "Не удалось добавить комментарий"))
                }
                is RequestResult.Success -> {
                    reduce { state.copy(comments = result.data.map { it.comment }) }
                }
            }
        }
    }

    private fun showToastMessage(message: String) {
        intent { postSideEffect(CurrentPatientSideEffect.ShowMessage(message)) }
    }

    private fun changeDataPeriod(startDate: Long, endDate: Long) {
        intent {
            when (state.currentIndex) {
                IndexType.BVAS -> {
                    reduce {
                        state.copy(
                            bvasIndexes = bvasIndexes.filter { it.date.time in startDate..endDate }
                        )
                    }
                }
                IndexType.BASDAI -> {
                    reduce {
                        state.copy(
                            basdaiIndexes = basdaiIndexes.filter { it.date.time in startDate..endDate }
                        )
                    }
                }
                IndexType.ASDAS -> {
                    reduce {
                        state.copy(
                            asdasIndexes = asdasIndexes.filter { it.date.time in startDate..endDate }
                        )
                    }
                }
            }
        }
    }

    private fun deleteDateRange() {
        intent {
            when (state.currentIndex) {
                IndexType.BVAS -> {
                    reduce {
                        state.copy(
                            bvasIndexes = bvasIndexes
                        )
                    }
                }
                IndexType.BASDAI -> {
                    reduce {
                        state.copy(
                            basdaiIndexes = basdaiIndexes
                        )
                    }
                }
                IndexType.ASDAS -> {
                    reduce {
                        state.copy(
                            asdasIndexes = asdasIndexes
                        )
                    }
                }
            }
        }
    }

    private fun changeIndex(chosenIndex: IndexType) {
        intent {
            reduce { state.copy(currentIndex = chosenIndex) }
        }
    }

    private fun backClick() {
        intent {
            postSideEffect(CurrentPatientSideEffect.Finish)
        }
    }

    private fun fetchData() {
        fetchBvasIndexes()
        fetchAsdasIndexes()
        fetchBasdaiIndexes()
        fetchComments()
    }

    private fun fetchComments() {
        intent {
            when (val result = commentRepository.getCommentById(state.patient?.id ?: -1)) {
                is RequestResult.Error -> {
                    postSideEffect(CurrentPatientSideEffect.ShowMessage(result.message
                        ?: "Невозможно получить комментарии"))
                }
                is RequestResult.Success -> {
                    reduce { state.copy(comments = result.data.map { it.comment }) }
                }
            }
        }
    }

    private fun fetchBvasIndexes() {
        intent {

            when (val result = bvasRepository.getPatientIndexes(patientId = state.patient?.id ?: -1)) {
                is RequestResult.Error -> {
                    postSideEffect(CurrentPatientSideEffect.ShowMessage(result.message
                        ?: "Ошибка во время получения индексов BVAS"))
                }
                is RequestResult.Success -> {
                    bvasIndexes = result.data
                    reduce { state.copy(bvasIndexes = result.data, selectedBvasIndex = result.data.last()) }
                }
            }
        }
    }

    private fun fetchBasdaiIndexes() {
        intent {

            when (val result =
                basdaiRepository.getPatientIndexes(patientId = state.patient?.id ?: -1)) {
                is RequestResult.Error -> {
                    postSideEffect(CurrentPatientSideEffect.ShowMessage(result.message
                        ?: "Ошибка во время получения индексов BASDAI"))
                }
                is RequestResult.Success -> {
                    basdaiIndexes = result.data
                    reduce { state.copy(basdaiIndexes = result.data, selectedBasdaiIndex = result.data.last()) }
                }
            }
        }
    }

    private fun fetchAsdasIndexes() {
        intent {

            when (val result =
                asdasRepository.getPatientIndexes(patientId = state.patient?.id ?: -1)) {
                is RequestResult.Error -> {
                    postSideEffect(CurrentPatientSideEffect.ShowMessage(result.message
                        ?: "Ошибка во время получения индексов ASDAS"))
                }
                is RequestResult.Success -> {
                    asdasIndexes = result.data
                    reduce { state.copy(asdasIndexes = result.data, selectedAsdasIndex = result.data.last()) }
                }
            }
        }
    }
}