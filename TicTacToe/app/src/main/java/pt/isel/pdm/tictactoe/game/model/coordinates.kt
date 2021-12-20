package pt.isel.pdm.tictactoe.game.model

/**
 * The Tic-Tac-Toe's board side
 */
const val TIC_TAC_TOE_SIDE = 3

/**
 * Checks whether [value] is a valid row index
 */
fun isValidRow(value: Int) = value in 0 until TIC_TAC_TOE_SIDE

/**
 * Represents a row index in a Tic-Tac-Toe board.
 *
 * @param value the row index. Must be in the interval 0 <= value < [TIC_TAC_TOE_SIDE]
 */
data class Row(val value: Int) {
    init { require(isValidRow(value)) }
}

/**
 * Int extensions for expressing row indexes.
 */
fun Int.toRow(): Row = Row(this)
val Int.Row: Row
    get() = toRow()

/**
 * Checks whether [value] is a valid column index
 */
fun isValidColumn(value: Int) = value in 0 until TIC_TAC_TOE_SIDE

/**
 * Represents a column index in a Tic-Tac-Toe board.
 * @param value the row number. Must be in the interval 0 <= value < [TIC_TAC_TOE_SIDE]
 */

data class Column(val value: Int) {
    init { require(isValidColumn(value)) }
}

/**
 * Int extensions for expressing column indexes
 */
fun Int.toColumn(): Column = Column(this)
val Int.Column: Column
    get() = toColumn()

/**
 * Represents coordinates in the Tic-Tac-Toe board
 */
data class Coordinate(val row: Row, val column: Column)

/**
 * Checks whether [value] is an index that may express a valid board coordinate
 */
fun isInCoordinateRange(value: Int) = value < TIC_TAC_TOE_SIDE * TIC_TAC_TOE_SIDE

/**
 * Extension function thaht converts this coordinate to a one dimensional index
 */
fun Coordinate.toIndex() = row.value * TIC_TAC_TOE_SIDE + column.value

/**
 * Int extensions for expressing board coordinates
 */

fun Int.toCoordinate(): Coordinate = Coordinate((this / TIC_TAC_TOE_SIDE).Row, (this % TIC_TAC_TOE_SIDE).Column)

fun Int.toCoordinateOrNull(): Coordinate? =
    if (isInCoordinateRange(this)) toCoordinate() else null

val Int.Coordinate: Coordinate
    get() = toCoordinate()


