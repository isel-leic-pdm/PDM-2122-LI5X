package pt.isel.pdm.tictactoe.game

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pt.isel.pdm.tictactoe.TAG
import pt.isel.pdm.tictactoe.TicTacToeApplication
import pt.isel.pdm.tictactoe.game.model.Board
import pt.isel.pdm.tictactoe.game.model.Coordinate
import pt.isel.pdm.tictactoe.game.model.Player


/**
 * The Game screen view model
 */
class GameViewModel(
    app: Application,
    private val initialGameState: GameState,
    val localPlayer: Player): AndroidViewModel(app) {

    private val _game: MutableLiveData<Result<Board>> by lazy {
        MutableLiveData(Result.success(initialGameState.toBoard()))
    }
    val game: LiveData<Result<Board>> = _game

    private val gameSubscription = getApplication<TicTacToeApplication>()
        .gamesRepository.subscribeToGameStateChanges(
            challengeId = initialGameState.id,
            onSubscriptionError = { _game.value = Result.failure(it) },
            onGameStateChange = { _game.value = Result.success(it.toBoard()) }
        )

    /**
     * Makes a move at the given position. Publishes the result in [game] live data
     */
    fun makeMove(at: Coordinate) {
        game.value?.onSuccess { board ->
            Log.v(TAG, "Making move at $at")
            Log.v(TAG, "Board is ${board.toGameState(initialGameState.id)}")
            if (board.turn == localPlayer) {
                val newBoard = board.makeMove(at)
                Log.v(TAG, "Board became ${newBoard.toGameState(initialGameState.id)}")
                _game.value = Result.success(newBoard)
                getApplication<TicTacToeApplication>().gamesRepository.updateGameState(
                    gameState = newBoard.toGameState(initialGameState.id),
                    onComplete = { result ->
                        result.onFailure { _game.value = Result.failure(it) }
                    }
                )
            }
        }
    }

    /**
     * View model is destroyed
     */
    override fun onCleared() {
        super.onCleared()
        getApplication<TicTacToeApplication>().gamesRepository.deleteGame(
            challengeId = initialGameState.id,
            onComplete = { }
        )
        gameSubscription.remove()
    }
}