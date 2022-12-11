package agency.five.codebase.android.movieapp.data.network.model

import agency.five.codebase.android.movieapp.model.Crewman
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCrew(         //from GET /movie/{movie_id}/credits endpoint documentation
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("job")
    val job: String

) {
    fun toCrewman() = Crewman(
        id = id,
        name = name,
         job = job
    )
}
