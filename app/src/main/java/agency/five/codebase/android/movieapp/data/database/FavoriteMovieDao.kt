package agency.five.codebase.android.movieapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM favoriteMovies")
    fun favorites(): Flow<List<DbFavoriteMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: DbFavoriteMovie)

    @Query("DELETE FROM favoriteMovies WHERE id=:id")
    suspend fun deleteMovie(id: Int)
}
