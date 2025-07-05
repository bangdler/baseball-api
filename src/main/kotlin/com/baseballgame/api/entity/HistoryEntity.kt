import com.baseballgame.api.domain.History
import jakarta.persistence.Embeddable

@Embeddable
data class HistoryEntity(
    val id: Long,
    val input: String,
    val strike: Int,
    val ball: Int
) {
    fun toDomain(): History = History(id, input, strike, ball)

    companion object {
        fun from(domain: History): HistoryEntity =
            HistoryEntity(id = domain.id, input = domain.input, strike = domain.strike, ball = domain.ball)
    }
}