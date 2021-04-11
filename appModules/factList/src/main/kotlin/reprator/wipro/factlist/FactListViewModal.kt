package reprator.wipro.factlist

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import reprator.wipro.base.extensions.computationalBlock
import reprator.wipro.base.useCases.AppError
import reprator.wipro.base.useCases.AppSuccess
import reprator.wipro.base.util.network.AppCoroutineDispatchers
import reprator.wipro.factlist.domain.usecase.FactListUseCase
import reprator.wipro.factlist.modals.FactModals
import javax.inject.Inject

@HiltViewModel
class FactListViewModal @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val coroutineDispatcherProvider: AppCoroutineDispatchers,
    private val factListUseCase: FactListUseCase
) : ViewModel() {

    private val _isLoadingForeCast = MutableLiveData(true)
    val isLoadingForeCast: LiveData<Boolean> = _isLoadingForeCast

    private val _errorMsgForeCast = MutableLiveData("")
    val errorMsgForeCast: LiveData<String> = _errorMsgForeCast

    private val _title = MutableLiveData<String?>()
    val title: LiveData<String?> = _title

    @VisibleForTesting
    val _factList = MutableLiveData(emptyList<FactModals>())

    fun getFactList() {
        computationalBlock {
            factListUseCase().flowOn(coroutineDispatcherProvider.io)
                .catch { e ->
                    _errorMsgForeCast.value = e.localizedMessage
                }.onStart {
                    _isLoadingForeCast.value = true
                }.onCompletion {
                    _isLoadingForeCast.value = false
                }.flowOn(coroutineDispatcherProvider.main)
                .collect {
                    withContext(coroutineDispatcherProvider.main) {
                        when (it) {
                            is AppSuccess -> {
                                _title.value = it.data.first
                                _factList.value = it.data.second
                            }
                            is AppError -> {
                                _errorMsgForeCast.value = it.message ?: it.throwable!!.message
                            }
                            else -> throw IllegalArgumentException()
                        }
                    }
                }
        }
    }

    fun retryFactList() {
        _isLoadingForeCast.value = true
        _errorMsgForeCast.value = ""
        getFactList()
    }

    private fun computationalBlock(
        coroutineExceptionHandler: CoroutineExceptionHandler? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.computationalBlock(
            coroutineDispatcherProvider,
            coroutineExceptionHandler,
            block
        )
    }
}