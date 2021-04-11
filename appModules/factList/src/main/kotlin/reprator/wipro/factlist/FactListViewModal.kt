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
    private val coroutineDispatcherProvider: AppCoroutineDispatchers,
    private val factListUseCase: FactListUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMsg = MutableLiveData("")

    val errorMsg: LiveData<String> = _errorMsg

    private val _title = MutableLiveData<String?>()
    val title: LiveData<String?> = _title

    @VisibleForTesting
    val _factList = MutableLiveData(emptyList<FactModals>())

    val _swipeErrorMsg = MutableLiveData("")
    val _swipeLoading = MutableLiveData(false)

    fun getFactList() {
        useCaseCall({
            _isLoading.value = it
        }, {
            _errorMsg.value = it
        })
    }

    fun retryFactList() {
        _isLoading.value = true
        _errorMsg.value = ""
        getFactList()
    }

    fun onRefresh() {
        useCaseCall({
            _swipeLoading.value = it
        }, {
            _swipeErrorMsg.value = it
        })
    }

    private fun useCaseCall(
        blockLoader: (Boolean) -> Unit, blockError: (String) -> Unit
    ) {
        computationalBlock {
            factListUseCase().flowOn(coroutineDispatcherProvider.io)
                .catch { e ->
                    blockError(e.localizedMessage ?: "")
                }.onStart {
                    blockLoader(true)
                }.onCompletion {
                    blockLoader(false)
                }.flowOn(coroutineDispatcherProvider.main)
                .collect {
                    withContext(coroutineDispatcherProvider.main) {
                        when (it) {
                            is AppSuccess -> {
                                _title.value = it.data.first
                                _factList.value = it.data.second
                            }
                            is AppError -> {
                                blockError(it.message ?: it.throwable?.message ?: "")
                            }
                            else -> throw IllegalArgumentException()
                        }
                    }
                }
        }
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