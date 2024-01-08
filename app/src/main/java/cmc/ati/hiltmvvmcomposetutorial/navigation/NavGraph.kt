package cmc.ati.hiltmvvmcomposetutorial.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import cmc.ati.hiltmvvmcomposetutorial.data.model.moviedetail.Genre
import java.util.ArrayList

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier,
    genres: ArrayList<Genre>? = null
) {
    NavHost(navController, startDestination = Screen.Home.route, modifier) {
        composable(Screen.Home.route) {

        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}