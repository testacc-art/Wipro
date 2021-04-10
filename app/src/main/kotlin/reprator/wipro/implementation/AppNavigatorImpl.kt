package reprator.wipro.implementation

import dagger.hilt.android.scopes.ActivityScoped
import reprator.wipro.navigation.AppNavigator
import javax.inject.Inject

@ActivityScoped
class AppNavigatorImpl @Inject constructor() : AppNavigator