package pt.isel.pdm.tictactoe.game

import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import pt.isel.pdm.tictactoe.challenges.ChallengeInfo
import pt.isel.pdm.tictactoe.game.model.Board

/**
 * The path of the Firestore collection that contains all the active games
 */
private const val GAMES_COLLECTION = "games"

private const val GAME_STATE_KEY = "game"

/**
 * The repository for the Tic-Tac-Toe games, implemented using Firebase.
 */
class GamesRepository(private val mapper: Gson) {

    /**
     * Creates the game for the given challenge ID
     */
    fun createGame(
        challenge: ChallengeInfo,
        onComplete: (Result<Pair<ChallengeInfo, GameState>>) -> Unit
    ) {
        val gameState = Board().toGameState(challenge.id)
        Firebase.firestore.collection(GAMES_COLLECTION)
            .document(challenge.id)
            .set(hashMapOf(GAME_STATE_KEY to mapper.toJson(gameState)))
            .addOnSuccessListener { onComplete(Result.success(Pair(challenge, gameState))) }
            .addOnFailureListener { onComplete(Result.failure(it)) }
    }

    /**
     * Updates the shared game state
     */
    fun updateGameState(gameState: GameState, onComplete: (Result<GameState>) -> Unit) {

        Firebase.firestore.collection(GAMES_COLLECTION)
            .document(gameState.id)
            .set(hashMapOf(GAME_STATE_KEY to mapper.toJson(gameState)))
            .addOnSuccessListener { onComplete(Result.success(gameState)) }
            .addOnFailureListener { onComplete(Result.failure(it)) }
    }

    /**
     * Subscribes for changes in the challenge identified by [challengeId]
     */
    fun subscribeToGameStateChanges(
        challengeId: String,
        onSubscriptionError: (Exception) -> Unit,
        onGameStateChange: (GameState) -> Unit
    ): ListenerRegistration {

        return Firebase.firestore
            .collection(GAMES_COLLECTION)
            .document(challengeId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onSubscriptionError(error)
                    return@addSnapshotListener
                }

                if (snapshot?.exists() == true) {
                    val gameState = mapper.fromJson(
                        snapshot.get(GAME_STATE_KEY) as String,
                        GameState::class.java
                    )
                    onGameStateChange(gameState)
                }
            }
    }

    /**
     * Deletes the shared game state for the given challenge.
     */
    fun deleteGame(challengeId: String, onComplete: (Result<Unit>) -> Unit) {
        Firebase.firestore.collection(GAMES_COLLECTION)
            .document(challengeId)
            .delete()
            .addOnSuccessListener { onComplete(Result.success(Unit)) }
            .addOnFailureListener { onComplete(Result.failure(it)) }
    }
}

