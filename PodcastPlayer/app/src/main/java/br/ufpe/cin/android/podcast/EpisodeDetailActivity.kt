package br.ufpe.cin.android.podcast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_episode_detail.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EpisodeDetailActivity : AppCompatActivity() {

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
