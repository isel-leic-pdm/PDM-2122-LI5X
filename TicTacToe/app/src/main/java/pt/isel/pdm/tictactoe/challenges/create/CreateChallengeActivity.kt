package pt.isel.pdm.tictactoe.challenges.create

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import pt.isel.pdm.tictactoe.R
import pt.isel.pdm.tictactoe.TAG
import pt.isel.pdm.tictactoe.databinding.ActivityCreateChallengeBinding
import pt.isel.pdm.tictactoe.game.GameActivity
import pt.isel.pdm.tictactoe.game.model.Player

/**
 * The activity used to create a new challenge.
 */
class CreateChallengeActivity : AppCompatActivity() {

    private val viewModel: CreateChallengeViewModel by viewModels()
    private val binding: ActivityCreateChallengeBinding by lazy {
        ActivityCreateChallengeBinding.inflate(layoutInflater)
    }

    /**
     * Callback method that handles the activity initiation
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.created.observe(this) {
            if (it == null) displayCreateChallenge()
            else it.onFailure { displayError() }.onSuccess {
                displayWaitingForChallenger()
            }
        }

        viewModel.accepted.observe(this) {
            if (it == true) {
                Log.v(TAG, "Someone accepted our challenge")
                viewModel.created.value?.onSuccess { challenge ->
                    val intent = GameActivity.buildIntent(
                        origin = this,
                        turn = Player.firstToMove,
                        local = Player.firstToMove,
                        challengeInfo = challenge
                    )
                    startActivity(intent)
                }
            }
        }

        binding.action.setOnClickListener {
            if (viewModel.created.value == null)
                viewModel.createChallenge(
                    binding.name.text.toString(),
                    binding.message.text.toString()
                )
            else viewModel.removeChallenge()
        }
    }

    /**
     * Displays the screen in its Create challenge state
     */
    private fun displayCreateChallenge() {
        binding.action.text = getString(R.string.create_challenge_button_label)
        with(binding.name) { text.clear(); isEnabled = true }
        with(binding.message) { text.clear(); isEnabled = true }
        binding.loading.isVisible = false
        binding.waitingMessage.isVisible = false
    }

    /**
     * Displays the screen in its Waiting for challenge state
     */
    private fun displayWaitingForChallenger() {
        binding.action.text = getString(R.string.cancel_challenge_button_label)
        binding.name.isEnabled = false
        binding.message.isEnabled = false
        binding.loading.isVisible = true
        binding.waitingMessage.isVisible = true
    }

    /**
     * Displays the screen in its error creating challenge state
     */
    private fun displayError() {
        displayCreateChallenge()
        Toast
            .makeText(this, R.string.error_creating_challenge, Toast.LENGTH_LONG)
            .show()
    }
}
