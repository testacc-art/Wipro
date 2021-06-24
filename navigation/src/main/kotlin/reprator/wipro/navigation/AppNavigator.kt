package reprator.wipro.navigation

import androidx.navigation.NavController


interface AppNavigator : FactListNavigator

interface FactListNavigator  {

}

/*
* interface to handle back button
* */
interface BackNavigator {

    /**
     * @param navController The navcontroller is provided by respective activity or fragment
     */
    fun navigateToBack(navController: NavController)
}