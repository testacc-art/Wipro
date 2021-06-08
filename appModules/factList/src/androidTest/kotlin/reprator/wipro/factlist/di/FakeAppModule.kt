package reprator.wipro.factlist.di

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.testing.TestLifecycleOwner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import reprator.wipro.base.util.interent.ConnectionDetector
import reprator.wipro.base.util.network.AppCoroutineDispatchers
import java.util.concurrent.Executors

@InstallIn(SingletonComponent::class)
@Module
class FakeAppModule {

    @Provides
    fun provideLifeCycle(): Lifecycle {
        return TestLifecycleOwner().lifecycle
    }

    @Provides
    fun provideLifetimeScope(lifecycle: Lifecycle): CoroutineScope {
        return lifecycle.coroutineScope
    }

    @Provides
    fun provideCoroutineDispatcherProvider(): AppCoroutineDispatchers {
        return TestDispatcherProvider(
            Dispatchers.Main, Dispatchers.IO, Dispatchers.IO, Dispatchers.Default,
            Executors.newFixedThreadPool(1).asCoroutineDispatcher()
        )
    }

    @Provides
    fun provideConnectivityChecker(
        @ApplicationContext context: Context, lifecycle: Lifecycle
    ): ConnectionDetector {
        return TestInternet()
    }

    class TestInternet(override val isInternetAvailable: Boolean = true): ConnectionDetector
}