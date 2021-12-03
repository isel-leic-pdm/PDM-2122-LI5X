package pt.isel.pdm.quoteofdaydemo.history

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import pt.isel.pdm.quoteofdaydemo.R
import pt.isel.pdm.quoteofdaydemo.common.QuoteOfDayDTO

private const val QUOTE_EXTRA = "QuoteActivityLegacy.Extra.Quote"

/**
 * The screen used to display the a quote.
 */
class QuoteActivity : AppCompatActivity() {

    companion object {
        fun buildIntent(origin: Activity, quoteDto: QuoteOfDayDTO): Intent {
            val msg = Intent(origin, QuoteActivity::class.java)
            msg.putExtra(QUOTE_EXTRA, quoteDto)
            return msg
        }
    }

    /**
     * Sets up the screen look and behaviour
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)

        val quoteDto = intent.getParcelableExtra<QuoteOfDayDTO>(QUOTE_EXTRA)
        findViewById<TextView>(R.id.quote_author).text = quoteDto?.quote?.author
        findViewById<TextView>(R.id.quote_text).text = quoteDto?.quote?.text
    }
}