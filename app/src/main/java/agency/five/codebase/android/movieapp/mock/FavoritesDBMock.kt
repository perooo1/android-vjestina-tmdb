package agency.five.codebase.android.movieapp.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object FavoritesDBMock {

    private val _favoriteIds = MutableStateFlow(setOf<Int>())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds.asStateFlow()

    fun insert(movieId: Int) = _favoriteIds.update { it.plus(movieId) }
    fun delete(movieId: Int) = _favoriteIds.update { it.minus(movieId) }

}
