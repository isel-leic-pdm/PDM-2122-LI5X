package pt.isel.pdm.quoteofdaydemo.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Call
import retrofit2.http.GET

/**
 * Represents data returned by the remote API. It's [Parcelable] so that it can also be used
 * locally (in the device) to exchange data between activities and as a means to preserve state.
 * See https://martinfowler.com/bliki/LocalDTO.html by Martin Fowler
 */
@Parcelize
data class QuoteOfDayDTO(val quote: QuoteDTO, val date: String) : Parcelable

/**
 * Represents part of the data returned by the remote API. It's existence is due to the API design.
 * As simple as that.
 */
@Parcelize
data class QuoteDTO(val author: String, val text: String) : Parcelable


/**
 * The abstraction that represents accesses to the remote API's resources.
 */
interface QuoteOfDayService {
    @GET("/")
    fun getQuote(): Call<QuoteOfDayDTO>
}

/**
 * Represents errors while accessing the remote API. Instead of tossing around Retrofit errors,
 * we can use this exception to wrap them up.
 */
class ServiceUnavailable(message: String = "", cause: Throwable? = null) : Exception(message, cause)