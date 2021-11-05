package pt.isel.pdm.quote

import java.time.LocalDate

/**
 * Represents quotes.
 * @property author - The author
 * @property text   - The quote's text
 */
data class Quote(val author: String, val text: String) {
    init {
        if (author.isBlank() || text.isBlank())
            throw IllegalArgumentException("Quotes cannot include blank strings")
    }
}

/**
 * Represents the daily quote
 */
data class QuoteOfDay(val quote: Quote, val date: String)

/**
 * Extension method that converts this quote to a JSON encoded string.
 * @return the JSON representation of this quote
 */
fun Quote.toJson(): String {
    return "{ \"author\": \"$author\", \"text\": \"$text\" }"
}
