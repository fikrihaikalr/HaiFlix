package com.fikrihaikal.haiflix.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fikrihaikal.haiflix.R
import com.fikrihaikal.haiflix.core.data.source.Resource
import com.fikrihaikal.haiflix.core.domain.model.Movie
import com.fikrihaikal.haiflix.core.utils.commonImageUrl
import com.fikrihaikal.haiflix.core.utils.loadImage
import com.fikrihaikal.haiflix.core.utils.toDateFormat
import com.fikrihaikal.haiflix.core.utils.transparentStatusBar
import com.fikrihaikal.haiflix.databinding.ActivityDetailBinding
import com.fikrihaikal.haiflix.ui.detail.adapter.CasterAdapter
import com.fikrihaikal.haiflix.ui.detail.adapter.TrailerAdapter
import com.fikrihaikal.haiflix.ui.home.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var detailMovie: Movie
    private val castertAdapter: CasterAdapter by lazy {
        CasterAdapter{}
    }
    private val trailerAdapter: TrailerAdapter by lazy {
        TrailerAdapter{}
    }
    private val viewModel: DetailViewModel by viewModels()
    private var id by Delegates.notNull<Int>()
    private var isFavorite by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarDetail.setNavigationOnClickListener {
            onBackPressed()
        }
        this.transparentStatusBar()
        val bundle = intent.extras
        val movie = bundle?.getParcelable<Movie>(EXTRA_MOVIE_ID)
        if (movie != null){
            detailMovie = movie
            id = movie.id!!
        }
        binding.apply {
            imgDetailBackground.loadImage(this@DetailActivity, movie?.img?.commonImageUrl(),true)
            Glide.with(this@DetailActivity).load(movie?.img).into(ivMoviePoster)
            tvReleaseDate.text = movie?.releaseDate.toDateFormat()
            collapsingToolbar.title = movie?.name
            tvRating.text = movie?.voteAverage.toString()
            tvRatersVote.text = movie?.voteCount.toString()
            tvOverview.text = movie?.overview
        }
        viewModel.getCastAndCrew(id)
        viewModel.getMovieVideo(id)
        movie?.id?.let {
            viewModel.isFavorite(it).observe(this){favorite ->
                binding.ivFavorite.apply {
                    if (favorite == true){
                        setImageResource(R.drawable.baseline_favorite_24)
                        setColorFilter(
                            ContextCompat.getColor(context,R.color.red),
                            android.graphics.PorterDuff.Mode.SRC_IN
                        )
                    }else{
                        setImageResource(R.drawable.baseline_favorite_border_24)
                        setColorFilter(
                            ContextCompat.getColor(context,R.color.grey),
                            android.graphics.PorterDuff.Mode.SRC_IN
                        )
                    }
                    isFavorite = favorite
                }
                favoriteOnClick()
            }
        }
        observeData()
        setupRecyclerView()
    }

    private fun favoriteOnClick() {
        binding.ivFavorite.apply {
            setOnClickListener{
                if (!isFavorite){
                    viewModel.insertMovie(detailMovie)
                    setImageResource(R.drawable.baseline_favorite_24)
                    isFavorite = true
                }else{
                    viewModel.deleteMovie(detailMovie)
                    setImageResource(R.drawable.baseline_favorite_border_24)
                    isFavorite = true
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvCast.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
            adapter = this@DetailActivity.castertAdapter
        }
        binding.rvTrailer.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
            adapter = this@DetailActivity.trailerAdapter
        }
    }

    private fun observeData() = binding.run {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.castResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            rvCast.visibility = View.VISIBLE
                            pbCast.visibility = View.GONE
                            it.data?.let { movies ->
                                castertAdapter.setItems(movies.shuffled())
                            }
                            Log.d("succes",it.data.toString())
                        }
                        is Resource.Loading -> {
                            rvCast.visibility = View.GONE
                            pbCast.visibility = View.VISIBLE
                        }
                        is Resource.Error -> {
                            Log.d("error",it.message.toString())
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.movieVideoResponse.collect{
                    when(it){
                        is Resource.Success ->{
                            rvTrailer.visibility = View.VISIBLE
                            pbTrailer.visibility = View.GONE
                            it.data?.let { trailer ->
                                trailerAdapter.setItems(trailer.shuffled())
                            }
                        }
                        is Resource.Loading ->{
                            rvTrailer.visibility = View.GONE
                            pbTrailer.visibility = View.VISIBLE
                        }
                        is Resource.Error ->{}
                    }
                }
            }
        }
    }

//    private fun observeData() = binding.run {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                viewModel.discoverMovieById.collectLatest{
//                    when(it){
//                        is Resource.Success ->{
//                            pbLoading.visibility = View.GONE
//                            it.data?.let { movieDetail -> bindView(movieDetail) }
//                        }
//                        is Resource.Loading ->{
//                            pbLoading.visibility = View.VISIBLE
//                        }
//                        is Resource.Error ->{}
//                    }
//                }
//            }
//        }
//    }
//
//    private fun bindView(movieDetail: List<Movie>) = binding.run {
//        val name = movieDetail.map { it.name }.toString()
//        binding.apply {
//            collapsingToolbar.title = name
//        }
//    }
//
//    private fun fetchData() = with(viewModel){
//        movieById(movieId)
//    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"

//        fun startActivity(
//            context: Context,
//            movieId: Int
//        ) {
//            Intent(context, DetailActivity::class.java).apply {
//                putExtra(EXTRA_MOVIE_ID, movieId)
//            }.run { context.startActivity(this) }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}