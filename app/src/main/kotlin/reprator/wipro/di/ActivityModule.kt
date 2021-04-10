package reprator.wipro.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import reprator.wipro.implementation.AppNavigatorImpl
import reprator.wipro.navigation.AppNavigator

@InstallIn(ActivityComponent::class)
@Module
class ActivityModule {

    @ActivityScoped
    @Provides
    fun provideAppNavigator(): AppNavigator {
        return AppNavigatorImpl()
    }
}