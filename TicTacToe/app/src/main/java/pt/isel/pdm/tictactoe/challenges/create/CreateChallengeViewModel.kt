package pt.isel.pdm.tictactoe.challenges.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ListenerRegistration
import pt.isel.pdm.tictactoe.TicTacToeApplication
import pt.isel.pdm.tictactoe.challenges.ChallengeInfo

/**
 * The View Model to be used in the [CreateChallengeActivity].
 *
 * Challenges are created by participants and are posted on the server, awaiting acceptance.
 */
class CreateChallengeViewModel(app: Application) : AndroidViewModel(app) {

    /**
     * Used to publish the result of the challenge creation operation. Null if no challenge is
     * currently published.
     */
    private val _created: MutableLiveData<Result<ChallengeInfo>?> = MutableLiveData(null)
    val created: LiveData<Result<ChallengeInfo>?> = _created

    /**
     * Used to publish the acceptance state of the challenge
     */
    private val _accepted: MutableLiveData<Boolean> = MutableLiveData(false)
    val accepted: LiveData<Boolean> = _accepted

    /**
     * Creates a challenge with the given arguments. The result is placed in [created]
     */
    fun createChallenge(name: String, message: String) {
        getApplication<TicTacToeApplication>().challengesRepository.publishChallenge(
            name = name,
            message = message,
            onComplete = {
                _created.value = it
                it.onSuccess(::waitForAcceptance)
            }
        )
    }

    /**
     * Withdraws the current challenge from the list of available challenges.
     * @throws IllegalStateException if there's no challenge currently published
     */
    fun removeChallenge() {
        val currentChallenge = created.value
        check(currentChallenge != null && currentChallenge.isSuccess)
        val repo = getApplication<TicTacToeApplication>().challengesRepository
        subscription?.let { repo.unsubscribeToChallengeAcceptance(it) }
        currentChallenge.onSuccess {
            repo.withdrawChallenge(
                challengeId = it.id,
                onComplete = { _created.value = null }
            )
        }
    }

    /**
     * Lets cleanup. The view model is about to be destroyed.
     */
    override fun onCleared() {
        if (created.value != null && created.value?.isSuccess == true)
            removeChallenge()
    }

    private var subscription: ListenerRegistration? = null

    private fun waitForAcceptance(challengeInfo: ChallengeInfo) {
        subscription = getApplication<TicTacToeApplication>().challengesRepository.subscribeToChallengeAcceptance(
            challengeId = challengeInfo.id,
            onSubscriptionError = { _created.value = Result.failure(it) },
            onChallengeAccepted = { _accepted.value = true },
        )
    }
}
