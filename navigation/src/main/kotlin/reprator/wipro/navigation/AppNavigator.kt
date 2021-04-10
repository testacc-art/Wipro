package reprator.wipro.navigation

import androidx.navigation.NavController


interface AppNavigator : FactListNavigator

interface FactListNavigator  {
    fun navigateToCityDetailScreen(
        navController: NavController,
        latLng: String, title: String
    )
}

interface BackNavigator {
    fun navigateToBack(navController: NavController)
}