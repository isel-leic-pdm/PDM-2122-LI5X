package pt.isel.pdm.quoteofdaydemo

import retrofit2.Call
import retrofit2.http.GET

data class Quote(val author: String, val text: String)

interface QuoteOfDayService {
    @GET("/")
    fun getQuote(): Call<Quote>
}