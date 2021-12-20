package pt.isel.pdm.tictactoe.challenges.list

import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.tictactoe.R
import pt.isel.pdm.tictactoe.challenges.ChallengeInfo


/**
 * Represents views (actually, the corresponding holder) that display the information pertaining to
 * a [ChallengeInfo] instance
 */
class ChallengeViewHolder(private val view: ViewGroup) : RecyclerView.ViewHolder(view) {

    private val challengerNameView: TextView = view.findViewById(R.id.challengerName)
    private val challengerMessageView: TextView = view.findViewById(R.id.message)

    /**
     * Starts the item selection animation and calls [onAnimationEnd] once the animation ends
     */
    private fun startAnimation(onAnimationEnd: () -> Unit) {

        val animation = ValueAnimator.ofArgb(
            ContextCompat.getColor(view.context, R.color.list_item_background),
            ContextCompat.getColor(view.context, R.color.list_item_background_selected),
            ContextCompat.getColor(view.context, R.color.list_item_background)
        )

        animation.addUpdateListener { animator ->
            val background = view.background as GradientDrawable
            background.setColor(animator.animatedValue as Int)
        }

        animation.duration = 400
        animation.start()

        animation.doOnEnd { onAnimationEnd() }
    }

    /**
     * Used to create an association between the current view holder instance and the given
     * data item
     *
     * @param   challenge               the challenge data item
     * @param   itemSelectedListener    the function to be called whenever the item is selected
     */
    fun bindTo(challenge: ChallengeInfo?, itemSelectedListener: (ChallengeInfo) -> Unit) {
        challengerNameView.text = challenge?.challengerName ?: ""
        challengerMessageView.text = challenge?.challengerMessage ?: ""

        if (challenge != null)
            view.setOnClickListener {
                itemView.isClickable = false
                startAnimation {
                    itemSelectedListener(challenge)
                    itemView.isClickable = true
                }
            }
    }
}

/**
 * Adapts [ChallengeInfo] instances to be displayed in a [RecyclerView]
 */
class ChallengesListAdapter(
    private val contents: List<ChallengeInfo> = emptyList(),
    private val itemSelectedListener: (ChallengeInfo) -> Unit = { }) :
    RecyclerView.Adapter<ChallengeViewHolder>() {

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        holder.bindTo(contents[position], itemSelectedListener)
    }

    override fun getItemCount(): Int = contents.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false) as ViewGroup

        return ChallengeViewHolder(view)
    }
}


