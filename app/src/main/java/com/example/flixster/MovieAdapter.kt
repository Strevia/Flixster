package com.example.flixster

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


const val MOVIE_EXTRA = "MOVIE_EXTRA"
class MovieAdapter(private val context: Context, private val movies: List<Movie>)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder (itemView), View.OnClickListener {
        private val orientation = context.resources.configuration.orientation
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val tvPoster = itemView.findViewById<ImageView>(R.id.tvPoster)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(context).load(movie.posterImageUrl)
                    .placeholder(R.drawable.placeholder)
                    .transform(CenterCrop(), RoundedCorners(50))
                    .into(tvPoster)
            }
            else {
                Glide.with(context).load(movie.backdropImageUrl).placeholder(R.drawable.placeholder)
                    .into(tvPoster)
            }
        }

        override fun onClick(p0: View?) {
            val movie = movies[adapterPosition]

            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movie)
            val titleView = Pair.create(itemView.findViewById<TextView>(R.id.tvTitle) as View, "title")
            val descView = Pair.create(itemView.findViewById<TextView>(R.id.tvOverview) as View, "desc")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, titleView, descView)
            context.startActivity(intent, options.toBundle())
        }
    }
}
