package cmc.ati.hiltmvvmcomposetutorial.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import cmc.ati.hiltmvvmcomposetutorial.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int = R.string.app_title,
    val navIcon: (@Composable () -> Unit) = {
        Icon(Icons.Filled.Home, contentDescription = "home")
    },
    val objectName: String = "",
    val objectPath: String = ""
) {
    data object Login : Screen("login_screen")
    data object Home : Screen("home_screen")
    data object Popular : Screen("popular_screen")
    data object TopRated : Screen("top_rated_screen")
    data object UpComing : Screen("upcoming_screen")
    data object NavigationDrawer :
        Screen("navigation_drawer", objectName = "genreId", objectPath = "/{genreId}")

    data object MovieDetail :
        Screen("movie_detail_screen", objectName = "movieItem", objectPath = "/{movieItem}")
}