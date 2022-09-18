package com.example.flixster

import org.json.JSONArray

data class Movie(
    val title: String,
    val movieId: Int,
    val overview: String,
    private val posterPath: String,
    val backdropPath: String
) {
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/${posterPath}"
    val backdropImageUrl = "https://image.tmdb.org/t/p/w342/${backdropPath}"
    companion object {
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>()
            for (i in 0 until movieJsonArray.length()){
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getString("title"),
                        movieJson.getInt("id"),
                        movieJson.getString("overview"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("backdrop_path")
                    )
                )
            }
            return movies
        }
    }
}