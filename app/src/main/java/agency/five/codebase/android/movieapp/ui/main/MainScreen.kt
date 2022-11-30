package agency.five.codebase.android.movieapp.ui.main

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.navigation.MOVIE_ID_KEY
import agency.five.codebase.android.movieapp.navigation.MovieDetailsDestination
import agency.five.codebase.android.movieapp.navigation.NavigationItem
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesRoute
import agency.five.codebase.android.movieapp.ui.home.HomeScreenRoute
import agency.five.codebase.android.movieapp.ui.movieDetails.MovieDetailsRoute
import agency.five.codebase.android.movieapp.ui.movieDetails.MovieDetailsViewModel
import agency.five.codebase.android.movieapp.ui.theme.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val showBottomBar by remember {
        derivedStateOf {
            when (navBackStackEntry?.destination?.route) {
                MovieDetailsDestination.route -> false
                else -> true
            }
        }
    }

    val showBackIcon =
        if (navBackStackEntry?.destination?.route == NavigationItem.FavoritesDestination.route) {
            true
        } else {
            !showBottomBar
        }

    Scaffold(topBar = {
        TopBar(navigationIcon = {
            if (showBackIcon) BackIcon(onBackClick = navController::popBackStack)
        })
    }, bottomBar = {
        if (showBottomBar) BottomNavigationBar(
            destinations = listOf(
                NavigationItem.HomeDestination,
                NavigationItem.FavoritesDestination,
            ), onNavigateToDestination = { destination ->
                navController.navigate(destination.route) {
                    this.popUpTo(destination.route) {
                        inclusive = true
                    }
                }
            }, currentDestination = navBackStackEntry?.destination
        )
    }) { padding ->
        Surface(
            color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.HomeDestination.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(NavigationItem.HomeDestination.route) {
                    HomeScreenRoute(
                        homeScreenViewModel = getViewModel(),
                        onNavigateToMovieDetails = { movieId ->
                            navController.navigate(
                                MovieDetailsDestination.createNavigationRoute(
                                    movieId
                                )
                            )
                        })
                }
                composable(NavigationItem.FavoritesDestination.route) {
                    FavoritesRoute(
                        viewModel = getViewModel(),
                        onNavigateToMovieDetails = { movieId ->
                            navController.navigate(
                                MovieDetailsDestination.createNavigationRoute(
                                    movieId
                                )
                            )
                        })
                }
                composable(
                    route = MovieDetailsDestination.route,
                    arguments = listOf(navArgument(MOVIE_ID_KEY) { type = NavType.IntType }),
                ) {
                    val movieId = it.arguments?.getInt(MOVIE_ID_KEY)
                    val movieDetailsViewModel =
                        getViewModel<MovieDetailsViewModel>(parameters = { parametersOf(movieId) })
                    MovieDetailsRoute(movieDetailsViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    navigationIcon: @Composable (() -> Unit)? = null,
) {
    if (navigationIcon != null) {
        CenterAlignedTopAppBar(navigationIcon = navigationIcon,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Blue),
            title = {
                Image(
                    painter = painterResource(id = R.drawable.tmdb_logo),
                    contentDescription = stringResource(
                        id = R.string.app_name
                    )
                )
            })
    } else {
        CenterAlignedTopAppBar(colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Blue
        ),
            title = {
                Image(
                    painter = painterResource(id = R.drawable.tmdb_logo),
                    contentDescription = stringResource(
                        id = R.string.app_name
                    )
                )
            })
    }
}

@Composable
private fun BackIcon(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = { onBackClick() }, modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
            contentDescription = stringResource(id = R.string.back_icon),
            tint = Color.White
        )
    }
}

@Composable
private fun BottomNavigationBar(
    destinations: List<NavigationItem>,
    onNavigateToDestination: (NavigationItem) -> Unit,
    currentDestination: NavDestination?,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background
    ) {
        destinations.forEach { destination ->
            val selected = destination.route == currentDestination?.route
            BottomNavigationItem(selected = selected,
                selectedContentColor = Blue,
                unselectedContentColor = Color.Gray,
                label = {
                    Text(
                        text = stringResource(id = destination.labelId),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = BottomNavText
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            if (selected) destination.selectedIconId
                            else destination.unselectedIconId
                        ), contentDescription = stringResource(id = R.string.home)
                    )
                },
                onClick = { onNavigateToDestination(destination) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val dest = listOf(
        NavigationItem.HomeDestination,
        NavigationItem.FavoritesDestination,
    )

    BottomNavigationBar(dest, {}, null)
}
