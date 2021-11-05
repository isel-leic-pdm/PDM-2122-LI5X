package pt.isel.pdm.quote

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Service that provides the quote for the day.
 */
class QuoteOfDayService {

    private val quotes: List<Quote> = mutableListOf<Quote>().apply {
        add(Quote(
            author = "Alan Greenspan",
            text = "I know you think you understand what you thought " +
                    "I said but I'm not sure you realize that what you heard is not what I meant."
        ))
        add(Quote(
            author = "Epitectus",
            text = "Practice yourself, for heaven's sake, in little things; and thence proceed to greater"
        ))
    }

    /**
     * Gets the quote for the day.
     * @return Today's quote
     */
    fun getQuoteForToday() = QuoteOfDay(
            quotes[LocalDateTime.now().dayOfYear % quotes.size],
            LocalDate.now().toString()
        )
}