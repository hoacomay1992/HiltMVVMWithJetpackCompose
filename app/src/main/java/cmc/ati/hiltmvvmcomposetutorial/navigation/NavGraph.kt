package cmc.ati.hiltmvvmcomposetutorial.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cmc.ati.hiltmvvmcomposetutorial.data.model.moviedetail.Genre
import java.util.ArrayList

@Composable
fun Navigation(navController: NavController, modifier: Modifier, genres: ArrayList<Genre>? = null) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier){
        composable(Screen.Home.route){
            Now
        }
    }
}