package cmc.ati.hiltmvvmcomposetutorial.ui.component

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cmc.ati.hiltmvvmcomposetutorial.data.datasource.remote.ApiURL
import cmc.ati.hiltmvvmcomposetutorial.data.model.MovieItem
import cmc.ati.hiltmvvmcomposetutorial.data.model.moviedetail.Genre
import cmc.ati.hiltmvvmcomposetutorial.navigation.Screen
import cmc.ati.hiltmvvmcomposetutorial.navigation.currentRoute
import cmc.ati.hiltmvvmcomposetutorial.ui.theme.DefaultBackgroundColor
import cmc.ati.hiltmvvmcomposetutorial.ui.theme.Purple200
import cmc.ati.hiltmvvmcomposetutorial.ui.theme.Purple500
import cmc.ati.hiltmvvmcomposetutorial.ui.theme.SecondaryFontColor
import cmc.ati.hiltmvvmcomposetutorial.ui.theme.cornerRadius
import cmc.ati.hiltmvvmcomposetutorial.utils.conditional
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import cmc.ati.hiltmvvmcomposetutorial.utils.items
import cmc.ati.hiltmvvmcomposetutorial.utils.pagingLoadingState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun MovieItemList(
    navController: NavController,
    movies: Flow<PagingData<MovieItem>>,
    genres: ArrayList<Genre>? = null,
    selectedName: Genre?,
    onclick: (genreId: Genre?) -> Unit
) {
    val activity = (LocalContext.current as Activity)
    val progressBar = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val moviesItems: LazyPagingItems<MovieItem> = movies.collectAsLazyPagingItems()

    BackHandler(enabled = (currentRoute(navController = navController) == Screen.Home.route)) {
        openDialog.value = true
    }
    Column(modifier = Modifier.background(DefaultBackgroundColor)) {
        genres?.let {
            LazyRow(
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = 9.dp,
                        end = 9.dp
                    )
                    .fillMaxWidth()
            ) {
                items(genres) { item ->
                    SelectableGenreChip(
                        selected = item.name === selectedName?.name,
                        genre = item.name,
                        onClick = {
                            onclick(item)
                        }
                    )
                }
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = progressBar.value, verticalBias = 0.4f)
        LazyVerticalGrid(columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .conditional(genres == null) {
                    padding(top = 8.dp)
                },
            content = {
                items(moviesItems) { item ->
                    item?.let {
                        MovieItemView(item, navController)
                    }
                }
            })
    }
    if (openDialog.value) {
        ExitAlertDialog(navController, {
            openDialog.value = it
        }, {
            activity?.finish()
        })
    }
    moviesItems.pagingLoadingState {
        progressBar.value = it
    }
}

@Composable
fun MovieItemView(item: MovieItem, navController: NavController) {
    Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 10.dp)) {
        CoilImage(
            modifier = Modifier
                .size(250.dp)
                .cornerRadius(10)
                .clickable {
                    navController.navigate(Screen.MovieDetail.route.plus("/${item.id}"))
                },
            imageModel = { ApiURL.IMAGE_URL.plus(item.posterPath) },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                contentDescription = "Movie item",
                colorFilter = null,
            ),
            component = rememberImageComponent {
                +CircularRevealPlugin(
                    duration = 800
                )
                +ShimmerPlugin(
                    baseColor = SecondaryFontColor,
                    highlightColor = DefaultBackgroundColor
                )
            },
        )
    }
}

@Composable
fun SelectableGenreChip(
    selected: Boolean,
    genre: String,
    onClick: () -> Unit
) {
    val animationChipBackgroundColor by animateColorAsState(
        targetValue = if (selected) Purple500 else Purple200,
        animationSpec = tween(durationMillis = 50, delayMillis = 0, easing = LinearOutSlowInEasing),
        label = "animated"
    )
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .cornerRadius(16)
            .background(color = animationChipBackgroundColor)
            .height(32.dp)
            .widthIn(min = 80.dp)
            .padding(horizontal = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick
            }

    ) {
        Text(
            text = genre,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
            color = Color.White.copy(alpha = 0.80F)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemListPreview(

) {
    MovieItemList(
        navController = NavController(LocalContext.current),
        movies = flow { },
        selectedName = null,
        onclick = {})
}