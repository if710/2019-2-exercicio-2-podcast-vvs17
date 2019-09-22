package br.ufpe.cin.android.podcast

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_episode_detail.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EpisodeDetailActivity : AppCompatActivity() {

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_detail)

        // Takes the ItemFeed object passed by the main activity
        val itemDetails = intent.getParcelableExtra("item_details") as ItemFeed?

        if (itemDetails != null) {
            val episodeImage = Picasso.get().load(itemDetails.imageLink)
            episodeImage.into(episode_image)
            episode_image.visibility = View.VISIBLE

            episode_title.text = itemDetails.title

            // Formats the episode's description
            episode_description.text =
                HtmlCompat.fromHtml(itemDetails.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    .trim()

            episode_link.apply {
                // Opens the episode's link
                onClick {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(itemDetails.link)
                    startActivity(i)
                }
            }
        }
    }
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_detail)

        episode_title.text = intent.getStringExtra("item_title")
        doAsync {
            val db = PodcastDatabase.getDatabase(this@EpisodeDetailActivity)

            val episode = db.podcastDAO().getItem(episode_title.text!!.toString())

            episode_description.text = episode.description
            episode_date.text = episode.pubDate


            uiThread{
                Picasso.get().load(episode.imageLink).into(episode_image)
            }
        }
    }
}
