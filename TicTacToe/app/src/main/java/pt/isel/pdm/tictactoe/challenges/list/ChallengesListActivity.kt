package pt.isel.pdm.tictactoe.challenges.list

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import pt.isel.pdm.tictactoe.R
import pt.isel.pdm.tictactoe.challenges.ChallengeInfo
import pt.isel.pdm.tictactoe.challenges.create.CreateChallengeActivity
import pt.isel.pdm.tictactoe.databinding.ActivityChallengesListBinding
import pt.isel.pdm.tictactoe.game.GameActivity
import pt.isel.pdm.tictactoe.game.model.Player

/**
 * The activity used to display the list of existing challenges.
 */
class ChallengesListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityChallengesListBinding.inflate(layoutInflater) }
    private val viewModel: ChallengesListViewModel by viewModels()

    /**
     * Sets up the screen behaviour
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.challengesList.setHasFixedSize(true)
        binding.challengesList.layoutManager = LinearLayoutManager(this)

        viewModel.challenges.observe(this) { result ->
            result.onSuccess {
                binding.challengesList.adapter = ChallengesListAdapter(it, ::challengeSelected)
                binding.refreshLayout.isRefreshing = false
            }
            result.onFailure {
                Toast.makeText(this, R.string.error_getting_list, Toast.LENGTH_LONG).show()
            }
        }

        binding.refreshLayout.setOnRefreshListener { updateChallengesList() }
        binding.createChallengeButton.setOnClickListener {
            startActivity(Intent(this, CreateChallengeActivity::class.java))
        }

        viewModel.enrolmentResult.observe(this) {
            it?.onSuccess { createdGameInfo ->
                val intent = GameActivity.buildIntent(
                    origin = this,
                    turn = Player.firstToMove,
                    local = Player.firstToMove.other,
                    challengeInfo = createdGameInfo.first
                )
                startActivity(intent)
            }
        }
    }

    /**
     * The screen is about to become visible: refresh its contents.
     */
    override fun onStart() {
        super.onStart()
        updateChallengesList()
    }

    /**
     * Called whenever the challenges list is to be fetched again.
     */
    private fun updateChallengesList() {
        binding.refreshLayout.isRefreshing = true
        viewModel.fetchChallenges()
    }

    /**
     * Called whenever a list element is selected. The player that accepts the challenge is the
     * first to make a move.
     *
     * @param challenge the selected challenge
     */
    private fun challengeSelected(challenge: ChallengeInfo) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.accept_challenge_dialog_title, challenge.challengerName))
            .setPositiveButton(R.string.accept_challenge_dialog_ok) { _, _ -> viewModel.tryAcceptChallenge(challenge) }
            .setNegativeButton(R.string.accept_challenge_dialog_cancel, null)
            .create()
            .show()
    }
}