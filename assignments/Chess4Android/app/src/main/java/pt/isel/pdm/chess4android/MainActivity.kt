package pt.isel.pdm.chess4android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import pt.isel.pdm.chess4android.databinding.ActivityMainBinding
import pt.isel.pdm.chess4android.views.Tile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random
import kotlin.random.nextInt

val dailyPuzzleService: DailyPuzzleService = Retrofit.Builder()
    .baseUrl(URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(DailyPuzzleService::class.java)


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val call = dailyPuzzleService.getPuzzle()
        call.enqueue(object : Callback<PuzzleInfo> {
            override fun onResponse(call: Call<PuzzleInfo>, response: Response<PuzzleInfo>) {
                Log.v("Chess4Android", "onResponse")
            }
            override fun onFailure(call: Call<PuzzleInfo>, t: Throwable) {
                Log.v("Chess4Android", "onFailure")
            }
        })

        binding.boardView.onTileClickedListener = { tile: Tile, row: Int, column: Int ->
            val randomArmy = Army.values()[Random.nextInt(Army.values().indices)]
            val randomPiece = Piece.values()[Random.nextInt(Piece.values().indices)]
            tile.piece = Pair(randomArmy, randomPiece)
        }
    }
}