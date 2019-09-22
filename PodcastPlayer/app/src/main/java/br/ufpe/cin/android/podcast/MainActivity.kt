package br.ufpe.cin.android.podcast

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val podcastURL = "https://feeds.megaphone.fm/thecuriousinvestor"
        runFeed(podcastURL)
    }

    private fun runFeed(podcastURL: String) {
        val db = PodcastDatabase.getDatabase(this)
        // val itemFeed: List≤ItemFeed>? = null

        doAsync {
            try {
                val feed = URL(podcastURL).readText()
                // val (title) = Parser.parse(feed)

                val itemFeed = Parser.parse(feed)

                if (itemFeed != null) itemFeed.forEach {
                        itemFeed -> db.podcastDAO().insertItemFeed(itemFeed)
                } else throw NullPointerException("Expression 'itemFeed' must not be null")

                val savedFeed = db.podcastDAO().getAll()

                uiThread {
                    showFeed(it, savedFeed)
                }

            } catch (e: Exception) {
                uiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Ocorreu um erro.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            //val savedFeed = db.podcastDAO().getAll()

        }
    }

    fun showFeed(ctx: Context, podcastDataset: List<ItemFeed>) {
        podcastRecyclerView.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(ctx)

            // specify an viewAdapter (see also next example)
            adapter = MyAdapter(podcastDataset)

            addItemDecoration(
                DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation)
            )
        }
    }
}