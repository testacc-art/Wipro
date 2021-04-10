package reprator.wipro.base.util.network

import kotlinx.coroutines.CoroutineDispatcher

interface AppCoroutineDispatchers {
    val main: CoroutineDispatcher
    val computation: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val singleThread: CoroutineDispatcher
}