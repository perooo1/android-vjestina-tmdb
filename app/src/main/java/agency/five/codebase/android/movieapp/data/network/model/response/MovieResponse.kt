package agency.five.codebase.android.movieapp.data.network.model.response

import agency.five.codebase.android.movieapp.data.network.model.ApiMovie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("results")
    val movies: List<ApiMovie>
)
