package pt.isel.pdm.quoteofdaydemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MyView(ctx: Context, attributeSet: AttributeSet?): View(ctx, attributeSet) {

    companion object {
        val brush: Paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.FILL_AND_STROKE
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (isEnabled) {
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), brush)
        }
    }
}