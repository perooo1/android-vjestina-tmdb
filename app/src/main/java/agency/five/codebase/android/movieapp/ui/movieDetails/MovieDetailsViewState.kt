package agency.five.codebase.android.movieapp.ui.movieDetails


data class CrewmanViewState(
    val name: String,
    val job: String
)

data class ActorViewState(
    val name: String,
    val character: String,
    val imageUrl: String?
)

data class MovieDetailsViewState(
    val id: Int,
    val imageUrl: String?,
    val voteAverage: Float,
    val title: String,
    val overview: String,
    val isFavorite: Boolean,
    val crew: List<CrewmanViewState>,
    val cast: List<ActorViewState>,
)
