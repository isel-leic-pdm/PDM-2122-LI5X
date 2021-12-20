package pt.isel.pdm.tictactoe

import android.app.Application
import com.google.gson.Gson
import pt.isel.pdm.tictactoe.challenges.ChallengesRepository
import pt.isel.pdm.tictactoe.game.GamesRepository

/**
 * Tag to be used in all the application's log messages
 */
const val TAG = "TicTacToe"

/**
 * Contains the globally accessible objects
 */
class TicTacToeApplication : Application() {

    private val mapper: Gson by lazy { Gson() }

    /**
     * The challenges' repository
     */
    val challengesRepository: ChallengesRepository by lazy { ChallengesRepository() }

    /**
     * The games' repository
     */
    val gamesRepository: GamesRepository by lazy { GamesRepository(mapper) }
}
