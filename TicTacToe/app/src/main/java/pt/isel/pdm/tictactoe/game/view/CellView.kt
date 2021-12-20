package pt.isel.pdm.tictactoe.game.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import pt.isel.pdm.tictactoe.R
import pt.isel.pdm.tictactoe.game.model.*
import kotlin.math.min

private const val MIN_SIDE = 300

/**
 * Class for views that display Tic-Tac-Toe moves on the board
 */
class CellView(ctx: Context, attrs: AttributeSet?) : View(ctx, attrs) {

    /**
     * Singleton instance used to paint the view's frame
     */
    private object OutlineBrush : Paint() {
        init {
            color = Color.BLACK
            style = Style.FILL_AND_STROKE
            strokeWidth = 6F
        }
    }

    /**
     * Singleton instance used to paint the view's createdChallenge
     */
    private object ContentBrush : Paint() {
        init {
            color = Color.RED
            style = Style.STROKE
            strokeWidth = 10F
        }
    }

    /**
     * The cell's coordinates
     */
    val coordinates: Coordinate

    init {
        ctx.theme.obtainStyledAttributes(
            attrs, R.styleable.CellView, 0, 0).apply {
            try {
                coordinates = Coordinate(
                    column = getInt(R.styleable.CellView_column, 0).toColumn(),
                    row = getInt(R.styleable.CellView_row, 0).toRow()
                )
            }
            finally {
                recycle()
            }
        }
    }

    /**
     * The move occupying the tile, or null if it's empty
     */
    var move: Player? = null
        set(value) {
            field = value
            invalidate()
        }

    /**
     * Draws the current view
     */
    override fun onDraw(canvas: Canvas) {
        drawFrame(canvas)
        drawContent(canvas)
    }

    override fun getSuggestedMinimumWidth() = MIN_SIDE

    override fun getSuggestedMinimumHeight() = MIN_SIDE

    /**
     * Draws the cell's frame
     * @param [canvas]  The [Canvas] where the frame is to be drawn
     */
    private fun drawFrame(canvas: Canvas) {
        with (canvas) {
            if (coordinates.row.value == 1) {
                drawLine(0.toFloat(), 0.toFloat(), width.toFloat(), 0.toFloat(),
                    OutlineBrush
                )
                drawLine(0.toFloat(), height.toFloat(), width.toFloat(), height.toFloat(),
                    OutlineBrush
                )
            }
            if (coordinates.column.value == 1) {
                drawLine(0.toFloat(), 0.toFloat(), 0.toFloat(), height.toFloat(),
                    OutlineBrush
                )
                drawLine(width.toFloat(), 0.toFloat(), width.toFloat(), height.toFloat(),
                    OutlineBrush
                )
            }
        }
    }

    /**
     * Draws the cell's createdChallenge, according to the current display mode
     *
     * @param [canvas]  The [Canvas] where the frame is to be drawn
     */
    private fun drawContent(canvas: Canvas) {

        val availableHeight = height - paddingTop - paddingBottom
        val availableWidth = width - paddingStart - paddingEnd

        val side = min(availableHeight, availableWidth).toFloat()
        val marginW = (width - side) / 2
        val marginH = (height - side) / 2

        when (move) {
            null -> return
            Player.CROSS -> drawCross(canvas, side, marginW, marginH)
            Player.CIRCLE -> drawCircle(canvas, side)
        }
    }

    /**
     * Helper function used to draw a circle
     */
    private fun drawCircle(canvas: Canvas, side: Float) {
        canvas.drawCircle(
            width.toFloat() / 2,
            height.toFloat() / 2,
            side / 2,
            ContentBrush
        )
    }

    /**
     * Helper function used to draw a cross
     */
    private fun drawCross(canvas: Canvas, side: Float, marginW: Float, marginH: Float) {
        canvas.drawLine(
            marginW,
            marginH,
            marginW + side,
            marginH + side,
            ContentBrush
        )
        canvas.drawLine(
            marginW + side,
            marginH,
            marginW,
            marginH + side,
            ContentBrush
        )
    }
}