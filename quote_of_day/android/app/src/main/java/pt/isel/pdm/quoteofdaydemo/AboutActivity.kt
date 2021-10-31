package pt.isel.pdm.quoteofdaydemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView

private const val GitHubURL = "https://github.com/isel-leic-pdm/PDM-2122-LI5X"

class AboutActivity : LoggingActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        findViewById<ImageView>(R.id.github_icon).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GitHubURL)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }
    }
}