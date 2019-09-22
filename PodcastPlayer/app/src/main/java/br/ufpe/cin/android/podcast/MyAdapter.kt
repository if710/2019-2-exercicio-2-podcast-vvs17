package br.ufpe.cin.android.podcast

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.itemlista.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class MyAdapter(private val myDataset: List<ItemFeed>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemlista, parent, false) as View

        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.apply {
            item_title.apply {
                text = myDataset[position].title

                onClick {
                    val intent = Intent(context, EpisodeDetailActivity::class.java)
                    intent.putExtra("item_title", myDataset[position].title)

                    startActivity(context, intent, null)
                }
            }

            item_date.text = myDataset[position].pubDate

            item_action.apply {
                onClick {
                    try {
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(myDataset[position].downloadLink)
                        startActivity(context, i, null)
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "Ocorreu um erro.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun getItemCount() = myDataset.size
}