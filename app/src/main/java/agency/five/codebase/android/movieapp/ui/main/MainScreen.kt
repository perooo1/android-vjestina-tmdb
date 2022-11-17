package agency.five.codebase.android.movieapp.ui.main

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.navigation.MOVIE_ID_KEY
import agency.five.codebase.android.movieapp.navigation.MovieDetailsDestination
import agency.five.codebase.android.movieapp.navigation.NavigationItem
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesRoute
import agency.five.codebase.android.movieapp.ui.home.HomeScreenRoute
import agency.five.codebase.android.movieapp.ui.movieDetails.MovieDetailsRoute
import agency.five.codebase.android.movieapp.ui.theme.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar by remember {
        // your code goes here ...
    }

    val showBackIcon = !showBottomBar
    Scaffold(
        topBar = {
            TopBar(
                navigationIcon = {
                    if (showBackIcon) BackIcon(onBackClick = navController::popBackStack)
                }
            )
        },
        bottomBar = {
            if (showBottomBar)
                BottomNavigationBar(
                    destinations = listOf(
                        NavigationItem.HomeDestination,
                        NavigationItem.FavoritesDestination,
                    ),
                    onNavigateToDestination =,// your code goes here ...,
                    currentDestination = navBackStackEntry?.destination
                )
        }
    ) { padding ->
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.HomeDestination.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(NavigationItem.HomeDestination.route) {
                    HomeScreenRoute(
                        onNavigateToMovieDetails = // your code goes here ...
                    )
                }
                composable(NavigationItem.FavoritesDestination.route) {
                    FavoritesRoute(
                        onNavigateToMovieDetails = // your code goes here ...
                    )
                }
                composable(
                    route = MovieDetailsDestination.route,
                    arguments = listOf(navArgument(MOVIE_ID_KEY) { type = NavType.IntType }),
                ) {
                    MovieDetailsRoute()
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
        CenterAlignedTopAppBar(
            navigationIcon = navigationIcon,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Blue),
            title = {
                Image(
                    painter = painterResource(id = R.drawable.tmdb_logo),
                    contentDescription = stringResource(
                        id = R.string.app_name
                    )
                )
            }
        )
    } else {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Blue),
            title = {
                Image(
                    painter = painterResource(id = R.drawable.tmdb_logo),
                    contentDescription = stringResource(
                        id = R.string.app_name
                    )
                )
            }
        )
    }
}

@Composable
private fun BackIcon(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = { onBackClick() },
        modifier = modifier
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
            BottomNavigationItem(
                selected = selected,
                selectedContentColor = Blue,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (selected) {
                            Icon(
                                painter = painterResource(id = destination.selectedIconId),
                                contentDescription = stringResource(id = R.string.home)
                            )
                            Text(
                                text = stringResource(id = destination.labelId),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = BottomNavText
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = destination.unselectedIconId),
                                contentDescription = stringResource(id = R.string.favorites)
                            )
                            Text(
                                text = stringResource(id = destination.labelId),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = BottomNavText
                            )
                        }
                    }
                },
                onClick = { onNavigateToDestination(destination) }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    MovieAppTheme {
        TopBar(navigationIcon = { BackIcon(onBackClick = {}) })
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    MovieAppTheme {

        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val destinations = listOf(
            NavigationItem.HomeDestination,
            NavigationItem.FavoritesDestination,
        )

        BottomNavigationBar(
            destinations = destinations,
            onNavigateToDestination = {},
            currentDestination = navBackStackEntry?.destination
        )
    }
}

