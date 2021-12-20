package pt.isel.pdm.tictactoe.game.model

/**
 * Represents a Tic-Tac-Toe board. Instances are immutable.
 *
 * @property side   The number of tiles per board side
 * @property turn   The next player to move, or null if the game has already ended
 * @property board  The board tiles
 */
data class Board(
    val side: Int = TIC_TAC_TOE_SIDE,
    val turn: Player? = Player.firstToMove,
    private val board: List<Player?> = List(size = TIC_TAC_TOE_SIDE * TIC_TAC_TOE_SIDE, init = { null })
) {

    /**
     * Overloads the indexing operator
     */
    operator fun get(at: Coordinate): Player? = getMove(at)

    /**
     * Gets the move at the given coordinates.
     *
     * @param at    the move's coordinates
     * @return the [Player] instance that made the move, or null if the position is empty
     */
    fun getMove(at: Coordinate): Player? = board[at.toIndex()]

    /**
     * Makes a move at the given coordinates and returns the new board instance.
     *
     * @param at    the board's coordinate
     * @throws IllegalArgumentException if the position is already occupied
     * @throws IllegalStateException if the game has already ended
     * @return the new board instance
     */
    fun makeMove(at: Coordinate): Board {
        require(this[at] == null)
        checkNotNull(turn)
        return Board(
            turn = turn.other,
            board = board.mapIndexed { index, elem ->
                if (index == at.toIndex()) turn
                else elem
            }
        )
    }

    /**
     * Converts this instance to a list of moves.
     */
    fun toList(): List<Player?> = board
}

/**
 * Extension function that overloads the indexing operator for [Board] instances
 */
operator fun Board.get(row: Row, column: Column): Player? =
    getMove(Coordinate(row, column))

/**
 * Extension function that checks whether this board's position [at] is free or not
 *
 * @param at    the board's coordinate
 * @return true if the board position is free, false otherwise
 */
fun Board.isFree(at: Coordinate): Boolean = this[at] == null

/**
 * Extension function that checks whether this board's position [at] is free or not
 *
 * @param at    the board's coordinate
 * @return true if the board position is occupied, false otherwise
 */
fun Board.isNotFree(at: Coordinate): Boolean = !isFree(at)

/**
 * Extension function that checks whether this board represents a tied game or not
 *
 * @return true if the board is a tied game, false otherwise
 */
fun Board.isTied(): Boolean =
    toList().all { it != null }


