package reprator.wipro.factlist.diFake

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import reprator.wipro.base.util.network.AppCoroutineDispatchers

class TestDispatcherProvider(
    override val main: CoroutineDispatcher = Dispatchers.Main,
    override val computation: CoroutineDispatcher = Dispatchers.Unconfined,
    override val io: CoroutineDispatcher = Dispatchers.IO,
    override val default: CoroutineDispatcher = Dispatchers.Unconfined,
    override val singleThread: CoroutineDispatcher = Dispatchers.Unconfined
) : AppCoroutineDispatchers