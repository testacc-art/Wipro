package reprator.wipro.navigation

import androidx.navigation.NavController


interface AppNavigator : FactListNavigator

interface FactListNavigator  {

}

interface BackNavigator {
    fun navigateToBack(navController: NavController)
}