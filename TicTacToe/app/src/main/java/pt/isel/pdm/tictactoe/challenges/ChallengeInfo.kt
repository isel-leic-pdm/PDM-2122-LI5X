package pt.isel.pdm.tictactoe.challenges

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The challenge information.
 *
 * @property [id]                   the challenge identifier
 * @property [challengerName]       the challenger name
 * @property [challengerMessage]    the challenger message
 */
@Parcelize
data class ChallengeInfo(
    val id: String,
    val challengerName: String,
    val challengerMessage: String
) : Parcelable