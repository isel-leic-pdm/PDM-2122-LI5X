package pt.isel.pdm.tictactoe.game

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pt.isel.pdm.tictactoe.game.model.Board
import pt.isel.pdm.tictactoe.game.model.Player

/**
 * Data type used to represent the game state externally, that is, when the game state crosses
 * process boundaries and device boundaries.
 */
@Parcelize
data class GameState(
    val id: String,
    val turn: String?,
    val board: String
): Parcelable

/**
 * Extension to create a [GameState] instance from this [Board].
 */
fun Board.toGameState(gameId: String): GameState {
    val moves: String = toList().map {
        when(it) {
            null -> ' '
            Player.CROSS -> 'X'
            Player.CIRCLE -> 'O'
        }
    }.joinToString(separator = "")

    return GameState(id = gameId, turn = turn?.name, board = moves)
}

/**
 * Extension to create a [Board] instance from this [GameState].
 */
fun GameState.toBoard() = Board(
    turn = if (turn != null) Player.valueOf(turn) else null,
    board = board.toBoardContents()
)

/**
 * Extension to create a list of moves from this string
 */
private fun String.toBoardContents(): List<Player?> = this.map {
    when(it) {
        'X' -> Player.CROSS
        'O' -> Player.CIRCLE
        else -> null
    }
}