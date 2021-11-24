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
        add(Quote(
            author = "Frederick P. Brooks Jr.",
            text = "Good judgement comes from experience, and experience comes from bad judgement."
        ))
        add(Quote(
            author = "Frederick P. Brooks Jr.",
            text = "The programmer, like the poet, works only slightly removed from pure thought-stuff. " +
                    "He builds his castles in the air, from air, creating by exertion of the imagination. " +
                    "Few media of creation are so flexible, so easy to polish and rework, so readily capable of " +
                    "realizing grand conceptual structures. ... Yet the program construct, unlike the poet's words, " +
                    "is real in the sense that it moves and works, producing visible outputs separate from the " +
                    "construct itself. It prints results, draws pictures, produces sounds, moves arms. " +
                    "The magic of myth and legend has come true in our time. One types the correct incantation " +
                    "on a keyboard, and a display screen comes to life, showing things that never were nor could be."
        ))
        add(Quote(
            author = "Frederick P. Brooks Jr.",
            text = "A scientist builds in order to learn; an engineer learns in order to build."
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