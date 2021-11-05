package pt.isel.pdm.quote

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import spark.Service.ignite

private const val DEFAULT_PORT = 8080

private val logger = LoggerFactory.getLogger("Quote-Of-Day server")

/**
 * The server's entry point.
 */
fun main(args: Array<String>) {

    logger.info("Starting ...")
    val port = if (args.size > 1) args[1].toIntOrNull() ?: DEFAULT_PORT else DEFAULT_PORT
    val quoteOfDayService = QuoteOfDayService()

    val http = ignite().port(port)
    http.get("/") { _, response ->
        response.header("Content-Type", "application/json")
        val quote = jacksonObjectMapper().writeValueAsString(quoteOfDayService.getQuoteForToday())
        logger.info("Served GET request to \"/\". Payload was $quote")
        quote
    }

    logger.info("Ready and listening on port $port.")
}