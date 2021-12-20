package pt.isel.pdm.tictactoe.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TableRow
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.pdm.tictactoe.TAG
import pt.isel.pdm.tictactoe.challenges.ChallengeInfo
import pt.isel.pdm.tictactoe.databinding.ActivityGameBinding
import pt.isel.pdm.tictactoe.game.model.Board
import pt.isel.pdm.tictactoe.game.model.Player
import pt.isel.pdm.tictactoe.game.view.CellView

private const val GAME_EXTRA = "GameActivity.GameInfoExtra"
private const val LOCAL_PLAYER_EXTRA = "GameActivity.LocalPlayerExtra"

/**
 * The activity that displays the board.
 */
class GameActivity : AppCompatActivity() {

    companion object {
        fun buildIntent(origin: Context, local: Player, turn: Player, challengeInfo: ChallengeInfo) =
            Intent(origin, GameActivity::class.java)
                .putExtra(GAME_EXTRA, Board(turn = turn).toGameState(challengeInfo.id))
                .putExtra(LOCAL_PLAYER_EXTRA, local.name)
    }

    private val binding: ActivityGameBinding by lazy { ActivityGameBinding.inflate(layoutInflater) }

    private val localPlayer: Player by lazy {
        val local = intent.getStringExtra(LOCAL_PLAYER_EXTRA)
        if (local != null) Player.valueOf(local)
        else throw IllegalArgumentException("Mandatory extra $LOCAL_PLAYER_EXTRA not present")
    }

    private val initialState: GameState by lazy {
        intent.getParcelableExtra<GameState>(GAME_EXTRA) ?:
        throw IllegalArgumentException("Mandatory extra $GAME_EXTRA not present")
    }

    private val viewModel: GameViewModel by viewModels {
        @Suppress("UNCHECKED_CAST")
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return GameViewModel(application, initialState, localPlayer) as T
            }
        }
    }

    /**
     * Callback method that handles the activity initiation
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.v(TAG, "GameActivity.onCreate()")
        Log.v(TAG, "Local player is $localPlayer")
        Log.v(TAG, "Turn player is ${initialState.turn}")
        viewModel.game.observe(this) { updateUI() }
        binding.forfeitButton.setOnClickListener {
            // TODO: The chicken path
        }
    }

    /**
     * Used to update de board view according to the current state of the game
     */
    private fun updateBoard() {

        binding.forfeitButton.isClickable =
            if (viewModel.game.value?.isSuccess == true)
                viewModel.localPlayer == viewModel.game.value?.getOrThrow()?.turn
            else false

        // TODO: flatmap this dude up
        binding.board.children.forEach { row ->
            (row as? TableRow)?.children?.forEach { tile ->
                if (tile is CellView) {
                    viewModel.game.value?.onSuccess {
                        tile.move = it.getMove(tile.coordinates)
                    }
                }
            }
        }
    }

    /**
     * Used to update the UI according to the current state of the game
     */
    private fun updateUI() {
        updateBoard()
    }

    /**
     * Makes a move on the given cell
     *
     * @param [view] The cell where the move has been made on
     */
    fun handleMove(view: View) {
        val cell = view as CellView
        viewModel.makeMove(cell.coordinates)
    }
}