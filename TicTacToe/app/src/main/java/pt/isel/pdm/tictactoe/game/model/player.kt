package pt.isel.pdm.tictactoe.game.model

/**
 * Enumeration type used to represent the game's players.
 */
enum class Player {

    CIRCLE, CROSS;

    companion object {
        val firstToMove: Player = CIRCLE
    }

    /**
     * The other player
     */
    val other: Player
        get() = if (this == CIRCLE) CROSS else CIRCLE
}

