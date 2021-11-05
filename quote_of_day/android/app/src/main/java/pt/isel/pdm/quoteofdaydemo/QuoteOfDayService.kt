package pt.isel.pdm.quoteofdaydemo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Call
import retrofit2.http.GET

@Parcelize
data class Quote(val author: String, val text: String) : Parcelable

interface QuoteOfDayService {
    @GET("/")
    fun getQuote(): Call<Quote>
}